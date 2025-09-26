package com.geovannycode.store.catalog.domain;

import com.geovannycode.store.catalog.events.ProductEvents;
import com.geovannycode.store.catalog.exception.InvalidVoteException;
import com.geovannycode.store.catalog.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService {

    private static final String PRODUCT_NOT_FOUND = "Product not found with id: ";
    private static final String INVALID_VOTE_RANGE = "Vote must be between 1 and 5";

    private final ProductRepository products;
    private final ApplicationEventPublisher eventPublisher;

    /**
     * Creates a new product and publishes an event.
     *
     * @return Product identifier of the created product
     */
    public Product.ProductIdentifier createProduct(String name, String description, BigDecimal price,
                                                   Integer stock, String category) {
        // Create and save product using functional approach
        return Optional.ofNullable(createProductEntity(name, description, price, stock, category))
                .map(products::save)
                .map(this::publishProductCreatedEvent)
                .map(Product::getId)
                .orElseThrow(() -> new IllegalStateException("Failed to create product"));
    }

    /**
     * Updates an existing product and publishes an event.
     *
     * @throws ProductNotFoundException if the product does not exist
     */
    public void updateProduct(Product.ProductIdentifier productId, String name, String description,
                              BigDecimal price, Integer stock, String category) {
        products.findById(productId)
                .map(product -> updateProductFields(product, name, description, price, stock, category))
                .map(products::save)
                .map(this::publishProductUpdatedEvent)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));
    }

    /**
     * Adds a review to a product and publishes an event.
     *
     * @return The review created
     * @throws ProductNotFoundException if the product does not exist
     * @throws InvalidVoteException if the vote is invalid
     */
    public Review addReview(Product.ProductIdentifier productId, Integer vote, String comment) {
        validateVote(vote);

        // Create review
        final Review review = createReview(vote, comment);

        return products.findById(productId)
                .map(product -> product.add(review))
                .map(products::save)
                .map(product -> {
                    publishReviewAddedEvent(product.getId(), review);
                    return review;
                })
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));
    }

    // ===== Private methods =====

    private Product createProductEntity(String name, String description,
                                        BigDecimal price, Integer stock, String category) {
        var product = new Product();
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        return product;
    }

    private Product publishProductCreatedEvent(Product product) {
        eventPublisher.publishEvent(
                new ProductEvents.ProductCreated(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        product.getCategory()
                )
        );
        return product;
    }

    private Product updateProductFields(Product product, String name, String description,
                                        BigDecimal price, Integer stock, String category) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
        return product;
    }

    private Review createReview(Integer vote, String comment) {
        var review = new Review();
        review.setVote(vote);
        review.setComment(comment);
        return review;
    }

    private Product publishProductUpdatedEvent(Product product) {
        eventPublisher.publishEvent(
                new ProductEvents.ProductUpdated(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        product.getPrice(),
                        product.getStock(),
                        product.getCategory(),
                        UUID.randomUUID().toString()
                )
        );
        return product;
    }

    private void validateVote(Integer vote) {
        if (vote == null || vote < 1 || vote > 5) {
            throw new InvalidVoteException(INVALID_VOTE_RANGE);
        }
    }

    private void publishReviewAddedEvent(Product.ProductIdentifier productId, Review review) {
        eventPublisher.publishEvent(
                new ProductEvents.ProductReviewed(
                        productId,
                        review.getId(),
                        review.getVote(),
                        review.getComment()
                )
        );
    }

}
