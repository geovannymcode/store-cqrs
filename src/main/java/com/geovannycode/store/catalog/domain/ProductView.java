package com.geovannycode.store.catalog.domain;

import com.geovannycode.store.catalog.events.ProductEvents.ProductReviewed;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.jmolecules.architecture.cqrs.QueryModel;
import org.jmolecules.ddd.types.AggregateRoot;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "product_views")
@Getter
@Setter
@QueryModel
public class ProductView implements AggregateRoot<ProductView, Product.ProductIdentifier> {

    @EmbeddedId
    private Product.ProductIdentifier id;
    private String name, description, category;
    private BigDecimal price;
    private Integer stock;

    @Setter(AccessLevel.NONE)
    private BigDecimal averageRating = BigDecimal.ZERO;

    @Setter(AccessLevel.NONE)
    private Integer reviewCount = 0;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public ProductView() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public ProductView on(ProductReviewed event) {
        BigDecimal currentTotal = averageRating.multiply(BigDecimal.valueOf(reviewCount));
        this.reviewCount = reviewCount + 1;
        this.averageRating = currentTotal.add(BigDecimal.valueOf(event.vote()))
                .divide(BigDecimal.valueOf(reviewCount), 2, java.math.RoundingMode.HALF_UP);
        return this;
    }
}
