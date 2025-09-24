package com.geovannycode.store.catalog.query;

import com.geovannycode.store.catalog.events.ProductEvents.ProductReviewed;
import com.geovannycode.store.catalog.events.ProductEvents.ProductUpdated;
import com.geovannycode.store.catalog.events.ProductEvents.ProductCreated;
import com.geovannycode.store.catalog.domain.ProductView;
import com.geovannycode.store.catalog.domain.ProductViewRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.jmolecules.event.annotation.DomainEventHandler;
import org.springframework.modulith.events.ApplicationModuleListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class ProductEventHandler {

    private final ProductViewRepository viewRepository;


    @DomainEventHandler
    @ApplicationModuleListener
    @Transactional
    void on(ProductCreated event) {
        var view = new ProductView();
        view.setId(event.id());
        view.setName(event.name());
        view.setDescription(event.description());
        view.setPrice(event.price());
        view.setStock(event.stock());
        view.setCategory(event.category());
        viewRepository.save(view);
    }


    @DomainEventHandler
    @ApplicationModuleListener
    @Transactional
    void on(ProductUpdated event) {
        viewRepository.findById(event.id()).ifPresent(view -> {
            view.setName(event.name());
            view.setDescription(event.description());
            view.setPrice(event.price());
            view.setStock(event.stock());
            view.setCategory(event.category());

            viewRepository.save(view);
        });
    }


    @ApplicationModuleListener
    @Transactional
    void on(ProductReviewed event) {
        viewRepository.findById(event.productId())
                .map(view -> view.on(event))
                .ifPresent(viewRepository::save);
    }
}