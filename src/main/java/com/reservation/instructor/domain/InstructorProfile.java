package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorProfileException;

import java.util.Objects;
import java.util.Set;

record InstructorProfile(InstructorName name,
                         Set<InstructorSpecialty> specialties,
                         InstructorBio bio) {
    InstructorProfile {
        if (name == null) {
            throw new InvalidInstructorProfileException("Name cannot be null");
        }
        if (specialties == null || specialties.isEmpty()) {
            throw new InvalidInstructorProfileException("Specialties cannot be null or empty");
        }
        if (specialties.stream().anyMatch(Objects::isNull)) {
            throw new InvalidInstructorProfileException("Specialties cannot contain null elements");
        }
        if (bio == null) {
            throw new InvalidInstructorProfileException("Bio cannot be null");
        }
    }
}
