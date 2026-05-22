package com.techlab.ecommerce.exception;

/**
 * Excepción personalizada que se lanza cuando se intenta asignar
 * un stock inválido (por ejemplo, un valor negativo).
 *
 * Más adelante, cuando incorporemos el carrito de compras, esta
 * misma excepción nos servirá para señalar el caso en que un
 * cliente quiera comprar más unidades de las disponibles.
 *
 * Tener una excepción específica para cada tipo de error de
 * dominio hace que el código sea más expresivo y más fácil de
 * mantener.
 */
public class StockInsuficienteException extends RuntimeException {

    public StockInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
