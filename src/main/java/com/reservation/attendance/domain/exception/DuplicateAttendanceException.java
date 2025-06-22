package com.reservation.attendance.domain.exception;

import java.io.Serial;

public class DuplicateAttendanceException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public DuplicateAttendanceException() {
        super("Attendance is already marked as present");
    }
}
