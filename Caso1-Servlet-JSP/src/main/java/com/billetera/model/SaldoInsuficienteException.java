package com.billetera.model;

/**
 * Se lanza cuando se intenta registrar un Gasto que supera el saldo
 * disponible en la billetera. Es una excepción checked a propósito:
 * obliga al Servlet a manejar explícitamente el caso de negocio inválido.
 */
public class SaldoInsuficienteException extends Exception {

    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
