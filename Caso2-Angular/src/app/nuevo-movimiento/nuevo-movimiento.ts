import { Component, input, output } from '@angular/core';
import { FormsModule } from '@angular/forms';

export interface Movimiento {
  tipo: 'Recarga' | 'Gasto';
  monto: number;
}

@Component({
  selector: 'app-nuevo-movimiento',
  imports: [FormsModule],
  templateUrl: './nuevo-movimiento.html',
  styleUrl: './nuevo-movimiento.css'
})
export class NuevoMovimiento {

  saldo = input<number>(0);

  movimientoNuevo = output<Movimiento>();

  monto: number | null = null;
  tipo: 'Recarga' | 'Gasto' = 'Recarga';

  get gastoInvalido(): boolean {
    return this.tipo === 'Gasto' &&
           this.monto !== null &&
           this.monto > this.saldo();
  }

  registrarMovimiento(): void {

    if (this.monto === null || this.monto <= 0 || this.gastoInvalido) {
      return;
    }

    this.movimientoNuevo.emit({
      tipo: this.tipo,
      monto: this.monto
    });

    this.monto = null;
    this.tipo = 'Recarga';
  }
}
