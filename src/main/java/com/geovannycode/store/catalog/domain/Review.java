package com.geovannycode.store.catalog.command;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "reviews")
@Getter
@Setter
public class Review implements org.jmolecules.ddd.types.Entity<Product, ReviewIdentifier> {

    @EmbeddedId
    private ReviewIdentifier id;
    private Integer vote;
    private String comment;
    private String author;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    public Review() {
        this.id = new ReviewIdentifier(UUID.randomUUID());
        this.createdAt = LocalDateTime.now();
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
