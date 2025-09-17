import {
  AfterViewInit,
  Component,
  effect,
  OnInit,
  signal,
  ViewChild,
} from '@angular/core';
import { Category } from '../../models/category';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import {
  FormControl,
  FormGroup,
  ReactiveFormsModule,
  Validators,
} from '@angular/forms';
import { CategoryService } from '../../services/category/category-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';
import { merge } from 'rxjs';
import { takeUntilDestroyed } from '@angular/core/rxjs-interop';
import { Router } from '@angular/router';

@Component({
  selector: 'app-category-list',
  imports: [
    MatButtonModule,
    MatFormFieldModule,
    MatIconModule,
    MatSortModule,
    MatTableModule,
    MatInputModule,
    MatButtonModule,
    ReactiveFormsModule,
  ],
  templateUrl: './category-list.html',
  styleUrl: './category-list.css',
})
export class CategoryList implements AfterViewInit, OnInit {
  searchText = new FormControl('');
  displayedColumns = ['id', 'name', 'numberOfProducts', 'expand'];
  categoriesDataSource = new MatTableDataSource<Category>([]);
  expandedCategory: Category | null = null;
  updateCategoryForm = new FormGroup({
    name: new FormControl('', Validators.required),
  });
  nameErrorMessage = signal('');
  errorMessage = signal<string | null>(null);

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private categoryService: CategoryService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {
    merge(
      this.updateCategoryForm.controls.name.valueChanges,
      this.updateCategoryForm.controls.name.statusChanges
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

  private getCategories() {
    this.categoriesDataSource.sort = this.sort;

    this.categoryService.getCategories().subscribe({
      next: (categories: Category[]) => {
        this.categoriesDataSource.data = categories;
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
    if (this.updateCategoryForm.controls.name.hasError('required')) {
      this.nameErrorMessage.set('You must enter a name');
    } else {
      this.nameErrorMessage.set('');
    }
  }

  ngAfterViewInit(): void {
    this.getCategories();
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
    this.categoriesDataSource.filter = filterValue.trim().toLowerCase();
  }

  clearSearchText(event: MouseEvent) {
    event.preventDefault();

    this.searchText.setValue('');
    this.categoriesDataSource.filter = '';
  }

  isExpanded(category: Category) {
    return this.expandedCategory === category;
  }

  toggle(category: Category) {
    this.expandedCategory = this.isExpanded(category) ? null : category;

    if (this.expandedCategory) {
      this.updateCategoryForm.reset();
    }
  }

  updateCategory(category: Category) {
    const name = this.updateCategoryForm.controls.name.value;

    this.categoryService.updateCategory(category.id, name!).subscribe({
      next: (_) => {
        this.snackBar.open('Successfully updated category');

        this.getCategories();
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

  deleteCategory() {}
}
