package com.geovannycode.store.orders.domain;

import com.geovannycode.store.orders.domain.models.Customer;
import com.geovannycode.store.orders.domain.models.OrderCreatedEvent;
import com.geovannycode.store.orders.domain.models.OrderItem;
import com.geovannycode.store.orders.domain.models.OrderStatus;
import com.geovannycode.store.orders.web.InvalidOrderException;
import com.geovannycode.store.orders.web.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductServiceClient productServiceClient;
    private final ApplicationEventPublisher eventPublisher;


    public OrderEntity.OrderIdentifier createOrder(Customer customer, List<OrderItem> items) {

        if (customer == null || customer.getEmail() == null || customer.getEmail().isBlank()) {
            throw new InvalidOrderException("Customer email is required");
        }

        if (items == null || items.isEmpty()) {
            throw new InvalidOrderException("Order must contain at least one item");
        }


        OrderEntity order = new OrderEntity(customer);
        items.forEach(order::addItem);
        OrderEntity savedOrder = orderRepository.save(order);
        publishOrderCreatedEvent(savedOrder);
        return savedOrder.getId();
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


    private void publishOrderCreatedEvent(OrderEntity order) {
        List<OrderCreatedEvent.OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> new OrderCreatedEvent.OrderItemDto(
                        item.getProductId().getId(),
                        item.getQuantity(),
                        item.getUnitPrice()))
                .toList();

        OrderCreatedEvent event = new OrderCreatedEvent(
                order.getId().id(),
                order.getCreatedAt(),
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.calculateTotal(),
                itemDtos
        );

        eventPublisher.publishEvent(event);
    }
}
