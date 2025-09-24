package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class InventoryEntity implements AggregateRoot<InventoryEntity, InventoryEntity.InventoryIdentifier> {

    private InventoryIdentifier id;
    private ProductIdentifier productId;
    private Integer availableStock;
    private Integer reservedStock;
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

    /**
     * Value Object para el identificador de Inventory.
     */
    public record InventoryIdentifier(UUID id) implements Identifier {
        public InventoryIdentifier {
            if (id == null) {
                throw new IllegalArgumentException("Inventory ID cannot be null");
            }
        }
    }
}
