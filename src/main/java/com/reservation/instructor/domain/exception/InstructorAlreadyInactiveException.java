package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InstructorAlreadyInactiveException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE_TEMPLATE = "Instructor is already inactive";

  public InstructorAlreadyInactiveException() {
    super(MESSAGE_TEMPLATE);
  }
}
