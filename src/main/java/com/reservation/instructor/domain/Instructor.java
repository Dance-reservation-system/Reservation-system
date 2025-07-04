package com.reservation.instructor.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.instructor.domain.exception.InstructorAlreadyActiveException;
import com.reservation.instructor.domain.exception.InstructorAlreadyInactiveException;
import com.reservation.instructor.domain.exception.ProfileCannotBeUpdatedWhenInactiveException;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Instructor implements AggregateRoot<InstructorEvent> {
    @EqualsAndHashCode.Include
    private final InstructorId instructorId;
    private final SystemUserId systemUserId;
    private final List<InstructorEvent> instructorEvents = new ArrayList<>();
    private InstructorProfile profile;
    private InstructorStatus status;

    private Instructor(InstructorId instructorId, SystemUserId systemUserId, InstructorProfile profile) {
        this.instructorId = Objects.requireNonNull(instructorId);
        this.systemUserId = Objects.requireNonNull(systemUserId);
        this.profile = Objects.requireNonNull(profile);
        this.status = InstructorStatus.ACTIVE;
    }

    public static Instructor create(InstructorId instructorId, SystemUserId systemUserId, InstructorProfile profile) {
        Instructor instructor = new Instructor(instructorId, systemUserId, profile);
        instructor.registerEvent(new InstructorCreated(instructorId, systemUserId));
        return instructor;
    }

    public InstructorId getInstructorId() {
        return this.instructorId;
    }

    public SystemUserId getSystemUserId() {
        return this.systemUserId;
    }

    public void updateProfile(InstructorProfile profile) {
        if (!isSameProfile(profile)) {
            if (!isActive()) {
                throw new ProfileCannotBeUpdatedWhenInactiveException();
            }
            this.profile = Objects.requireNonNull(profile);
            registerEvent(new InstructorUpdated(instructorId, systemUserId));
        }
    }

    public void activate() {
        if (isActive()) {
            throw new InstructorAlreadyActiveException();
        }
        this.status = InstructorStatus.ACTIVE;
        registerEvent(new InstructorActivated(instructorId, systemUserId));
    }

    public void deactivate() {
        if (!isActive()) {
            throw new InstructorAlreadyInactiveException();
        }
        this.status = InstructorStatus.INACTIVE;
        registerEvent(new InstructorDeactivated(instructorId, systemUserId));
    }

    @Override
    public List<InstructorEvent> pullEvents() {
        List<InstructorEvent> copyEvents = List.copyOf(instructorEvents);
        instructorEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(InstructorEvent instructorEvent) {
        instructorEvents.add(Objects.requireNonNull(instructorEvent));
    }

    public boolean isSameProfile(InstructorProfile profile) {
        return this.profile.equals(profile);
    }

    public boolean isActive() {
        return this.status.isActive();
    }
}
