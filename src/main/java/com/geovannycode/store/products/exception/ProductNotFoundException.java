package com.geovannycode.store.products.exception;

/**
 * Excepción lanzada cuando se intenta acceder a un producto que no existe.
 */
public class ProductNotFoundException extends ProductException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
