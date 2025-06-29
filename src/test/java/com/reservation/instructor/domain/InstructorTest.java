package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActiveException;
import com.reservation.instructor.domain.exception.InstructorAlreadyInactiveException;
import com.reservation.instructor.domain.exception.ProfileCannotBeUpdatedWhenInactiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InstructorTest {

    private InstructorId instructorId;
    private SystemUserId systemUserId;
    private InstructorProfile instructorProfile;
    private Instructor instructor;
    private List<InstructorEvent> events;

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
        events = instructor.pullEvents();

        assertEquals(instructorId, instructor.getInstructorId());
        assertEquals(systemUserId, instructor.getSystemUserId());
        assertTrue(instructor.isSameProfile(instructorProfile));
        assertTrue(instructor.isActive());

        assertEquals(1, events.size());
        assertInstanceOf(InstructorCreated.class, events.getFirst());
    }

    @Test
    void shouldUpdateProfileWhenActive() {
        Name newName = new Name("Jane Smith");
        Specialty newSpecialty = new Specialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new Bio("New bio"));

        instructor.updateProfile(newProfile);
        events = instructor.pullEvents();

        assertTrue(instructor.isSameProfile(newProfile));
        assertEquals(2, events.size());
        assertInstanceOf(InstructorUpdated.class, events.get(1));
    }

    @Test
    void shouldNotAddUpdateEventIfProfileIsSame() {
        instructor.updateProfile(instructorProfile);
        events = instructor.pullEvents();

        assertEquals(1, events.size());
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProfileWhileInactive() {
        Name newName = new Name("Jane Smith");
        Specialty newSpecialty = new Specialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new Bio("New bio"));

        instructor.deactivate();

        assertThrows(ProfileCannotBeUpdatedWhenInactiveException.class, () ->
                instructor.updateProfile(newProfile)
        );
    }

    @Test
    void shouldActivateInstructorWhenInactive() {
        instructor.deactivate();
        instructor.activate();
        events = instructor.pullEvents();

        assertTrue(instructor.isActive());
        assertEquals(3, events.size());
        assertInstanceOf(InstructorActivated.class, events.get(2));
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveInstructor() {
        assertThrows(InstructorAlreadyActiveException.class, instructor::activate);
    }

    @Test
    void shouldDeactivateActiveInstructor() {
        instructor.deactivate();
        events = instructor.pullEvents();

        assertFalse(instructor.isActive());
        assertEquals(2, events.size());
        assertInstanceOf(InstructorDeactivated.class, events.get(1));
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        instructor.deactivate();

        assertThrows(InstructorAlreadyInactiveException.class, instructor::deactivate);
    }
}
