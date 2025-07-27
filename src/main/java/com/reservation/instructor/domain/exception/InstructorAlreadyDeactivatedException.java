package com.reservation.instructor.domain.exception;

public class InstructorAlreadyDeactivatedException extends RuntimeException {

    private static final String MESSAGE = "Instructor has already been deactivated";

    public InstructorAlreadyDeactivatedException() {
        super(MESSAGE);
    }
}
