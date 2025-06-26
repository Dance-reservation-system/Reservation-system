package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActiveException;
import com.reservation.instructor.domain.exception.InstructorAlreadyInactiveException;
import com.reservation.instructor.domain.exception.ProfileCannotBeUpdatedWhenInactiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstructorTest {

    private InstructorId instructorId;
    private SystemUserId systemUserId;
    private InstructorProfile instructorProfile;
    private Instructor instructor;

    @BeforeEach
    void setUp() {
        instructorId = InstructorId.next();
        systemUserId = SystemUserId.next();

        Name name = new Name("John Doe");
        Specialty specialty = new Specialty("Yoga");
        Bio bio = new Bio("Experienced instructor");

        instructorProfile = new InstructorProfile(name, Set.of(specialty), bio);

        instructor = Instructor.create(instructorId, systemUserId, instructorProfile);
    }

    @Test
    void shouldCreateInstructorWithActiveStatus() {
        assertEquals(instructorId, instructor.getInstructorId());
        assertEquals(systemUserId, instructor.getSystemUserId());
        assertTrue(instructor.isSameProfile(instructorProfile));
        assertTrue(instructor.isActive());
    }

    @Test
    void shouldUpdateProfileWhenActive() {
        Name newName = new Name("Jane Smith");
        Specialty newSpecialty = new Specialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new Bio("New bio"));

        instructor.updateProfile(newProfile);

        assertTrue(instructor.isSameProfile(newProfile));
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProfileWhileInactive() {
        instructor.deactivate();

        assertThrows(ProfileCannotBeUpdatedWhenInactiveException.class, () ->
                instructor.updateProfile(instructorProfile)
        );
    }

    @Test
    void shouldActivateInstructorWhenInactive() {
        instructor.deactivate();
        instructor.activate();

        assertTrue(instructor.isActive());
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveInstructor() {
        assertThrows(InstructorAlreadyActiveException.class, instructor::activate);
    }

    @Test
    void shouldDeactivateActiveInstructor() {
        instructor.deactivate();

        assertFalse(instructor.isActive());
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        instructor.deactivate();

        assertThrows(InstructorAlreadyInactiveException.class, instructor::deactivate);
    }
}
