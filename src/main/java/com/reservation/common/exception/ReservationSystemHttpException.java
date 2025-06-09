package com.reservation.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatusCode;

@Getter
public class ReservationSystemHttpException extends RuntimeException {
    protected final HttpStatusCode httpStatus;
    protected final ReservationSystemHttpExceptionResponse errorMessage;

    protected ReservationSystemHttpException(HttpStatusCode httpStatus, String errorMessage) {
        super(errorMessage);
        this.httpStatus = httpStatus;
        this.errorMessage = new ReservationSystemHttpExceptionResponse(errorMessage);
    }

    protected ReservationSystemHttpException(HttpStatusCode httpStatus, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.httpStatus = httpStatus;
        this.errorMessage = new ReservationSystemHttpExceptionResponse(errorMessage);
    }
}
