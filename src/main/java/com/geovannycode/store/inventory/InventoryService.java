package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public InventoryEntity updateStock(ProductIdentifier productId, Integer stock) {
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseGet(() -> new InventoryEntity(productId, 0));

        inventory.setAvailableStock(stock);
        inventory.setLastUpdated(java.time.LocalDateTime.now());

        return inventoryRepository.save(inventory);
    }

    public InventoryEntity decreaseStock(ProductIdentifier productId, Integer quantity) {
        InventoryEntity inventory = inventoryRepository.findByProductId(productId)
                .orElseThrow(() -> new IllegalStateException("No inventory found for product: " + productId.toString()));

        inventory.decreaseStock(quantity);

        return inventoryRepository.save(inventory);
    }

    @Transactional()
    public int getAvailableStock(ProductIdentifier productId) {
        return inventoryRepository.findByProductId(productId)
                .map(InventoryEntity::getAvailableStock)
                .orElse(0);
    }

    @Transactional()
    public boolean hasEnoughStock(ProductIdentifier productId, Integer quantity) {
        return getAvailableStock(productId) >= quantity;
    }
}
