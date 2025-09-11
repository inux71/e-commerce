import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-products',
  imports: [MatIconModule, MatTabsModule],
  templateUrl: './products.html',
  styleUrl: './products.css',
})
export class Products {}
