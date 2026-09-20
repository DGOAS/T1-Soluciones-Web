import { Component } from '@angular/core';
import { Billetera } from './billetera/billetera';

@Component({
  selector: 'app-root',
  imports: [Billetera],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
}