import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class CategoryService {
  constructor(private httpClient: HttpClient) {}

  createCategory(name: string): Observable<void> {
    return this.httpClient.post<void>(
      `${environment.baseUrl}/${environment.endpoints.category.create}`,
      {
        name: name.toLowerCase().trim(),
      }
    );
  }
}
