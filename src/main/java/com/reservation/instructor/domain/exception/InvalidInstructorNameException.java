package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InvalidInstructorNameException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public InvalidInstructorNameException(String message) {
    super(message);
  }
}
