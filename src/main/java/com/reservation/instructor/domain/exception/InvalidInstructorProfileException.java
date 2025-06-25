package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InvalidInstructorProfileException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public InvalidInstructorProfileException(String message) {
        super(message);
    }
}
