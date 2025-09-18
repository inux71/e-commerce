import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root',
})
export class EmployeeService {
  constructor(private httpClient: HttpClient) {}

  changePassword(password: string): Observable<void> {
    return this.httpClient.patch<void>(
      `${environment.baseUrl}/${environment.endpoints.employee.changePassword}`,
      {
        password: password,
      }
    );
  }
}
