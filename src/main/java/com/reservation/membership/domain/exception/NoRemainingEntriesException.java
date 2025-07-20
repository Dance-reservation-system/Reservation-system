package com.reservation.membership.domain.exception;

import java.io.Serial;

public class NoRemainingEntriesException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = 1L;

  public NoRemainingEntriesException() {
    super("No remaining entries to use");
  }
}
