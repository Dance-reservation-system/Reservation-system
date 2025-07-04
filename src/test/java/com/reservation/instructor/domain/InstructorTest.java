package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActiveException;
import com.reservation.instructor.domain.exception.InstructorAlreadyInactiveException;
import com.reservation.instructor.domain.exception.ProfileCannotBeUpdatedWhenInactiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        InstructorCreated instructorCreated = events.stream()
                .filter(InstructorCreated.class::isInstance)
                .map(InstructorCreated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected InstructorCreated event"));

        assertAll(
                () -> assertEquals(instructorId, instructorCreated.instructorId()),
                () -> assertEquals(systemUserId, instructorCreated.systemUserId()),
                () -> assertNotNull(instructorCreated.createdAt())
        );
    }

    @Test
    void shouldUpdateProfileWhenActive() {
        Name newName = new Name("Jane Smith");
        Specialty newSpecialty = new Specialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new Bio("New bio"));

        instructor.updateProfile(newProfile);
        events = instructor.pullEvents();

        InstructorUpdated instructorUpdated = events.stream()
                .filter(InstructorUpdated.class::isInstance)
                .map(InstructorUpdated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected InstructorUpdated event"));

        assertAll(
                () -> assertEquals(instructorId, instructorUpdated.instructorId()),
                () -> assertEquals(systemUserId, instructorUpdated.systemUserId()),
                () -> assertNotNull(instructorUpdated.updatedAt())
        );
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

        InstructorActivated activatedEvent = events.stream()
                .filter(InstructorActivated.class::isInstance)
                .map(InstructorActivated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected InstructorActivated event"));

        assertAll(
                () -> assertEquals(instructorId, activatedEvent.instructorId()),
                () -> assertEquals(systemUserId, activatedEvent.systemUserId()),
                () -> assertNotNull(activatedEvent.activatedAt())

        );
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveInstructor() {
        assertThrows(InstructorAlreadyActiveException.class, instructor::activate);
    }

    @Test
    void shouldDeactivateActiveInstructor() {
        instructor.deactivate();
        events = instructor.pullEvents();


        InstructorDeactivated instructorDeactivated = events.stream()
                .filter(InstructorDeactivated.class::isInstance)
                .map(InstructorDeactivated.class::cast)
                .findFirst()
                .orElseThrow(() -> new AssertionError("Expected InstructorDeactivated event"));

        assertAll(
                () -> assertEquals(instructorId, instructorDeactivated.instructorId()),
                () -> assertEquals(systemUserId, instructorDeactivated.systemUserId()),
                () -> assertNotNull(instructorDeactivated.deactivatedAt())
        );
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        instructor.deactivate();

        assertThrows(InstructorAlreadyInactiveException.class, instructor::deactivate);
    }
}
