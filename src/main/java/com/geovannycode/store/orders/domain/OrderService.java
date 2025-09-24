package com.geovannycode.store.orders.domain;

import com.geovannycode.store.common.events.DomainEventPublisher;
import com.geovannycode.store.inventory.InventoryService;
import com.geovannycode.store.orders.domain.models.Customer;
import com.geovannycode.store.orders.domain.models.OrderCreatedEvent;
import com.geovannycode.store.orders.domain.models.OrderItem;
import com.geovannycode.store.orders.domain.models.OrderStatus;
import com.geovannycode.store.orders.web.InvalidOrderException;
import com.geovannycode.store.orders.web.OrderNotFoundException;
import com.geovannycode.store.products.command.ProductIdentifier;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final InventoryService inventoryService;
    private final OrderRepository orderRepository;
    private final DomainEventPublisher events;


    public OrderEntity.OrderIdentifier createOrder(Customer customer, List<OrderItem> items) {

        if (customer == null || customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new InvalidOrderException("Customer email is required");
        }

        if (items == null || items.isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one item");
        }


        // 1) Mapa productId -> qty (del VO embebible)
        Map<ProductIdentifier, Integer> requested = items.stream()
                .collect(Collectors.toMap(
                        OrderItem::getProductId,
                        OrderItem::getQuantity,
                        Integer::sum // por si repiten producto
                ));

        // 2) Reservar stock (lanza si no alcanza; toda la TX se revierte)
        inventoryService.reserveStock(requested);

        // 3) Crear y guardar orden
        OrderEntity order = new OrderEntity(customer);
        items.forEach(order::addItem);
        OrderEntity saved = orderRepository.save(order);

        // 4) Publicar evento AFTER_COMMIT (solo si se confirmó la TX)
        var event = new OrderCreatedEvent(
                saved.getId().id(),
                saved.getCreatedAt(),
                customer.getName(),
                customer.getEmail(),
                saved.calculateTotal(),
                saved.getItems().stream().map(i ->
                        new OrderCreatedEvent.OrderItemDto(
                                i.getProductId().getId(),
                                i.getQuantity(),
                                i.getUnitPrice()
                        )
                ).toList()
        );
        events.publishAfterCommit(event);

        return saved.getId();
    }

    public OrderEntity findById(OrderEntity.OrderIdentifier orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + orderId.id()));
    }

    public OrderEntity updateOrderStatus(OrderEntity.OrderIdentifier orderId, OrderStatus status) {
        OrderEntity order = findById(orderId);
        order.updateStatus(status);
        return orderRepository.save(order);
    }
}
