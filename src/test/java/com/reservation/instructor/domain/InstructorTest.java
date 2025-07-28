package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActivatedException;
import com.reservation.instructor.domain.exception.InstructorAlreadyDeactivatedException;
import com.reservation.instructor.domain.exception.InstructorProfileCannotBeUpdatedWhenInactiveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

        assertThat(instructor.getInstructorId()).isEqualTo(instructorId);
        assertThat(instructor.getSystemUserId()).isEqualTo(systemUserId);
        assertThat(instructor.isSameProfile(instructorProfile)).isTrue();
        assertThat(instructor.isActive()).isTrue();

        InstructorCreatedEvent event = findFirstEventWithClass(InstructorCreatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorCreatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructorId);
        assertThat(event.systemUserId()).isEqualTo(systemUserId);
        assertThat(event.createdAt()).isNotNull();
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

        assertThat(event.instructorId()).isEqualTo(instructorId);
        assertThat(event.systemUserId()).isEqualTo(systemUserId);
        assertThat(event.updatedAt()).isNotNull();
    }

    @Test
    void shouldNotAddUpdateEventIfProfileIsSame() {
        instructor.updateProfile(instructorProfile);
        events = instructor.pullEvents();

        assertThat(events).hasSize(1);
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProfileWhileInactive() {
        InstructorName newName = new InstructorName("Jane Smith");
        InstructorSpecialty newSpecialty = new InstructorSpecialty("Pilates");
        InstructorProfile newProfile = new InstructorProfile(newName, Set.of(newSpecialty), new InstructorBio("New bio"));

        instructor.deactivate();

        assertThatThrownBy(() -> instructor.updateProfile(newProfile))
                .isInstanceOf(InstructorProfileCannotBeUpdatedWhenInactiveException.class);
    }

    @Test
    void shouldActivateInstructorWhenInactive() {
        instructor.deactivate();
        instructor.activate();
        events = instructor.pullEvents();

        assertThat(instructor.isActive()).isTrue();

        InstructorActivatedEvent event = findFirstEventWithClass(InstructorActivatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorActivatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructorId);
        assertThat(event.systemUserId()).isEqualTo(systemUserId);
        assertThat(event.activatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveInstructor() {
        assertThatThrownBy(instructor::activate)
                .isInstanceOf(InstructorAlreadyActivatedException.class);
    }

    @Test
    void shouldDeactivateActiveInstructor() {
        instructor.deactivate();
        events = instructor.pullEvents();

        InstructorDeactivatedEvent event = findFirstEventWithClass(InstructorDeactivatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorDeactivatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructorId);
        assertThat(event.systemUserId()).isEqualTo(systemUserId);
        assertThat(event.deactivatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        instructor.deactivate();

        assertThatThrownBy(instructor::deactivate)
                .isInstanceOf(InstructorAlreadyDeactivatedException.class);
    }

    private <T> Optional<T> findFirstEventWithClass(Class<T> eventClass) {
        return events.stream()
                .filter(eventClass::isInstance)
                .map(eventClass::cast)
                .findFirst();
    }
}
