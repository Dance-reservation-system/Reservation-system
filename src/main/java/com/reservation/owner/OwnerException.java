package com.reservation.owner;

import com.reservation.common.exception.ReservationSystemHttpException;
import org.springframework.http.HttpStatus;

class OwnerException extends ReservationSystemHttpException {
    private static final String OWNER_NOT_FOUND = "Owner with id: %s not found";
    private static final String OWNER_ALREADY_EXISTS = "Owner with keycloak user id: %s already exists";

    private OwnerException(HttpStatus status, String message) {
        super(status, message);
    }

    static OwnerException ownerNotFound(Long id) {
        return new OwnerException(HttpStatus.NOT_FOUND, OWNER_NOT_FOUND.formatted(id));
    }

    static OwnerException ownerAlreadyExists(String email) {
        return new OwnerException(HttpStatus.BAD_REQUEST, OWNER_ALREADY_EXISTS.formatted(email));
    }
}
