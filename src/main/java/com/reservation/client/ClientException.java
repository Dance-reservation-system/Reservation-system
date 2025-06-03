package com.reservation.client;

import com.reservation.common.exception.ReservationSystemHttpException;
import org.springframework.http.HttpStatus;

class ClientException extends ReservationSystemHttpException {
    private static final String CLIENT_NOT_FOUND = "Client with id: %s not found";

    private ClientException(HttpStatus status, String message) {
        super(status, message);
    }

    static ClientException clientNotFound(Long id) {
        return new ClientException(HttpStatus.NOT_FOUND, String.format(CLIENT_NOT_FOUND, id));
    }
}
