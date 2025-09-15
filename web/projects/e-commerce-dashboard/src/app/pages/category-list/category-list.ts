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
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { CategoryService } from '../../services/category/category-service';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse } from '@angular/common/http';

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
  displayedColumns = ['id', 'name', 'numberOfProducts'];
  categoriesDataSource = new MatTableDataSource<Category>([]);
  errorMessage = signal<string | null>(null);

  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private categoryService: CategoryService,
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

  ngAfterViewInit(): void {
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
}
