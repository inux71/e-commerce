import { Component, effect, signal } from '@angular/core';
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
import { MatSnackBar } from '@angular/material/snack-bar';
import { merge } from 'rxjs';
import { CategoryService } from '../../services/category/category-service';
import { HttpErrorResponse } from '@angular/common/http';
import { Router } from '@angular/router';

@Component({
  selector: 'app-add-category',
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    ReactiveFormsModule,
  ],
  templateUrl: './add-category.html',
  styleUrl: './add-category.css',
})
export class AddCategory {
  addCategoryForm = new FormGroup({
    name: new FormControl('', [Validators.required]),
  });
  nameErrorMessage = signal('');
  errorMessage = signal<string | null>(null);

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    merge(
      this.addCategoryForm.controls.name.statusChanges,
      this.addCategoryForm.controls.name.valueChanges
    )
      .pipe(takeUntilDestroyed())
      .subscribe(() => this.updateNameErrorMessage());

    effect(() => {
      const message = this.errorMessage();

      if (message) {
        snackBar.open(message);
        this.errorMessage.set(null);
      }
    });
  }

  private updateNameErrorMessage() {
    if (this.addCategoryForm.controls.name.hasError('required')) {
      this.nameErrorMessage.set('You must enter a name');
    } else {
      this.nameErrorMessage.set('');
    }
  }

  addCategory() {
    const name = this.addCategoryForm.controls.name.value;

    this.categoryService.createCategory(name!).subscribe({
      next: (_) => {
        this.snackBar.open('Successfuly created category');
      },
      error: (error: HttpErrorResponse) => {
        switch (error.status) {
          case 401:
            this.router.navigate(['/sign-in'], { replaceUrl: true });
            break;
          case 409:
            this.errorMessage.set(`Category with name ${name} already exists`);
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
