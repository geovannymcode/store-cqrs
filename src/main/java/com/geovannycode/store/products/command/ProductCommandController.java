package com.geovannycode.store.products.command;

import com.geovannycode.store.products.dto.command.AddReviewRequest;
import com.geovannycode.store.products.dto.command.CreateProductRequest;
import com.geovannycode.store.products.dto.command.UpdateProductRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductCommandController {

    private final ProductCommandService commandService;

    @PostMapping
    ResponseEntity<ProductIdentifier> createProduct(@RequestBody CreateProductRequest request) {

        var id = commandService.createProduct(
                request.name(), request.description(), request.price(),
                request.stock(), request.category()
        );

        return ResponseEntity
                .created(URI.create("/api/products/" + id.getId()))
                .body(id);
    }

    @PutMapping("/{id}")
    ResponseEntity<Void> updateProduct(
            @PathVariable ProductIdentifier id,
            @RequestBody UpdateProductRequest request) {

        commandService.updateProduct(
                id, request.name(), request.description(), request.price(),
                request.stock(), request.category()
        );

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/reviews")
    ResponseEntity<ReviewIdentifier> addReview(
            @PathVariable ProductIdentifier id,
            @RequestBody AddReviewRequest request) {

        var review = commandService.addReview(id, request.vote(), request.comment());
        var reviewId = review.getId();

        return ResponseEntity
                .created(URI.create("/api/products/" + id.getId()+ "/reviews/" + reviewId.id()))
                .body(reviewId);
    }

}
