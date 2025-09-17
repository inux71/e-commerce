import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Category } from '../../models/category';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private httpClient: HttpClient) {}

  getCategories(): Observable<Category[]> {
    return this.httpClient.get<Category[]>(
      `${environment.baseUrl}/${environment.endpoints.category.categories}`
    );
  }

  createCategory(name: string): Observable<void> {
    return this.httpClient.post<void>(
      `${environment.baseUrl}/${environment.endpoints.category.create}`,
      {
        name: name.toLowerCase().trim(),
      }
    );
  }

  updateCategory(id: number, name: string): Observable<void> {
    return this.httpClient.patch<void>(
      `${environment.baseUrl}/${environment.endpoints.category.update}`,
      {
        id: id,
        name: name.toLowerCase().trim(),
      }
    );
  }
}
