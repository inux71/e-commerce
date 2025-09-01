import { Routes } from '@angular/router';
import { NotFound } from './pages/not-found/not-found';
import { SignIn } from './pages/sign-in/sign-in';
import { Dashboard } from './pages/dashboard/dashboard';

export const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/sign-in',
  },
  {
    path: 'dashboard',
    component: Dashboard,
    title: 'Dashboard',
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
