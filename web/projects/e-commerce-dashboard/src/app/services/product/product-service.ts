import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../../models/category';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private httpClient: HttpClient) {}

  createProduct(
    name: string,
    description: string,
    price: number,
    categories: Category[]
  ): Observable<void> {
    const categoryIds: number[] = categories.map((category) => category.id);

    return this.httpClient.post<void>(
      `${environment.baseUrl}/${environment.endpoints.product.create}`,
      {
        name: name,
        description: description,
        price: price,
        categoryIds: categoryIds,
      }
    );
  }
}
