package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class InstructorNameCannotBeBlankException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE_TEMPLATE = "Full name cannot be null or blank";

  public InstructorNameCannotBeBlankException() {
    super(MESSAGE_TEMPLATE);
  }
}
