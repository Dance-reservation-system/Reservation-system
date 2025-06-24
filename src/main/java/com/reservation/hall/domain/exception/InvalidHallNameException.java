package com.reservation.hall.domain.exception;

import java.io.Serial;

public class InvalidHallNameException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;
  private static final String MESSAGE_TEMPLATE = "Hall name %s is invalid. %s";

  public InvalidHallNameException(String name, String message) {
      super(String.format(MESSAGE_TEMPLATE, name, message));
  }
}
