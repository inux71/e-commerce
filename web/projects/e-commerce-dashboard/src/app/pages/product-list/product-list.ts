import {
  AfterViewInit,
  Component,
  effect,
  OnInit,
  signal,
  ViewChild,
} from '@angular/core';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
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

@Component({
  selector: 'app-product-list',
  imports: [
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatPaginatorModule,
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

  errorMessage = signal<string | null>(null);

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private productService: ProductService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    effect(() => {
      const message = this.errorMessage();

      if (message) {
        snackBar.open(message);
        this.errorMessage.set(null);
      }
    });
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

  ngAfterViewInit(): void {
    this.productsDataSource.sort = this.sort;

    this.sort.sortChange.subscribe(() => {
      this.pageIndex = 0;

      this.getProducts();
    });

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

    /*if (this.expandedProduct) {
        this.updateCategoryForm.reset();
      }*/
  }
}
