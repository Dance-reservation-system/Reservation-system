package com.reservation.instructor.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.instructor.domain.exception.InstructorAlreadyActivatedException;
import com.reservation.instructor.domain.exception.InstructorAlreadyDeactivatedException;
import com.reservation.instructor.domain.exception.InstructorProfileCannotBeUpdatedWhenInactiveException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

class Instructor implements AggregateRoot<InstructorEvent> {

    private final InstructorId instructorId;
    private final SystemUserId systemUserId;
    private InstructorProfile profile;
    private InstructorStatus status;

    private final List<InstructorEvent> events = new ArrayList<>();

    private Instructor(InstructorId instructorId,
                       SystemUserId systemUserId,
                       InstructorProfile profile) {
        this.instructorId = Objects.requireNonNull(instructorId);
        this.systemUserId = Objects.requireNonNull(systemUserId);
        this.profile = Objects.requireNonNull(profile);
        this.status = InstructorStatus.ACTIVE;
    }

    static Instructor create(InstructorId instructorId,
                             SystemUserId systemUserId,
                             InstructorProfile profile) {
        Instructor instructor = new Instructor(instructorId, systemUserId, profile);
        instructor.registerEvent(new InstructorCreatedEvent(instructorId, systemUserId));
        return instructor;
    }

    InstructorId getInstructorId() {
        return this.instructorId;
    }

    SystemUserId getSystemUserId() {
        return this.systemUserId;
    }

    boolean isSameProfile(InstructorProfile profile) {
        return this.profile.equals(profile);
    }

    void updateProfile(InstructorProfile profile) {
        if (!isSameProfile(profile)) {
            if (!isActive()) {
                throw new InstructorProfileCannotBeUpdatedWhenInactiveException();
            }
            this.profile = Objects.requireNonNull(profile);
            registerEvent(new InstructorUpdatedEvent(instructorId, systemUserId));
        }
    }

    boolean isActive() {
        return status.isActive();
    }

    void activate() {
        if (isActive()) {
            throw new InstructorAlreadyActivatedException();
        }
        this.status = InstructorStatus.ACTIVE;
        registerEvent(new InstructorActivatedEvent(instructorId, systemUserId));
    }

    void deactivate() {
        if (!isActive()) {
            throw new InstructorAlreadyDeactivatedException();
        }
        this.status = InstructorStatus.INACTIVE;
        registerEvent(new InstructorDeactivatedEvent(instructorId, systemUserId));
    }

    @Override
    public List<InstructorEvent> pullEvents() {
        List<InstructorEvent> copyEvents = List.copyOf(events);
        events.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(InstructorEvent instructorEvent) {
        events.add(Objects.requireNonNull(instructorEvent));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Instructor other) {
            return Objects.equals(instructorId, other.instructorId);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(instructorId);
    }
}
