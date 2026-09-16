import { Component } from '@angular/core';
import { NuevoMovimiento } from './nuevo-movimiento/nuevo-movimiento';

@Component({
  selector: 'app-root',
  imports: [NuevoMovimiento],
  templateUrl: './app.html',
  styleUrl: './app.css'
})
export class App {
}