package com.reservation.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@MappedSuperclass
@Getter
@Setter
public class AbstractEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "created_at", nullable = false)
    protected Instant createdAt;

    @Column(name = "updated_at")
    protected Instant updatedAt;

    @PrePersist
    void prePersist() {
        this.createdAt = Instant.now();
    }

    @PreUpdate
    void setUpdateDate() {
        this.updatedAt = Instant.now();
    }
}
