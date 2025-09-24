package com.geovannycode.store.catalog.query;

import com.geovannycode.store.catalog.command.ProductIdentifier;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;

import java.math.BigDecimal;
import java.util.List;

interface ProductViewRepository extends ListCrudRepository<ProductView, ProductIdentifier> {

    List<ProductView> findByCategory(String category);

    @Query("SELECT pv FROM ProductView pv WHERE pv.price BETWEEN :minPrice AND :maxPrice")
    List<ProductView> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice);

    @Query("SELECT pv FROM ProductView pv WHERE pv.reviewCount > 0 ORDER BY pv.averageRating DESC")
    List<ProductView> findAllOrderByRatingDesc();
}
