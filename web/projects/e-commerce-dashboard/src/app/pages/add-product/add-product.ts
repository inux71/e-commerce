import { AfterViewInit, Component, effect, signal } from '@angular/core';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { merge } from 'rxjs';
import { Category } from '../../models/category';
import { CategoryService } from '../../services/category/category-service';
import { HttpErrorResponse } from '@angular/common/http';
import { MatChipsModule } from '@angular/material/chips';
import { MatIconModule } from '@angular/material/icon';
import { ProductService } from '../../services/product/product-service';

@Component({
  selector: 'app-add-product',
  imports: [
    MatButtonModule,
    MatChipsModule,
    MatFormFieldModule,
    MatIconModule,
    MatInputModule,
    MatSelectModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-product.html',
  styleUrl: './add-product.css',
})
export class AddProduct implements AfterViewInit {
  addProductForm = new FormGroup({
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

  constructor(
    private categoryService: CategoryService,
    private productService: ProductService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    merge(
      this.addProductForm.controls.name.statusChanges,
      this.addProductForm.controls.name.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateNameErrorMessage());

    merge(
      this.addProductForm.controls.description.statusChanges,
      this.addProductForm.controls.description.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateDescriptionErrorMessage());

    merge(
      this.addProductForm.controls.price.statusChanges,
      this.addProductForm.controls.price.valueChanges
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

  ngAfterViewInit(): void {
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

  private updateNameErrorMessage() {
    if (this.addProductForm.controls.name.hasError('required')) {
      this.nameErrorMessage.set('You must enter a name');
    } else {
      this.nameErrorMessage.set('');
    }
  }

  private updateDescriptionErrorMessage() {
    const description = this.addProductForm.controls.description;

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
    const price = this.addProductForm.controls.price;

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

  removeCategory(category: Category) {
    const categories = this.addProductForm.controls.categories;

    categories.setValue(categories.value!.filter((c) => c.id != category.id));
  }

  addProduct() {
    const name: string = this.addProductForm.controls.name.value!;
    const description: string = this.addProductForm.controls.description.value!;
    const price: number = this.addProductForm.controls.price.value!;
    const categories: Category[] =
      this.addProductForm.controls.categories.value!;

    this.productService
      .createProduct(name, description, price, categories)
      .subscribe({
        next: (_) => {
          this.snackBar.open('Successfuly created product');
        },
        error: (error: HttpErrorResponse) => {
          switch (error.status) {
            case 401:
              this.router.navigate(['/sign-in'], { replaceUrl: true });
              break;
            case 409:
              this.errorMessage.set(`Product with name ${name} already exists`);
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
}
