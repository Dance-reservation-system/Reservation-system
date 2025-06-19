package com.reservation.attendance.domain;

import java.time.LocalDateTime;

public class Attendance {
    private AttendanceId id;
    private ClientId clientId;
    private SessionOccurrenceId sessionOccurrenceId;
    private LocalDateTime confirmedAt;
    private AttendanceStatus status;




}
