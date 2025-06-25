package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorProfileException;

import java.util.Set;

record InstructorProfile(Name name, Set<Specialty> specialties, Bio bio) {
    public InstructorProfile {
        if (name == null) {
            throw new InvalidInstructorProfileException("Name cannot be null");
        }
        if (specialties == null || specialties.isEmpty()) {
            throw new InvalidInstructorProfileException("Specialties cannot be null or empty");
        }
        if (bio == null) {
            throw new InvalidInstructorProfileException("Bio cannot be null");
        }

        specialties = Set.copyOf(specialties);
    }
}
