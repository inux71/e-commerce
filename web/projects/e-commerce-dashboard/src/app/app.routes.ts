import { Routes } from '@angular/router';
import { NotFound } from './pages/not-found/not-found';
import { SignIn } from './pages/sign-in/sign-in';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from 'e-commerce-auth';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/dashboard',
  },
  {
    path: 'dashboard',
    component: Dashboard,
    title: 'Dashboard',
    canActivate: [authGuard],
  },
  {
    path: 'sign-in',
    component: SignIn,
    title: 'Sign in',
  },
  {
    path: '**',
    component: NotFound,
  },
];
