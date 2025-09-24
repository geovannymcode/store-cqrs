package com.geovannycode.store.orders.domain.models;

import com.geovannycode.store.common.ProductIdentifierAttributeConverter;
import com.geovannycode.store.products.command.ProductIdentifier;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class OrderItem {


    @Convert(converter = ProductIdentifierAttributeConverter.class)
    @Column(name = "product_id", nullable = false, columnDefinition = "uuid")
    private ProductIdentifier productId;

    @Column(name = "product_name", nullable = false, length = 180)
    private String productName;

    @Column(name = "quantity", nullable = false)
    private Integer quantity;

    @Column(name = "unit_price", nullable = false, precision = 19, scale = 2)
    private BigDecimal unitPrice;

    @Transient
    public BigDecimal getSubtotal() {
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
}