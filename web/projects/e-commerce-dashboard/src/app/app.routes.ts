import { Routes } from '@angular/router';
import { NotFound } from './pages/not-found/not-found';
import { SignIn } from './pages/sign-in/sign-in';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from 'e-commerce-auth';
import { Products } from './pages/products/products';
import { Categories } from './pages/categories/categories';
import { Settings } from './pages/settings/settings';
import { Account } from './pages/account/account';

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
    children: [
      {
        path: 'products',
        component: Products,
        title: 'Products',
      },
      {
        path: 'categories',
        component: Categories,
        title: 'Categories',
      },
      {
        path: 'account',
        component: Account,
        title: 'Account',
      },
      {
        path: 'settings',
        component: Settings,
        title: 'Settings',
      },
    ],
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
