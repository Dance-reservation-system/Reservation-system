package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActiveException;
import com.reservation.instructor.domain.exception.InstructorAlreadyInactiveException;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Instructor {
    @EqualsAndHashCode.Include
    private final InstructorId instructorId;
    private final SystemUserId systemUserId;
    private InstructorProfile profile;
    private InstructorStatus status;

    private Instructor(InstructorId instructorId, SystemUserId systemUserId, InstructorProfile profile) {
        this.instructorId = Objects.requireNonNull(instructorId);
        this.systemUserId = Objects.requireNonNull(systemUserId);
        this.profile = Objects.requireNonNull(profile);
        this.status = InstructorStatus.ACTIVE;
    }

    public static Instructor create(InstructorId instructorId, SystemUserId systemUserId, InstructorProfile profile) {
        return new Instructor(instructorId, systemUserId, profile);
    }

    public InstructorId getInstructorId() {
        return this.instructorId;
    }

    public SystemUserId getSystemUserId() {
        return this.systemUserId;
    }

    public void updateProfile(InstructorProfile profile) {
        if(this.status.isInactive()) {
            throw new InstructorAlreadyInactiveException();
        }
        this.profile = Objects.requireNonNull(profile);
    }

    public void activate() {
        if (this.status.isActive()) {
            throw new InstructorAlreadyActiveException();
        }
        this.status = InstructorStatus.ACTIVE;
    }

    public void deactivate() {
        if (this.status.isInactive()) {
            throw new InstructorAlreadyInactiveException();
        }
        this.status = InstructorStatus.INACTIVE;
    }

    public boolean hasProfile(InstructorProfile profile) {
        return this.profile.equals(profile);
    }

    public boolean isActive() {
        return this.status.isActive();
    }
}
