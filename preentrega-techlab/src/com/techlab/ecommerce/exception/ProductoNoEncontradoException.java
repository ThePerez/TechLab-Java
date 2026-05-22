package com.techlab.ecommerce.exception;

/**
 * Excepción personalizada que se lanza cuando se busca un producto
 * por su id y no existe en el sistema.
 *
 * Hereda de RuntimeException (excepción no chequeada): no obliga
 * a quien usa el método a envolver la llamada en try/catch, pero
 * sí permite capturarla cuando nos interesa (por ejemplo, en el menú).
 *
 * Crear nuestras propias excepciones nos permite comunicar errores
 * de dominio con nombres claros, en lugar de usar excepciones
 * genéricas como Exception o IllegalArgumentException.
 */
public class ProductoNoEncontradoException extends RuntimeException {

    public ProductoNoEncontradoException(String mensaje) {
        // super() llama al constructor de la clase padre (RuntimeException),
        // que es quien guarda el mensaje y lo expone con getMessage().
        super(mensaje);
    }
}
