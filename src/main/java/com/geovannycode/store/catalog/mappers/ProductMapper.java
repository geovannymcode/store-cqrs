package com.geovannycode.store.catalog.mappers;

import com.geovannycode.store.catalog.domain.Product;
import com.geovannycode.store.catalog.domain.ProductView;
import com.geovannycode.store.catalog.domain.Review;
import com.geovannycode.store.catalog.events.ProductEvents;
import com.geovannycode.store.catalog.web.dto.AddReviewRequest;
import com.geovannycode.store.catalog.web.dto.CreateProductRequest;
import com.geovannycode.store.catalog.web.dto.UpdateProductRequest;

import java.util.UUID;
import java.util.function.Function;

public final class ProductMapper {
    private ProductMapper() {
// Private constructor to prevent instantiation
    }
    /**

     Maps CreateProductRequest to Product entity.
     */
    public static Function<CreateProductRequest, Product> toEntity() {
        return request -> {
            Product product = new Product();
            product.setName(request.name());
            product.setDescription(request.description());
            product.setPrice(request.price());
            product.setStock(request.stock());
            product.setCategory(request.category());
            return product;
        };
    }

    /**

     Creates a function that updates a Product entity with values from UpdateProductRequest.
     */
    public static Function<UpdateProductRequest, Function<Product, Product>> updateEntity() {
        return request -> product -> {
            product.setName(request.name());
            product.setDescription(request.description());
            product.setPrice(request.price());
            product.setStock(request.stock());
            product.setCategory(request.category());
            return product;
        };
    }

    /**

     Creates a Review from AddReviewRequest.
     */
    public static Function<AddReviewRequest, Review> toReview() {
        return request -> {
            Review review = new Review();
            review.setVote(request.vote());
            review.setComment(request.comment());
            return review;
        };
    }

    /**

     Creates a ProductCreated event from a Product entity.
     */
    public static Function<Product, ProductEvents.ProductCreated> toCreatedEvent() {
        return product -> new ProductEvents.ProductCreated(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory()
        );
    }

    /**

     Creates a ProductUpdated event from a Product entity.
     */
    public static Function<Product, ProductEvents.ProductUpdated> toUpdatedEvent() {
        return product -> new ProductEvents.ProductUpdated(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCategory(),
                UUID.randomUUID().toString()
        );
    }

    /**

     Maps a Product entity to ProductView.
     */
    public static Function<Product, ProductView> toView() {
        return product -> {
            ProductView view = new ProductView();
            view.setId(product.getId());
            view.setName(product.getName());
            view.setDescription(product.getDescription());
            view.setPrice(product.getPrice());
            view.setStock(product.getStock());
            view.setCategory(product.getCategory());
            return view;
        };
    }

    /**

     Maps ProductCreated event to ProductView.
     */
    public static Function<ProductEvents.ProductCreated, ProductView> eventToView() {
        return event -> {
            ProductView view = new ProductView();
            view.setId(event.id());
            view.setName(event.name());
            view.setDescription(event.description());
            view.setPrice(event.price());
            view.setStock(event.stock());
            view.setCategory(event.category());
            return view;
        };
    }
}

