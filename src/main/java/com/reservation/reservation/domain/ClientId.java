package com.reservation.reservation.domain;

import java.util.Objects;
import java.util.UUID;

record ClientId(UUID value) {
    public ClientId {
        Objects.requireNonNull(value);
    }

    public static ClientId next(){
        return new ClientId(UUID.randomUUID());
    }
}
