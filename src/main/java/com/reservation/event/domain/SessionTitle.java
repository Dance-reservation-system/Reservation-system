package com.reservation.event.domain;

import com.reservation.event.domain.exception.InvalidSessionTitleException;

record SessionTitle(String value) {
    public SessionTitle {
        if(value == null || value.isBlank()) {
            throw new InvalidSessionTitleException("SessionTitle value cannot be null or empty");
        }
        if (value.length() > 100) {
            throw new InvalidSessionTitleException("SessionTitle value cannot be longer than 100 characters");
        }
    }
}
