package com.reservation.instructor.domain.exception;

import java.io.Serial;

public class ProfileCannotBeUpdatedWhenInactiveException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE_TEMPLATE = "Profile cannot be updated when inactive";

  public ProfileCannotBeUpdatedWhenInactiveException() {
      super(MESSAGE_TEMPLATE);
  }
}
