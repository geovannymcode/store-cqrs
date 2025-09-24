package com.geovannycode.store.orders.mappers;

import com.geovannycode.store.orders.domain.OrderEntity;
import com.geovannycode.store.orders.domain.models.Customer;
import com.geovannycode.store.orders.domain.models.OrderItem;
import com.geovannycode.store.orders.web.CreateOrderRequest;
import com.geovannycode.store.orders.web.OrderDto;
import com.geovannycode.store.products.command.ProductIdentifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class OrderMapper {

    public Customer toCustomer(CreateOrderRequest.CustomerDto customerDto) {
        return new Customer(
                customerDto.name(),
                customerDto.email(),
                customerDto.address()
        );
    }

    public List<OrderItem> toOrderItems(List<CreateOrderRequest.OrderItemDto> itemDtos) {
        return itemDtos.stream()
                .map(dto -> {
                    OrderItem item = new OrderItem(
                            new ProductIdentifier(dto.productId()),
                            dto.productName(),
                            dto.quantity(),
                            dto.unitPrice()
                    );
                    return item;
                })
                .toList();
    }

    public OrderDto toOrderDto(OrderEntity order) {
        OrderDto.CustomerDto customerDto = new OrderDto.CustomerDto(
                order.getCustomer().getName(),
                order.getCustomer().getEmail(),
                order.getCustomer().getAddress()
        );

        List<OrderDto.OrderItemDto> itemDtos = order.getItems().stream()
                .map(item -> new OrderDto.OrderItemDto(
                        item.getProductId().getId(),
                        item.getProductName(),
                        item.getQuantity(),
                        item.getUnitPrice(),
                        item.getSubtotal()
                ))
                .toList();

        return new OrderDto(
                order.getId().id(),
                order.getCreatedAt(),
                order.getStatus().name(),
                customerDto,
                itemDtos,
                order.calculateTotal()
        );
    }
}
