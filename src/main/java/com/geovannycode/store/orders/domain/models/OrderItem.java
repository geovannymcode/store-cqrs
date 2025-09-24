package com.geovannycode.store.orders.domain.models;

import com.geovannycode.store.orders.domain.OrderEntity;
import com.geovannycode.store.products.command.ProductIdentifier;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jmolecules.ddd.types.Identifier;
import org.jmolecules.ddd.types.Entity;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderItem implements Entity<OrderEntity, OrderItem.OrderItemIdentifier> {

    private OrderItemIdentifier id;
    private ProductIdentifier productId;
    private String productName;
    private Integer quantity;
    private BigDecimal unitPrice;

    public OrderItem(ProductIdentifier productId, String productName, Integer quantity, BigDecimal unitPrice) {
        this.id = new OrderItemIdentifier(UUID.randomUUID());
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }


    public BigDecimal getSubtotal() {
        return unitPrice.multiply(new BigDecimal(quantity));
    }

    public record OrderItemIdentifier(UUID id) implements Identifier {
        public OrderItemIdentifier {
            if (id == null) {
                throw new IllegalArgumentException("OrderItem ID cannot be null");
            }
        }
    }
}
