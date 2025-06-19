package com.reservation.attendance.domain.exception;

public class AttendanceAlreadyCancelledException extends RuntimeException {
    public AttendanceAlreadyCancelledException(String message) {
        super(message);
    }
}
