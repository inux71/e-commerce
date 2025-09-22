import { Routes } from '@angular/router';
import { NotFound } from './pages/not-found/not-found';
import { SignIn } from './pages/sign-in/sign-in';
import { Dashboard } from './pages/dashboard/dashboard';
import { authGuard } from 'e-commerce-auth';
import { Products } from './pages/products/products';
import { Categories } from './pages/categories/categories';
import { Settings } from './pages/settings/settings';
import { Account } from './pages/account/account';
import { AddCategory } from './pages/add-category/add-category';
import { CategoryList } from './pages/category-list/category-list';
import { ProductList } from './pages/product-list/product-list';
import { AddProduct } from './pages/add-product/add-product';

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
        path: 'product',
        component: Products,
        title: 'Products',
        children: [
          {
            path: '',
            component: ProductList,
            title: 'Products',
          },
          {
            path: 'add',
            component: AddProduct,
            title: 'Add product',
          },
        ],
      },
      {
        path: 'category',
        component: Categories,
        title: 'Categories',
        children: [
          {
            path: '',
            component: CategoryList,
            title: 'Categories',
          },
          {
            path: 'add',
            component: AddCategory,
            title: 'Add category',
          },
        ],
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
