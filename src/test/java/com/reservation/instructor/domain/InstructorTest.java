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
import static org.junit.jupiter.api.Assertions.assertNotNull;
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
        InstructorEvent event = events.getFirst();
        assertInstanceOf(InstructorCreated.class, event);
        InstructorCreated createdEvent = (InstructorCreated) event;
        assertEquals(instructorId, createdEvent.instructorId());
        assertEquals(systemUserId, createdEvent.systemUserId());
        assertNotNull(createdEvent.createdAt());
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
        InstructorEvent event = events.get(1);
        assertInstanceOf(InstructorUpdated.class, event);
        InstructorUpdated updatedEvent = (InstructorUpdated) event;
        assertEquals(instructorId, updatedEvent.instructorId());
        assertEquals(systemUserId, updatedEvent.systemUserId());
        assertNotNull(updatedEvent.updatedAt());
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
        InstructorEvent event = events.get(2);
        assertInstanceOf(InstructorActivated.class, event);
        InstructorActivated activeEvent = (InstructorActivated) event;
        assertEquals(instructorId, activeEvent.instructorId());
        assertEquals(systemUserId, activeEvent.systemUserId());
        assertNotNull(activeEvent.activationAt());
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
        InstructorEvent event = events.get(1);
        assertInstanceOf(InstructorDeactivated.class, event);
        InstructorDeactivated deactivatedEvent = (InstructorDeactivated) event;
        assertEquals(instructorId, deactivatedEvent.instructorId());
        assertEquals(systemUserId, deactivatedEvent.systemUserId());
        assertNotNull(deactivatedEvent.deactivationAt());
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        instructor.deactivate();

        assertThrows(InstructorAlreadyInactiveException.class, instructor::deactivate);
    }
}
