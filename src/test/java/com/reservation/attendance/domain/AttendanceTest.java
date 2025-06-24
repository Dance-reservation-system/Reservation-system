package com.reservation.attendance.domain;


import com.reservation.attendance.domain.exception.AttendanceAlreadyCancelledException;
import com.reservation.attendance.domain.exception.AttendanceCannotBeCancelledException;
import com.reservation.attendance.domain.exception.AttendanceCannotBeMarkedPresentException;
import com.reservation.attendance.domain.exception.DuplicateAttendanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;


class AttendanceTest {

    AttendanceId attendanceId;
    ClientId clientId;
    SessionOccurrenceId sessionId;
    Attendance attendance;

    @BeforeEach
    void setUp() {
        attendanceId = AttendanceId.next();
        clientId = ClientId.next();
        sessionId = SessionOccurrenceId.next();
        attendance = new Attendance(attendanceId, clientId, sessionId);
    }

    @Test
    void shouldMarkClientAsPresentWhenNotAlreadyPresent(){
        // Given & When
        attendance.markPresent();

        // Then
        assertEquals(AttendanceStatus.PRESENT, attendance.getStatus());
        assertTrue(attendance.isPresent());
        assertNotNull(attendance.getConfirmedAt());
    }

    @Test
    void shouldThrowExceptionWhenMarkingClientAsPresentTwice(){
        // Given
        attendance.markPresent();

        // When & Then
        assertThrows(DuplicateAttendanceException.class,
                () -> attendance.markPresent());
    }

    @Test
    void shouldCancelAttendanceWhenInCreatedStatus() {
        // When
        attendance.cancel();

        // Then
        assertEquals(AttendanceStatus.CANCELLED, attendance.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCancellingPresentAttendance() {
        // Given
        attendance.markPresent();

        // When & Then
        assertThrows(AttendanceCannotBeCancelledException.class, attendance::cancel);
    }

    @Test
    void shouldThrowExceptionWhenCancellingAlreadyCancelledAttendance() {
        // Given
        attendance.cancel();

        // When & Then
        assertThrows(AttendanceAlreadyCancelledException.class, attendance::cancel);
    }

    @Test
    void shouldConfirmPresenceFlag() {
        // When
        attendance.markPresent();

        // Then
        assertTrue(attendance.isPresent());
    }

    @Test
    void shouldSetConfirmedAtWhenAttendanceIsMarkedPresent() {
        // When
        attendance.markPresent();

        // Then
        assertNotNull(attendance.getConfirmedAt());
    }

    @Test
    void shouldThrowExceptionWhenMarkingCancelledAttendanceAsPresent() {
        // Given
        attendance.cancel();

        // When & Then
        assertThrows(AttendanceCannotBeMarkedPresentException.class, attendance::markPresent);
    }
}