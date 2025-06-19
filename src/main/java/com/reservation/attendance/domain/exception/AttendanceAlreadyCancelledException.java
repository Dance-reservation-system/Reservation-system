package com.reservation.attendance.domain.exception;

public class AttendanceAlreadyCancelledException extends RuntimeException {
    public AttendanceAlreadyCancelledException() {
        super("Attendance has been cancelled");
    }
}
