package com.billetera.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Modelo de la billetera digital: mantiene el saldo y el historial de
 * movimientos, y concentra la regla de negocio central en un único lugar
 * (en vez de dejarla dispersa en el Servlet).
 *
 * Implementa Serializable porque vive dentro de la sesión HTTP.
 */
public class Billetera implements Serializable {

    private double saldo;
    private final List<Movimiento> movimientos = new ArrayList<>();

    public Billetera(double saldoInicial) {
        this.saldo = saldoInicial;
    }

    public double getSaldo() {
        return saldo;
    }

    /** Lista de movimientos, del más reciente al más antiguo. */
    public List<Movimiento> getMovimientos() {
        return Collections.unmodifiableList(movimientos);
    }

    /**
     * Registra un nuevo movimiento aplicando la regla de negocio central:
     * un Gasto nunca puede superar el saldo disponible (no se permite
     * saldo negativo).
     *
     * @param tipo  RECARGA o GASTO
     * @param monto monto del movimiento; se asume ya validado como > 0
     * @throws SaldoInsuficienteException si es un Gasto mayor al saldo actual
     */
    public void registrarMovimiento(TipoMovimiento tipo, double monto) throws SaldoInsuficienteException {
        if (tipo == TipoMovimiento.GASTO && monto > saldo) {
            throw new SaldoInsuficienteException(
                    String.format(
                            "No se puede registrar el gasto: S/ %.2f supera el saldo disponible (S/ %.2f).",
                            monto, saldo));
        }

        if (tipo == TipoMovimiento.RECARGA) {
            saldo += monto;
        } else {
            saldo -= monto;
        }

        // Se inserta al inicio para que el historial quede del más reciente al más antiguo.
        movimientos.add(0, new Movimiento(tipo, monto));
    }
}
