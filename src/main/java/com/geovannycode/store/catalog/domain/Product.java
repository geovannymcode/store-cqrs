package com.geovannycode.store.catalog.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import org.jmolecules.ddd.types.AggregateRoot;
import lombok.Getter;
import lombok.Setter;
import org.jmolecules.ddd.types.Identifier;

import java.util.List;

/**
 * Agregado Product del lado Command.
 * Optimizado para mantener consistencia e integridad de datos.
 */
@Entity
@Table(name = "products")
@Getter
@Setter
public class Product implements AggregateRoot<Product, Product.ProductIdentifier> {

    @EmbeddedId
    private ProductIdentifier id;
    private String name;
    private String description;
    private String category;
    private BigDecimal price;
    private Integer stock;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "product_id", nullable = false)
    private List<Review> productReviews = new ArrayList<>();

    public Product() {
        this.id = new ProductIdentifier(UUID.randomUUID());
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
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

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Embeddable
    public static final class ProductIdentifier implements Identifier, Serializable {

        @Column(name = "id", nullable = false, updatable = false)
        private UUID id;

        protected ProductIdentifier() {  }

        public ProductIdentifier(UUID id) {
            this.id = Objects.requireNonNull(id, "Product ID cannot be null");
        }

        @JsonCreator(mode = JsonCreator.Mode.DELEGATING)
        public static ProductIdentifier from(UUID id) {
            return new ProductIdentifier(id);
        }

        @JsonValue
        public UUID getId() { return id; }

        // Factory helpers opcionales
        public static ProductIdentifier random() { return new ProductIdentifier(UUID.randomUUID()); }
        public static ProductIdentifier fromString(String value) { return new ProductIdentifier(UUID.fromString(value)); }

        // equals/hashCode/toString
        @Override public boolean equals(Object o) { return o instanceof ProductIdentifier that && Objects.equals(id, that.id); }
        @Override public int hashCode() { return Objects.hash(id); }
        @Override public String toString() { return id.toString(); }
    }
}
