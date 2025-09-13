package com.geovannycode.store.products.command;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import org.jmolecules.ddd.types.Identifier;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
public final class ProductIdentifier implements Identifier, Serializable {

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
