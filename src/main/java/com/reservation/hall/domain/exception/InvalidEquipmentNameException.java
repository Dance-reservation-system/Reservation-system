package com.reservation.hall.domain.exception;

import java.io.Serial;

public class InvalidEquipmentNameException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 1L;
    private static final String MESSAGE_TEMPLATE = "Equipment name '%s' is invalid";

    public InvalidEquipmentNameException(String name) {
        super(String.format(MESSAGE_TEMPLATE, name));
    }
}
