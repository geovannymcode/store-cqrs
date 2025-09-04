package com.geovannycode.store.products.command;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;
import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * Agregado Product del lado Command.
 * Optimizado para mantener consistencia e integridad de datos.
 */
@Getter
@Setter
public class Product implements AggregateRoot<Product, Product.ProductIdentifier> {

    private ProductIdentifier id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stock;
    private List<Review> productReviews = new ArrayList<>();

    public Product() {
        this.id = new ProductIdentifier(UUID.randomUUID());
    }

    /**
     * Agregar un review al producto.
     * Mantiene la consistencia del agregado.
     */
    public Product add(Review review) {
        if (review == null) {
            throw new IllegalArgumentException("Review cannot be null");
        }
        this.productReviews.add(review);
        return this;
    }

    /**
     * Value Object para el identificador del producto.
     */
    public record ProductIdentifier(UUID id) implements Identifier {
        public ProductIdentifier {
            if (id == null) {
                throw new IllegalArgumentException("Product ID cannot be null");
            }
        }
    }
}
