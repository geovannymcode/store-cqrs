package com.geovannycode.store.inventory;

import com.geovannycode.store.orders.domain.models.OrderCreatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderEventInventoryHandler {

    @ApplicationModuleListener
    public void on(OrderCreatedEvent event) {
        log.info("OrderCreatedEvent received for order: {}", event.orderId());
        // TODO: métricas / auditoría / tracing (no modificar inventario aquí)
    }
}