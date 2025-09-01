import { InjectionToken } from '@angular/core';

export interface Config {
  signInUrl: string;
}

export const AUTH_CONFIG = new InjectionToken<Config>('AUTH_CONFIG');
