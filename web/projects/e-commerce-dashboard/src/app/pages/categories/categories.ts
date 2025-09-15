import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-categories',
  imports: [MatIconModule, MatTabsModule, RouterModule],
  templateUrl: './categories.html',
  styleUrl: './categories.css',
})
export class Categories {
  links = [
    {
      path: '/dashboard/category/',
      title: 'Categories',
      icon: 'list',
    },
    {
      path: '/dashboard/category/add',
      title: 'Add category',
      icon: 'add',
    },
  ];
  activeLink = this.links[0];
}
