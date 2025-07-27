package com.reservation.instructor.domain.exception;

public class InstructorAlreadyActivatedException extends RuntimeException {

    private static final String MESSAGE = "Instructor has already been activated";

    public InstructorAlreadyActivatedException() {
        super(MESSAGE);
    }
}
