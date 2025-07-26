package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.ContactDetailsCannotBeBlankException;

import java.util.Objects;

record ContactDetails(String address, String phoneNumber) {
    ContactDetails {
        Objects.requireNonNull(address);
        Objects.requireNonNull(phoneNumber);

        if (address.isBlank() || phoneNumber.isBlank()) {
            throw new ContactDetailsCannotBeBlankException();
        }
    }
}
