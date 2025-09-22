package com.geovannycode.store.orders.domain;

import com.geovannycode.store.orders.domain.models.Customer;
import com.geovannycode.store.orders.domain.models.OrderItem;
import com.geovannycode.store.orders.domain.models.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.types.AggregateRoot;
import org.jmolecules.ddd.types.Identifier;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderEntity implements AggregateRoot<OrderEntity, OrderEntity.OrderIdentifier> {

    private OrderIdentifier id;
    private LocalDateTime createdAt;
    private OrderStatus status;
    private Customer customer;
    private List<OrderItem> items = new ArrayList<>();

    public OrderEntity(Customer customer) {
        this.id = new OrderIdentifier(UUID.randomUUID());
        this.createdAt = LocalDateTime.now();
        this.status = OrderStatus.CREATED;
        this.customer = customer;
    }

    public OrderEntity addItem(OrderItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        // Lógica de negocio: no se puede añadir items a pedidos ya procesados
        if (status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot add items to order in status: " + status);
        }

        this.items.add(item);
        return this;
    }

    public BigDecimal calculateTotal() {
        return items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }


    public OrderEntity updateStatus(OrderStatus newStatus) {
        if (newStatus == OrderStatus.CREATED && this.status != OrderStatus.CREATED) {
            throw new IllegalStateException("Cannot change back to CREATED status");
        }

        if (newStatus == OrderStatus.CANCELLED && !this.status.canBeCancelled()) {
            throw new IllegalStateException("Order cannot be cancelled in status: " + this.status);
        }

        this.status = newStatus;
        return this;
    }

    public record OrderIdentifier(UUID id) implements Identifier {
        public OrderIdentifier {
            if (id == null) {
                throw new IllegalArgumentException("Order ID cannot be null");
            }
        }
    }
}
