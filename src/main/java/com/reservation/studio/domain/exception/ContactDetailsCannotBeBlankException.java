package com.reservation.studio.domain.exception;

public class ContactDetailsCannotBeBlankException extends RuntimeException {

    private static final String MESSAGE = "Contact details cannot be empty";

    public ContactDetailsCannotBeBlankException() {
        super(MESSAGE);
    }
}
