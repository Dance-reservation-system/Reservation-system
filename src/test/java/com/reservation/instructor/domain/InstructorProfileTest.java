package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorProfileException;
import com.reservation.instructor.domain.exception.InvalidInstructorSpecialtyException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class InstructorProfileTest {

    private static final InstructorName NAME = new InstructorName("John Smith");
    private static final InstructorSpecialty SPECIALTY = new InstructorSpecialty("Yoga");
    private static final Set<InstructorSpecialty> SPECIALTIES = Set.of(SPECIALTY);
    private static final InstructorBio BIO = new InstructorBio("Experienced instructor");

    @Test
    void shouldCreateValidInstructorProfile() {
        InstructorProfile profile = new InstructorProfile(NAME, SPECIALTIES, BIO);

        assertEquals(NAME, profile.name());
        assertEquals(SPECIALTIES, profile.specialties());
        assertEquals(BIO, profile.bio());
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(null, SPECIALTIES, BIO)
        );
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesIsNull() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(NAME, null, BIO)
        );
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesIsEmpty() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(NAME, Set.of(), BIO)
        );
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesContainsNullElements() {
        Set<InstructorSpecialty> invalidSpecialties = new HashSet<>();
        invalidSpecialties.add(SPECIALTY);
        invalidSpecialties.add(null);

        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(NAME,
                        invalidSpecialties,
                        BIO
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenAnyOfTheSpecialtiesIsNull() {
        assertThrows(InvalidInstructorSpecialtyException.class, () ->
                new InstructorProfile(NAME,
                        Set.of(SPECIALTY, new InstructorSpecialty(null)),
                        BIO
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenAnyOfTheSpecialtiesIsBlank() {
        assertThrows(InvalidInstructorSpecialtyException.class, () ->
                new InstructorProfile(NAME,
                        Set.of(SPECIALTY, new InstructorSpecialty("  ")),
                        BIO
                )
        );
    }

    @Test
    void shouldThrowExceptionWhenBioIsNull() {
        assertThrows(InvalidInstructorProfileException.class, () ->
                new InstructorProfile(NAME, SPECIALTIES, null)
        );
    }
}
