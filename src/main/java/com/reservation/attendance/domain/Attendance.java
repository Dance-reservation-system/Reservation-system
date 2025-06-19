package com.reservation.attendance.domain;

import com.reservation.attendance.domain.exception.AttendanceAlreadyCancelledException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Attendance {
    @EqualsAndHashCode.Include
    private final AttendanceId id;
    private final ClientId clientId;
    private final SessionOccurrenceId sessionOccurrenceId;
    private final LocalDateTime confirmedAt;
    private AttendanceStatus status;

    Attendance(AttendanceId id, ClientId clientId,
                      SessionOccurrenceId sessionOccurrenceId,
                      LocalDateTime confirmedAt, AttendanceStatus status) {
        this.id = Objects.requireNonNull(id);
        this.clientId = Objects.requireNonNull(clientId);
        this.sessionOccurrenceId = Objects.requireNonNull(sessionOccurrenceId);
        this.confirmedAt = confirmedAt;
        this.status = status;
    }

    public static Attendance markPresent(AttendanceId id, ClientId clientId,
                                         SessionOccurrenceId sessionOccurrenceId,
                                         LocalDateTime confirmedAt){
        if(confirmedAt == null){
            throw new IllegalArgumentException("confirmedAt cannot be null when marking attendance");
        }

        return new Attendance(id,clientId,sessionOccurrenceId,confirmedAt,AttendanceStatus.PRESENT);
    }

    void cancel(){
        if(status == AttendanceStatus.CANCELLED){
            throw new AttendanceAlreadyCancelledException();
        }
        this.status = AttendanceStatus.CANCELLED;
    }

    boolean isPresent(){
        return status == AttendanceStatus.PRESENT;
    }
}
