package com.geovannycode.store.inventory;

import com.geovannycode.store.orders.domain.models.OrderCreatedEvent;
import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventInventoryHandler {

    private final InventoryService inventoryService;

    @ApplicationModuleListener
    @Transactional
    public void on(OrderCreatedEvent event) {
        log.info("Processing OrderCreatedEvent for order: {}", event.orderId());

        event.items().forEach(item -> {
            ProductIdentifier productId = new ProductIdentifier(item.productId());
            Integer quantity = item.quantity();

            try {
                inventoryService.decreaseStock(productId, quantity);
                log.info("Stock decreased for product: {}, quantity: {}", productId.getId(), quantity);
            } catch (IllegalStateException e) {
                log.error("Failed to decrease stock for product: {}, reason: {}",
                        productId.getId(), e.getMessage());

            }
        });
    }
}
