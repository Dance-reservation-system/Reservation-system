package com.reservation.studio.domain.exception;

import java.time.Duration;

public class InvalidCancellationThresholdException extends RuntimeException {

    private static final String MESSAGE = "Cancellation notice period must be a positive duration. But was: %s";

    public InvalidCancellationThresholdException(Duration duration) {
        super(String.format(MESSAGE, duration));
    }
}
