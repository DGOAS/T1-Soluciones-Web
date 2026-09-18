package com.billetera.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Representa un único movimiento (Recarga o Gasto) registrado en la billetera.
 * Es inmutable: una vez creado, un movimiento no cambia.
 */
public class Movimiento implements Serializable {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private final TipoMovimiento tipo;
    private final double monto;
    private final LocalDate fecha;

    public Movimiento(TipoMovimiento tipo, double monto) {
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = LocalDate.now();
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public double getMonto() {
        return monto;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    /** Fecha ya formateada como dd/MM/yyyy, lista para mostrar en la vista JSP. */
    public String getFechaFormateada() {
        return fecha.format(FORMATO_FECHA);
    }

    /** Getters "is..." para poder usarlos directamente en EL/JSTL: ${mov.recarga} */
    public boolean isRecarga() {
        return tipo == TipoMovimiento.RECARGA;
    }

    public boolean isGasto() {
        return tipo == TipoMovimiento.GASTO;
    }
}
