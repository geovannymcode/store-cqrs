package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "inventory")
public class InventoryEntity implements AggregateRoot<InventoryEntity, InventoryEntity.InventoryIdentifier> {

    @EmbeddedId
    @AttributeOverride(name = "id",
            column = @Column(name = "id", nullable = false, updatable = false, columnDefinition = "uuid"))

    private InventoryIdentifier id;

    @Convert(converter = ProductIdentifierConverter.class)
    @Column(name = "product_id", nullable = false, updatable = false)
    private ProductIdentifier productId;

    @Column(name = "available_stock", nullable = false)
    private Integer availableStock;

    @Column(name = "reserved_stock", nullable = false)
    private Integer reservedStock;

    @Column(name = "last_updated", nullable = false)
    private LocalDateTime lastUpdated;

    /**
     * Constructor principal para crear un registro de inventario.
     */
    public InventoryEntity(ProductIdentifier productId, Integer initialStock) {
        this.id = new InventoryIdentifier(UUID.randomUUID());
        this.productId = productId;
        this.availableStock = initialStock;
        this.reservedStock = 0;
        this.lastUpdated = LocalDateTime.now();
    }

    public InventoryEntity decreaseStock(Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to decrease cannot be negative");
        }

        if (availableStock < quantity) {
            throw new IllegalStateException("Not enough stock available. Requested: " +
                    quantity + ", Available: " + availableStock);
        }

        this.availableStock -= quantity;
        this.lastUpdated = LocalDateTime.now();
        return this;
    }

    public InventoryEntity increaseStock(Integer quantity) {
        if (quantity < 0) {
            throw new IllegalArgumentException("Quantity to increase cannot be negative");
        }

        this.availableStock += quantity;
        this.lastUpdated = LocalDateTime.now();
        return this;
    }


    @Embeddable
    public static class InventoryIdentifier implements Identifier {
        @Column(name = "inventory_id", nullable = false, updatable = false, columnDefinition = "uuid")
        private UUID id;

        protected InventoryIdentifier() { }

        public InventoryIdentifier(UUID id) {
            if (id == null) throw new IllegalArgumentException("Inventory ID cannot be null");
            this.id = id;
        }

        public UUID getId() { return id; }
    }
}
