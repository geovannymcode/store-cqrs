package com.geovannycode.store.products.exception;

/**
 * Excepción base para todas las excepciones relacionadas con productos.
 * Define un comportamiento común para todas las excepciones del módulo de productos.
 */
public abstract class ProductException extends RuntimeException {
    public ProductException(String message) {
        super(message);
    }

    public ProductException(String message, Throwable cause) {
        super(message, cause);
    }
}
