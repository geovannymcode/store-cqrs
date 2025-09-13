package com.geovannycode.store.products.command;

import com.geovannycode.store.products.exception.InvalidVoteException;
import com.geovannycode.store.products.exception.ProductNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;


@Service
@Transactional
@RequiredArgsConstructor
public class ProductCommandService {

    private static final String PRODUCT_NOT_FOUND = "Product not found with id: ";
    private static final String INVALID_VOTE_RANGE = "Vote must be between 1 and 5";

    private final ProductRepository products;
    private final ApplicationEventPublisher eventPublisher;

    ProductIdentifier createProduct(String name, String description, BigDecimal price,
                                            Integer stock, String category) {

        // Paso 1: Crear el objeto Product
        var product = createProductEntity(name, description, price, stock, category);

        // Paso 2: Guardar en base de datos
        var saved = products.save(product);

        // Paso 3: Publicar evento (solo si el guardado fue exitoso)
        publishProductCreatedEvent(saved);

        // Paso 4: Devolver el ID
        return saved.getId();

    }

    /**
     * Actualiza un producto existente y publica un evento de actualización.
     *
     * @throws ProductNotFoundException si el producto no existe
     */
    void updateProduct(ProductIdentifier productId, String name, String description,
                       BigDecimal price, Integer stock, String category) {
        // Buscar y actualizar el producto
        var product = findProductOrThrow(productId);
        updateProductFields(product, name, description, price, stock, category);

        // Persistir y publicar evento
        products.save(product);
        publishProductUpdatedEvent(product);
    }

    /**
     * Agrega un review a un producto y publica un evento.
     *
     * @return El review creado
     * @throws ProductNotFoundException si el producto no existe
     * @throws InvalidVoteException si el voto está fuera de rango
     */
    Review addReview(ProductIdentifier productId, Integer vote, String comment) {
        validateVote(vote);

        // Crear review
        var review = createReview(vote, comment);

        // Buscar producto, agregar review y guardar
        var product = findProductOrThrow(productId)
                .add(review);
        products.save(product);

        // Publicar evento
        publishReviewAddedEvent(product.getId(), review);

        return review;
    }

    // ===== Métodos privados de soporte =====

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

    private void publishProductCreatedEvent(Product product) {
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
    }

    private void updateProductFields(Product product, String name, String description,
                                     BigDecimal price, Integer stock, String category) {
        product.setName(name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock(stock);
        product.setCategory(category);
    }

    private Review createReview(Integer vote, String comment) {
        var review = new Review();
        review.setVote(vote);
        review.setComment(comment);
        return review;
    }

    private void publishProductUpdatedEvent(Product product) {
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
    }
    private Product findProductOrThrow(ProductIdentifier productId) {
        return products.findById(productId)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND + productId));
    }

    private void validateVote(Integer vote) {
        if (vote == null || vote < 1 || vote > 5) {
            throw new InvalidVoteException(INVALID_VOTE_RANGE);
        }
    }

    private void publishReviewAddedEvent(ProductIdentifier productId, Review review) {
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
