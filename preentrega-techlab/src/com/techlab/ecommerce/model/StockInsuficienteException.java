package com.techlab.ecommerce.model;

/**
 * Excepción personalizada que se lanza cuando no hay suficiente
 * stock disponible para cumplir con un pedido.
 */
public class StockInsuficienteException extends Exception {
    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}