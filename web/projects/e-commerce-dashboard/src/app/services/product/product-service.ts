import { HttpClient, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Category } from '../../models/category';
import { environment } from '../../../environments/environment';
import { Page } from '../../common/page';
import { Product } from '../../models/product';

@Injectable({
  providedIn: 'root',
})
export class ProductService {
  constructor(private httpClient: HttpClient) {}

  getProducts(
    sort?: string,
    page?: number,
    size?: number
  ): Observable<Page<Product>> {
    let params = new HttpParams();

    if (sort) {
      params = params.append('sort', sort);
    }

    if (page) {
      params = params.append('page', page);
    }

    if (size) {
      params = params.append('size', size);
    }

    return this.httpClient.get<Page<Product>>(
      `${environment.baseUrl}/${environment.endpoints.product.products}`,
      { params }
    );
  }

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

  updateProduct(
    id: number,
    name: string,
    description: string,
    price: number,
    categories: Category[]
  ): Observable<void> {
    const categoryIds: number[] = categories.map((category) => category.id);

    return this.httpClient.patch<void>(
      `${environment.baseUrl}/${environment.endpoints.product.update}`,
      {
        id: id,
        name: name,
        description: description,
        price: price,
        categoryIds: categoryIds,
      }
    );
  }
}
