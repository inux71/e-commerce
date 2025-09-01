import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Token } from '../models/token';
import { AUTH_CONFIG } from '../../public-api';
import { Config } from '../config';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private token: string | null = null;

  constructor(
    private httpClient: HttpClient,
    @Inject(AUTH_CONFIG) private config: Config
  ) {}

  getToken(): string | null {
    return this.token;
  }

  setToken(token: string) {
    this.token = token;
  }

  signIn(email: string, password: string): Observable<Token> {
    return this.httpClient.post<Token>(this.config.signInUrl, {
      email: email,
      password: password,
    });
  }

  signOut() {
    this.token = null;
  }
}
