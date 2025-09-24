package com.geovannycode.store.inventory;

import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public void reserveStock(Map<ProductIdentifier, Integer> requested) {
        Map<ProductIdentifier, InventoryEntity> loaded = new HashMap<>();
        for (var entry : requested.entrySet()) {
            var pid = entry.getKey();
            var inv = inventoryRepository.findWithLockingByProductId(pid)
                    .orElseThrow(() -> new IllegalStateException("No inventory for product " + pid.getId()));
            loaded.put(pid, inv);
        }

        // 2) Validar TODO antes de mutar
        for (var entry : requested.entrySet()) {
            var pid = entry.getKey();
            var qty = entry.getValue();
            var inv = loaded.get(pid);
            if (qty == null || qty <= 0) {
                throw new IllegalArgumentException("Invalid quantity for " + pid.getId());
            }
            if (inv.getAvailableStock() < qty) {
                throw new IllegalStateException("Insufficient stock for " + pid.getId()
                        + " (requested " + qty + ", available " + inv.getAvailableStock() + ")");
            }
        }

        // 3) Aplicar decrementos y persistir
        for (var entry : requested.entrySet()) {
            var pid = entry.getKey();
            var qty = entry.getValue();
            var inv = loaded.get(pid);
            inv.decreaseStock(qty);                // ya actualiza lastUpdated
            inventoryRepository.save(inv);
        }
    }
}
