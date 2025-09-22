import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-products',
  imports: [MatIconModule, MatTabsModule, RouterModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products {
  links = [
    {
      path: '/dashboard/product/',
      title: 'Products',
      icon: 'list',
    },
    {
      path: '/dashboard/product/add',
      title: 'Add product',
      icon: 'add',
    },
  ];
  activeLink = this.links[0];
}
