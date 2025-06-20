package com.reservation.attendance.domain.exception;

public class DuplicateAttendanceException extends RuntimeException {
    public DuplicateAttendanceException() {
        super("Attendance is already marked as present");
    }
}
