package com.reservation.instructor.domain.exception;

public class InstructorProfileCannotBeUpdatedWhenInactiveException extends RuntimeException {
    private static final String MESSAGE = "Instructor profile cannot be updated if instructor is inactive";

    public InstructorProfileCannotBeUpdatedWhenInactiveException() {
        super(MESSAGE);
    }
}
