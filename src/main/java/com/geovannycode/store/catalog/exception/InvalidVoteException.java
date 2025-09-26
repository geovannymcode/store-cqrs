package com.geovannycode.store.catalog.exception;

/**
 * Excepción lanzada cuando se intenta crear una reseña con un voto inválido.
 */
public class InvalidVoteException extends ProductException {
    public InvalidVoteException(String message) {
        super(message);
    }
}
