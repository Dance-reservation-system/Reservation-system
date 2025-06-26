package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InstructorAlreadyActiveException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public InstructorAlreadyActiveException() {
      super("Instructor is already active");
  }
}
