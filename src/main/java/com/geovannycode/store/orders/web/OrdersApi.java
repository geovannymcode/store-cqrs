package com.geovannycode.store.orders.web;

import com.geovannycode.store.orders.domain.OrderEntity;
import com.geovannycode.store.orders.domain.OrderService;
import com.geovannycode.store.orders.domain.models.Customer;
import com.geovannycode.store.orders.domain.models.OrderItem;
import com.geovannycode.store.orders.domain.models.OrderStatus;
import com.geovannycode.store.orders.mappers.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersApi {

    private final OrderService orderService;
    private final OrderMapper orderMapper;


    @PostMapping
    public ResponseEntity<CreateOrderResponse> createOrder(@RequestBody CreateOrderRequest request) {

        Customer customer = orderMapper.toCustomer(request.customer());
        List<OrderItem> items = orderMapper.toOrderItems(request.items());

        OrderEntity.OrderIdentifier orderId = orderService.createOrder(customer, items);

        CreateOrderResponse response = new CreateOrderResponse(
                orderId.id(),
                "CREATED",
                "Order created successfully"
        );

        return ResponseEntity
                .created(URI.create("/api/orders/" + orderId.id()))
                .body(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable UUID id) {
        OrderEntity.OrderIdentifier orderId = new OrderEntity.OrderIdentifier(id);
        OrderEntity order = orderService.findById(orderId);
        OrderDto orderDto = orderMapper.toOrderDto(order);
        return ResponseEntity.ok(orderDto);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDto> updateOrderStatus(
            @PathVariable UUID id,
            @RequestParam OrderStatus status) {

        OrderEntity.OrderIdentifier orderId = new OrderEntity.OrderIdentifier(id);
        OrderEntity updatedOrder = orderService.updateOrderStatus(orderId, status);
        OrderDto orderDto = orderMapper.toOrderDto(updatedOrder);
        return ResponseEntity.ok(orderDto);
    }


    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<String> handleOrderNotFound(OrderNotFoundException ex) {
        return ResponseEntity.notFound().build();
    }

    @ExceptionHandler(InvalidOrderException.class)
    public ResponseEntity<String> handleInvalidOrder(InvalidOrderException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
