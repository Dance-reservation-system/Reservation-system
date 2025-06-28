package com.reservation.attendance.domain.exception;

import java.io.Serial;

public class AttendanceCannotBeCancelledException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AttendanceCannotBeCancelledException(String status) {
        super("Attendance cannot be cancelled from status: " + status);
    }
}