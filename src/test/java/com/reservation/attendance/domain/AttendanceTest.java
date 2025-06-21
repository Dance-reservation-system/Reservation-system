package com.reservation.attendance.domain;


import com.reservation.attendance.domain.exception.AttendanceAlreadyCancelledException;
import com.reservation.attendance.domain.exception.DuplicateAttendanceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;


class AttendanceTest {

    AttendanceId attendanceId;
    ClientId clientId;
    SessionOccurrenceId sessionId;
    Attendance attendance;

    @BeforeEach
    void setUp() {
        attendanceId = new AttendanceId(UUID.randomUUID());
        clientId = new ClientId(UUID.randomUUID());
        sessionId = new SessionOccurrenceId(UUID.randomUUID());
        attendance = new Attendance(attendanceId, clientId, sessionId, null);
    }

    @Test
    void markingClientAsPresentShouldSucceed(){
        //Given & When
        attendance.markPresent();

        // Then
        assertEquals(AttendanceStatus.PRESENT, attendance.getStatus());
        assertTrue(attendance.isPresent());
        assertNotNull(attendance.getConfirmedAt());
    }

    @Test
    void markPresentShouldThrowExceptionWhenIsAlreadyPresent(){
        //Given
        attendance.markPresent();

        //When & Then
        assertThrows(DuplicateAttendanceException.class,
                () -> attendance.markPresent());
    }

    @Test
    void cancelAttendanceShouldSucceed(){
        //Given
        attendance.markPresent();

        // When
        attendance.cancel();

        //Then
        assertEquals(AttendanceStatus.CANCELLED, attendance.getStatus());
    }

    @Test
    void shouldThrowExceptionWhenCancellingAlreadyCancelledAttendance(){
        //Given
        attendance.markPresent();

        // When
        attendance.cancel();

        //Then
        assertThrows(AttendanceAlreadyCancelledException.class, attendance::cancel);
    }

    @Test
    void shouldConfirmPresenceFlag(){
        //Given
       attendance.markPresent();

        //When
        boolean present = attendance.isPresent();

        //Then
        assertTrue(present);
    }

    @Test
    void confirmedAtShouldNotBeNullWhenMarkedPresent() {
        // Given
        attendance.markPresent();

        // When
        LocalDateTime confirmedAtFromAttendance = attendance.getConfirmedAt();

        // Then
        assertNotNull(confirmedAtFromAttendance, "confirmedAt should not be null when attendance is marked present");
    }
}
