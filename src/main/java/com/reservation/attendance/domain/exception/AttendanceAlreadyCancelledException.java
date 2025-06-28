package com.reservation.attendance.domain.exception;

import java.io.Serial;

public class AttendanceAlreadyCancelledException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public AttendanceAlreadyCancelledException() {
        super("Attendance has already been cancelled");
    }
}
