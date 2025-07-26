package com.reservation.studio.domain;

import com.reservation.studio.domain.exception.ContactDetailsCannotBeEmptyException;

import java.util.Objects;

record ContactDetails(String address, String phoneNumber) {
    ContactDetails {
        Objects.requireNonNull(address);
        Objects.requireNonNull(phoneNumber);

        if(address.isEmpty() || phoneNumber.isEmpty()){
            throw new ContactDetailsCannotBeEmptyException();
        }
    }
}
