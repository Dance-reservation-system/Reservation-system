package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActivatedException;
import com.reservation.instructor.domain.exception.InstructorAlreadyDeactivatedException;
import com.reservation.instructor.domain.exception.InstructorProfileCannotBeUpdatedWhenInactiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

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

        InstructorName name = new InstructorName("John Doe");
        InstructorSpecialty specialty = new InstructorSpecialty("Yoga");
        InstructorBio bio = new InstructorBio("Experienced instructor");

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

        InstructorCreatedEvent event = findFirstEventWithClass(InstructorCreatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorCreatedEvent"));

        assertAll(
                () -> assertEquals(instructorId, event.instructorId()),
                () -> assertEquals(systemUserId, event.systemUserId()),
                () -> assertNotNull(event.createdAt())
        );
    }

    @Test
    void shouldUpdateProfileWhenActive() {
        InstructorName newName = new InstructorName("Jane Smith");
        InstructorSpecialty newSpecialty = new InstructorSpecialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new InstructorBio("New bio"));

        instructor.updateProfile(newProfile);
        events = instructor.pullEvents();

        InstructorUpdatedEvent event = findFirstEventWithClass(InstructorUpdatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorUpdatedEvent"));

        assertAll(
                () -> assertEquals(instructorId, event.instructorId()),
                () -> assertEquals(systemUserId, event.systemUserId()),
                () -> assertNotNull(event.updatedAt())
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
        InstructorName newName = new InstructorName("Jane Smith");
        InstructorSpecialty newSpecialty = new InstructorSpecialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new InstructorBio("New bio"));

        instructor.deactivate();

        assertThrows(InstructorProfileCannotBeUpdatedWhenInactiveException.class, () ->
                instructor.updateProfile(newProfile)
        );
    }

    @Test
    void shouldActivateInstructorWhenInactive() {
        instructor.deactivate();
        instructor.activate();
        events = instructor.pullEvents();

        assertTrue(instructor.isActive());

        InstructorActivatedEvent event = findFirstEventWithClass(InstructorActivatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorActivatedEvent"));

        assertAll(
                () -> assertEquals(instructorId, event.instructorId()),
                () -> assertEquals(systemUserId, event.systemUserId()),
                () -> assertNotNull(event.activatedAt())

        );
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveInstructor() {
        assertThrows(InstructorAlreadyActivatedException.class, instructor::activate);
    }

    @Test
    void shouldDeactivateActiveInstructor() {
        instructor.deactivate();
        events = instructor.pullEvents();

        InstructorDeactivatedEvent event = findFirstEventWithClass(InstructorDeactivatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorDeactivatedEvent"));

        assertAll(
                () -> assertEquals(instructorId, event.instructorId()),
                () -> assertEquals(systemUserId, event.systemUserId()),
                () -> assertNotNull(event.deactivatedAt())
        );
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        instructor.deactivate();

        assertThrows(InstructorAlreadyDeactivatedException.class, instructor::deactivate);
    }

    private <T> Optional<T> findFirstEventWithClass(Class<T> eventClass) {
        return events.stream()
                .filter(eventClass::isInstance)
                .map(eventClass::cast)
                .findFirst();
    }
}
