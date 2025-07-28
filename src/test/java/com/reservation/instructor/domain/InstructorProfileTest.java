package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InvalidInstructorProfileException;
import com.reservation.instructor.domain.exception.InvalidInstructorSpecialtyException;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructorProfileTest {

    private static final InstructorName NAME = new InstructorName("John Smith");
    private static final InstructorSpecialty SPECIALTY = new InstructorSpecialty("Yoga");
    private static final Set<InstructorSpecialty> SPECIALTIES = Set.of(SPECIALTY);
    private static final InstructorBio BIO = new InstructorBio("Experienced instructor");

    @Test
    void shouldCreateValidInstructorProfile() {
        InstructorProfile profile = new InstructorProfile(NAME, SPECIALTIES, BIO);

        assertThat(profile.name()).isEqualTo(NAME);
        assertThat(profile.specialties()).isEqualTo(SPECIALTIES);
        assertThat(profile.bio()).isEqualTo(BIO);
    }

    @Test
    void shouldThrowExceptionWhenNameIsNull() {
        assertThatThrownBy(() -> new InstructorProfile(null, SPECIALTIES, BIO))
                .isInstanceOf(InvalidInstructorProfileException.class);
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesIsNull() {
        assertThatThrownBy(() -> new InstructorProfile(NAME, null, BIO))
                .isInstanceOf(InvalidInstructorProfileException.class);
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesIsEmpty() {
        assertThatThrownBy(() -> new InstructorProfile(NAME, Set.of(), BIO))
                .isInstanceOf(InvalidInstructorProfileException.class);
    }

    @Test
    void shouldThrowExceptionWhenSpecialtiesContainsNullElements() {
        Set<InstructorSpecialty> invalidSpecialties = new HashSet<>();
        invalidSpecialties.add(SPECIALTY);
        invalidSpecialties.add(null);

        assertThatThrownBy(() -> new InstructorProfile(NAME,
                invalidSpecialties,
                BIO
        )).isInstanceOf(InvalidInstructorProfileException.class);
    }

    @Test
    void shouldThrowExceptionWhenAnyOfTheSpecialtiesIsNull() {
        assertThatThrownBy(() -> new InstructorProfile(NAME,
                Set.of(SPECIALTY, new InstructorSpecialty(null)),
                BIO
        )).isInstanceOf(InvalidInstructorSpecialtyException.class);
    }

    @Test
    void shouldThrowExceptionWhenAnyOfTheSpecialtiesIsBlank() {
        assertThatThrownBy(() -> new InstructorProfile(NAME,
                Set.of(SPECIALTY, new InstructorSpecialty("  ")),
                BIO
        )).isInstanceOf(InvalidInstructorSpecialtyException.class);
    }

    @Test
    void shouldThrowExceptionWhenBioIsNull() {
        assertThatThrownBy(() -> new InstructorProfile(NAME, SPECIALTIES, null))
                .isInstanceOf(InvalidInstructorProfileException.class);
    }
}
