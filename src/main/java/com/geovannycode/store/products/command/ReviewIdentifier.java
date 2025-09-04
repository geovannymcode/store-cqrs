package com.geovannycode.store.products.command;

import org.jmolecules.ddd.types.Identifier;

import java.util.UUID;

/**
 * Value Object para identificar reviews.
 */
public record ReviewIdentifier(UUID id) implements Identifier {
    public ReviewIdentifier {
        if (id == null) {
            throw new IllegalArgumentException("Review ID cannot be null");
        }
    }
}

