package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorProfileException;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InstructorProfileTest {

    private final Name name = new Name("John Smith");
    private final Set<Specialty> specialties = Set.of(new Specialty("Yoga"));
    private final Bio bio = new Bio("Experienced instructor");

    @Test
    void shouldCreateValidInstructorProfile() {
        InstructorProfile profile = new InstructorProfile(name, specialties, bio);
        assertEquals(name, profile.name());
        assertEquals(specialties, profile.specialties());
        assertEquals(bio, profile.bio());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNullInProfile() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(null, specialties, bio)
        );
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesIsNull() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(name, null, bio)
        );
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesIsEmpty() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(name, Set.of(), bio)
        );
    }

    @Test
    void shouldThrowExceptionWhenBioIsNull() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(name, specialties, null)
        );
    }
}
