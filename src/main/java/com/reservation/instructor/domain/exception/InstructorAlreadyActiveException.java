package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InstructorAlreadyActiveException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE_TEMPLATE = "Instructor is already active";

  public InstructorAlreadyActiveException() {
      super(MESSAGE_TEMPLATE);
  }
}
