package com.reservation.instructor.domain.exception;

public class InvalidInstructorNameException extends RuntimeException {

    public InvalidInstructorNameException(String message) {
        super(message);
    }
}
