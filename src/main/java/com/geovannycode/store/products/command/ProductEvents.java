package com.geovannycode.store.products.command;

import org.jmolecules.event.types.DomainEvent;

import java.math.BigDecimal;

/**
 * Eventos de dominio del módulo Products.
 * Permiten comunicación asíncrona entre módulos.
 */
public class ProductEvents {

    /**
     * Evento publicado cuando se crea un producto.
     */
    public record ProductCreated(
            ProductIdentifier id,
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            String category
    ) implements DomainEvent {}

    /**
     * Evento publicado cuando se actualiza un producto.
     */
    public record ProductUpdated(
            ProductIdentifier id,
            String name,
            String description,
            BigDecimal price,
            Integer stock,
            String category,
            String eventIdentifier
    ) implements DomainEvent {}

    /**
     * Evento publicado cuando se agrega un review.
     */
    public record ProductReviewed(
            ProductIdentifier productId,
            ReviewIdentifier reviewId,
            Integer vote,
            String comment
    ) implements DomainEvent {}
}
