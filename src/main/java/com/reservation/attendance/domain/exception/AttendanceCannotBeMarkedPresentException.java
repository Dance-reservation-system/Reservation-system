package com.reservation.attendance.domain.exception;

import java.io.Serial;

public class AttendanceCannotBeMarkedPresentException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AttendanceCannotBeMarkedPresentException(String status) {
        super("Attendance cannot be marked as present from status: " + status);
    }
}