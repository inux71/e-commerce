import { Component } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatTabsModule } from '@angular/material/tabs';

@Component({
  selector: 'app-categories',
  imports: [MatIconModule, MatTabsModule],
  templateUrl: './categories.html',
  styleUrl: './categories.css',
})
export class Categories {}
