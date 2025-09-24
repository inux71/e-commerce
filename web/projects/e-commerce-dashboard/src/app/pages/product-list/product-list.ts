import {
  AfterViewInit,
  Component,
  effect,
  OnInit,
  signal,
  ViewChild,
} from '@angular/core';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { Product } from '../../models/product';
import { ProductService } from '../../services/product/product-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { HttpErrorResponse } from '@angular/common/http';
import { Page } from '../../common/page';
import { MatChipsModule } from '@angular/material/chips';
import { Category } from '../../models/category';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { MatSelectModule } from '@angular/material/select';
import { CategoryService } from '../../services/category/category-service';

@Component({
  selector: 'app-product-list',
  imports: [
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
    MatSelectModule,
    MatSortModule,
    MatTableModule,
    ReactiveFormsModule,
  ],
  templateUrl: './product-list.html',
  styleUrl: './product-list.css',
})
export class ProductList implements AfterViewInit, OnInit {
  searchText = new FormControl<string>('');
  totalElements = 0;
  pageIndex = 0;
  pageSizeOptions: number[] = [10, 20, 30, 40, 50];
  pageSize = this.pageSizeOptions[0];
  displayedColumns: string[] = [
    'id',
    'name',
    'price',
    'categories',
    'updatedBy',
    'expand',
  ];
  productsDataSource = new MatTableDataSource<Product>([]);
  expandedProduct: Product | null = null;
  updateProductForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
    description: new FormControl('', [
      Validators.required,
      Validators.maxLength(255),
    ]),
    price: new FormControl<number>(0, [
      Validators.required,
      Validators.min(0.01),
      Validators.max(999999.99),
    ]),
    categories: new FormControl<Category[]>([]),
  });
  nameErrorMessage = signal('');
  descriptionErrorMessage = signal('');
  priceErrorMessage = signal('');
  errorMessage = signal<string | null>(null);

  categories: Category[] = [];

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    merge(
      this.updateProductForm.controls.name.statusChanges,
      this.updateProductForm.controls.name.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateNameErrorMessage());

    merge(
      this.updateProductForm.controls.description.statusChanges,
      this.updateProductForm.controls.description.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateDescriptionErrorMessage());

    merge(
      this.updateProductForm.controls.price.statusChanges,
      this.updateProductForm.controls.price.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updatePriceErrorMessage());

    effect(() => {
      const message = this.errorMessage();

      if (message) {
        snackBar.open(message);
        this.errorMessage.set(null);
      }
    });
  }

  private updateNameErrorMessage() {
    if (this.updateProductForm.controls.name.hasError('required')) {
      this.nameErrorMessage.set('You must enter a name');
    } else {
      this.nameErrorMessage.set('');
    }
  }

  private updateDescriptionErrorMessage() {
    const description = this.updateProductForm.controls.description;

    if (description.hasError('required')) {
      this.descriptionErrorMessage.set('You must enter a description');
    } else if (description.hasError('maxlength')) {
      const error = description.getError('maxlength');
      const requiredLength = error.requiredLength;
      const actualLength = error.actualLength;

      this.descriptionErrorMessage.set(
        `Description is too long. It has ${actualLength} characters, but the maximum allowed is ${requiredLength}.`
      );
    } else {
      this.descriptionErrorMessage.set('');
    }
  }

  private updatePriceErrorMessage() {
    const price = this.updateProductForm.controls.price;

    if (price.hasError('required')) {
      this.priceErrorMessage.set('You must enter a price');
    } else if (price.hasError('min')) {
      const min = price.getError('min').min;

      this.priceErrorMessage.set(`Price must be at least ${min}`);
    } else if (price.hasError('max')) {
      const max = price.getError('max').max;

      this.priceErrorMessage.set(`Price must be lower than ${max}`);
    } else {
      this.priceErrorMessage.set('');
    }
  }

  private getProducts() {
    this.productsDataSource.sort = this.sort;

    const sort: string = this.productsDataSource.sort.active;

    this.productService
      .getProducts(sort, this.pageIndex, this.pageSize)
      .subscribe({
        next: (productPage: Page<Product>) => {
          this.totalElements = productPage.page.totalElements;
          this.pageIndex = productPage.page.number;
          this.pageSize = productPage.page.size;
          this.productsDataSource.data = productPage.content;
        },
        error: (error: HttpErrorResponse) => {
          switch (error.status) {
            case 401:
              this.router.navigate(['/sign-in'], { replaceUrl: true });
              break;
            case 500:
              this.errorMessage.set('Unable to connect with a server');
              break;
            default:
              console.log(error);
              this.errorMessage.set('Unknown error occurs');
              break;
          }
        },
      });
  }

  private getCategories() {
    this.categoryService.getCategories().subscribe({
      next: (categories) => {
        this.categories = categories;
      },
      error: (error: HttpErrorResponse) => {
        switch (error.status) {
          case 500:
            this.errorMessage.set('Unable to connect with a server');
            break;
          default:
            console.log(error);
            this.errorMessage.set('Unknown error occurs');
            break;
        }
      },
    });
  }

  ngAfterViewInit(): void {
    this.productsDataSource.sort = this.sort;

    this.sort.sortChange.subscribe(() => {
      this.pageIndex = 0;

      this.getProducts();
    });

    this.getCategories();
    this.getProducts();
  }

  ngOnInit(): void {
    this.searchText.valueChanges.subscribe((value) => {
      if (value) {
        this.searchText.setValue(value.trimStart());
      }
    });
  }

  applyFilter(event: Event) {
    event.preventDefault();

    const filterValue: string = (event.target as HTMLInputElement).value;
    this.productsDataSource.filter = filterValue.trim().toLowerCase();
  }

  clearSearchText(event: MouseEvent) {
    event.preventDefault();

    this.searchText.setValue('');
    this.productsDataSource.filter = '';
  }

  handlePageEvent(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;

    this.getProducts();
  }

  isExpanded(product: Product) {
    return this.expandedProduct === product;
  }

  toggle(product: Product) {
    this.expandedProduct = this.isExpanded(product) ? null : product;

    if (this.expandedProduct) {
      this.updateProductForm.reset();
      this.updateProductForm.controls.name.setValue(product.name);
      this.updateProductForm.controls.description.setValue(product.description);
      this.updateProductForm.controls.price.setValue(product.price);

      const productCategories: Category[] = this.categories.filter((category) =>
        product.categoryNames.includes(category.name)
      );
      this.updateProductForm.controls.categories.setValue(productCategories);
    }
  }

  removeCategory(category: Category) {
    const categories = this.updateProductForm.controls.categories;

    categories.setValue(categories.value!.filter((c) => c.id != category.id));
  }

  productChanged(product: Product): boolean {
    const nameChanged: boolean =
      product.name != this.updateProductForm.controls.name.value;
    const descriptionChanged: boolean =
      product.description != this.updateProductForm.controls.description.value;
    const priceChanged: boolean =
      product.price != this.updateProductForm.controls.price.value;

    const productCategoryIds: number[] = this.categories
      .filter((category) => product.categoryNames.includes(category.name))
      .map((category) => category.id);
    const newCategoryIds: number[] =
      this.updateProductForm.controls.categories.value!.map(
        (category) => category.id
      );
    const categoriesChanged: boolean =
      !productCategoryIds.every((id) => newCategoryIds.includes(id)) ||
      !newCategoryIds.every((id) => productCategoryIds.includes(id));

    return (
      nameChanged || descriptionChanged || priceChanged || categoriesChanged
    );
  }

  updateProduct(product: Product) {
    const name: string = this.updateProductForm.controls.name.value!;
    const description: string =
      this.updateProductForm.controls.description.value!;
    const price: number = this.updateProductForm.controls.price.value!;
    const categories: Category[] =
      this.updateProductForm.controls.categories.value!;

    this.productService
      .updateProduct(product.id, name, description, price, categories)
      .subscribe({
        next: (_) => {
          this.snackBar.open('Successfully updated product');

          this.getProducts();
        },
        error: (error: HttpErrorResponse) => {
          switch (error.status) {
            case 401:
              this.router.navigate(['/sign-in'], { replaceUrl: true });
              break;
            case 404:
              this.errorMessage.set('Category not found');
              break;
            case 500:
              this.errorMessage.set('Unable to connect with a server');
              break;
            default:
              console.log(error);
              this.errorMessage.set('Unknown error occurs');
              break;
          }
        },
      });
  }

  deleteProduct() {}
}
