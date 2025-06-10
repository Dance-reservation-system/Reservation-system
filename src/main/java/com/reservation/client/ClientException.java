package com.reservation.client;

import com.reservation.common.exception.ReservationSystemHttpException;
import org.springframework.http.HttpStatus;

class ClientException extends ReservationSystemHttpException {
    private static final String CLIENT_NOT_FOUND = "Client with id: %s not found";
    private static final String CLIENT_ALREADY_EXIST = "Client with e-mail: %s already exists";

    private ClientException(HttpStatus status, String message) {
        super(status, message);
    }

    static ClientException clientNotFound(Long id) {
        return new ClientException(HttpStatus.NOT_FOUND, CLIENT_NOT_FOUND.formatted(id));
    }

    static ClientException clientAlreadyExist(String email) {
        return new ClientException(HttpStatus.BAD_REQUEST, CLIENT_ALREADY_EXIST.formatted(email));
    }
}
