package com.reservation.studio.domain.exception;

public class ContactDetailsCannotBeEmptyException extends RuntimeException {

    private static final String MESSAGE = "Contact details cannot be empty";

    public ContactDetailsCannotBeEmptyException() {
        super(MESSAGE);
    }
}
