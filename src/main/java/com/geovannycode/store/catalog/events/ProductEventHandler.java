package com.geovannycode.store.catalog.events;

import com.geovannycode.store.catalog.domain.ProductQueryService;
import com.geovannycode.store.catalog.domain.ProductView;
import com.geovannycode.store.catalog.events.ProductEvents.ProductReviewed;
import com.geovannycode.store.catalog.events.ProductEvents.ProductUpdated;
import com.geovannycode.store.catalog.events.ProductEvents.ProductCreated;
import com.geovannycode.store.catalog.mappers.ProductMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

/**
 * Handles domain events to maintain the query model.
 */
@Component
@RequiredArgsConstructor
class ProductEventHandler {

    private final ProductQueryService queryService;

    @DomainEventHandler
    @ApplicationModuleListener
    @Transactional
    void on(ProductCreated event) {
        ProductView view = ProductMapper.eventToView().apply(event);
        queryService.saveProductView(view);
    }

    @DomainEventHandler
    @ApplicationModuleListener
    @Transactional
    void on(ProductUpdated event) {
        queryService.findProductById(event.id())
                .ifPresent(view -> {
                    view.setName(event.name());
                    view.setDescription(event.description());
                    view.setPrice(event.price());
                    view.setStock(event.stock());
                    view.setCategory(event.category());

                    queryService.saveProductView(view);
                });
    }

    @ApplicationModuleListener
    @Transactional
    void on(ProductReviewed event) {
        queryService.findProductById(event.productId())
                .map(view -> view.on(event))
                .ifPresent(queryService::saveProductView);
    }
}