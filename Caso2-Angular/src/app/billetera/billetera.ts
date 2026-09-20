import { Component } from '@angular/core';
import { NuevoMovimiento, Movimiento } from '../nuevo-movimiento/nuevo-movimiento';

@Component({
  selector: 'app-billetera',
  imports: [NuevoMovimiento],
  templateUrl: './billetera.html',
  styleUrl: './billetera.css'
})
export class Billetera {

  saldo = 250;

  movimientos: Movimiento[] = [];

  agregarMovimiento(movimiento: Movimiento): void {

    if (movimiento.tipo === 'Recarga') {
      this.saldo += movimiento.monto;
    } else {
      this.saldo -= movimiento.monto;
    }

    this.movimientos.unshift(movimiento);
  }
}