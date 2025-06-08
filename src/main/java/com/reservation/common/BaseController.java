package com.reservation.common;

import com.reservation.common.exception.ReservationSystemHttpException;
import com.reservation.common.exception.ReservationSystemHttpExceptionResponse;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public abstract class BaseController {

    protected abstract Logger getLogger();

    @ExceptionHandler(ReservationSystemHttpException.class)
    public ResponseEntity<ReservationSystemHttpExceptionResponse> handleReservationSystemHttpException(ReservationSystemHttpException e) {
        getLogger().error(HttpStatus.valueOf(e.getHttpStatus().value()).getReasonPhrase(), e);
        return new ResponseEntity<>(e.getErrorMessage(), e.getHttpStatus());
    }
}
