package com.reservation.attendance.domain;

import com.reservation.attendance.domain.exception.AttendanceAlreadyCancelledException;
import com.reservation.attendance.domain.exception.AttendanceCannotBeCancelledException;
import com.reservation.attendance.domain.exception.DuplicateAttendanceException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Attendance {
    @EqualsAndHashCode.Include
    private final AttendanceId id;
    private final ClientId clientId;
    private final SessionOccurrenceId sessionOccurrenceId;
    private LocalDateTime confirmedAt;
    private AttendanceStatus status;

    Attendance(AttendanceId id, ClientId clientId,
               SessionOccurrenceId sessionOccurrenceId) {
        this.id = Objects.requireNonNull(id);
        this.clientId = Objects.requireNonNull(clientId);
        this.sessionOccurrenceId = Objects.requireNonNull(sessionOccurrenceId);
        this.status = AttendanceStatus.CREATED;
    }

    public void markPresent() {
        if (status == AttendanceStatus.PRESENT) {
            throw new DuplicateAttendanceException();
        }
        if (status == AttendanceStatus.CREATED) {
            this.confirmedAt = LocalDateTime.now();
            this.status = AttendanceStatus.PRESENT;
        }
    }

    public void cancel() {
        if (status == AttendanceStatus.CANCELLED) {
            throw new AttendanceAlreadyCancelledException();
        }
        if (status != AttendanceStatus.CREATED) {
            throw new AttendanceCannotBeCancelledException(status.name());
        }
        this.status = AttendanceStatus.CANCELLED;
    }

    public boolean isPresent() {
        return status == AttendanceStatus.PRESENT;
    }
}
