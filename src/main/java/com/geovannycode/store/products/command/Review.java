package com.geovannycode.store.products.command;

import lombok.Getter;
import lombok.Setter;
import org.jmolecules.ddd.types.Entity;

import java.util.UUID;

@Getter
@Setter
public class Review implements Entity<Product, ReviewIdentifier> {
    private ReviewIdentifier id;
    private Integer vote;
    private String comment;
    private String author;

    public Review() {
        this.id = new ReviewIdentifier(UUID.randomUUID());
    }

    /**
     * Validar que el voto esté en rango válido.
     */
    public void setVote(Integer vote) {
        if (vote == null || vote < 1 || vote > 5) {
            throw new IllegalArgumentException("Vote must be between 1 and 5");
        }
        this.vote = vote;
    }
}
