package com.reservation.instructor.domain;

import com.reservation.instructor.domain.exception.InstructorAlreadyActivatedException;
import com.reservation.instructor.domain.exception.InstructorAlreadyDeactivatedException;
import com.reservation.instructor.domain.exception.InstructorProfileCannotBeUpdatedWhenInactiveException;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InstructorTest {

    @Test
    void shouldCreateInstructorWithActiveStatus() {
        InstructorId instructorId = InstructorId.next();
        SystemUserId systemUserId = SystemUserId.next();
        InstructorProfile instructorProfile = new InstructorProfile(
                new InstructorName("Chuck Norris"),
                Set.of(new InstructorSpecialty("Kick-boxing")),
                new InstructorBio("So experienced that even his shadow has a black belt")
        );

        Instructor instructor = Instructor.create(instructorId, systemUserId, instructorProfile);

        assertThat(instructor.getInstructorId()).isEqualTo(instructorId);
        assertThat(instructor.getSystemUserId()).isEqualTo(systemUserId);
        assertThat(instructor.isSameProfile(instructorProfile)).isTrue();
        assertThat(instructor.isActive()).isTrue();

        List<InstructorEvent> events = instructor.pullEvents();

        InstructorCreatedEvent event = findFirstEventWithClass(events, InstructorCreatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorCreatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructorId);
        assertThat(event.systemUserId()).isEqualTo(systemUserId);
        assertThat(event.createdAt()).isNotNull();
    }

    @Test
    void shouldUpdateProfileWhenActive() {
        Instructor instructor = new InstructorTestBuilder().build();

        InstructorProfile newProfile = new InstructorProfile(
                new InstructorName("Jane Smith"),
                Set.of(new InstructorSpecialty("Pilates")),
                new InstructorBio("New bio")
        );

        instructor.updateProfile(newProfile);
        List<InstructorEvent> events = instructor.pullEvents();

        InstructorUpdatedEvent event = findFirstEventWithClass(events, InstructorUpdatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorUpdatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructor.getInstructorId());
        assertThat(event.systemUserId()).isEqualTo(instructor.getSystemUserId());
        assertThat(event.updatedAt()).isNotNull();
    }

    @Test
    void shouldNotAddUpdateEventIfProfileIsSame() {
        InstructorTestBuilder builder = new InstructorTestBuilder();
        InstructorProfile instructorProfile = builder.buildProfile();
        Instructor instructor = builder.build();

        instructor.updateProfile(instructorProfile);
        List<InstructorEvent> events = instructor.pullEvents();

        assertThat(events).hasSize(1);
        assertThat(findFirstEventWithClass(events, InstructorUpdatedEvent.class)).isEmpty();
    }

    @Test
    void shouldThrowExceptionWhenUpdatingProfileWhileInactive() {
        Instructor instructor = new InstructorTestBuilder().build();

        InstructorProfile newProfile = new InstructorProfile(
                new InstructorName("Jane Smith"),
                Set.of(new InstructorSpecialty("Pilates")),
                new InstructorBio("New bio")
        );

        instructor.deactivate();

        assertThatThrownBy(() -> instructor.updateProfile(newProfile))
                .isInstanceOf(InstructorProfileCannotBeUpdatedWhenInactiveException.class);
    }

    @Test
    void shouldActivateInstructorWhenInactive() {
        Instructor instructor = new InstructorTestBuilder().build();

        instructor.deactivate();
        instructor.activate();
        List<InstructorEvent> events = instructor.pullEvents();

        assertThat(instructor.isActive()).isTrue();

        InstructorActivatedEvent event = findFirstEventWithClass(events, InstructorActivatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorActivatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructor.getInstructorId());
        assertThat(event.systemUserId()).isEqualTo(instructor.getSystemUserId());
        assertThat(event.activatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveInstructor() {
        Instructor instructor = new InstructorTestBuilder().build();

        assertThatThrownBy(instructor::activate)
                .isInstanceOf(InstructorAlreadyActivatedException.class);
    }

    @Test
    void shouldDeactivateActiveInstructor() {
        Instructor instructor = new InstructorTestBuilder().build();

        instructor.deactivate();
        List<InstructorEvent> events = instructor.pullEvents();

        InstructorDeactivatedEvent event = findFirstEventWithClass(events, InstructorDeactivatedEvent.class)
                .orElseThrow(() -> new AssertionError("Expected InstructorDeactivatedEvent"));

        assertThat(event.instructorId()).isEqualTo(instructor.getInstructorId());
        assertThat(event.systemUserId()).isEqualTo(instructor.getSystemUserId());
        assertThat(event.deactivatedAt()).isNotNull();
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveInstructor() {
        Instructor instructor = new InstructorTestBuilder().build();
        instructor.deactivate();

        assertThatThrownBy(instructor::deactivate)
                .isInstanceOf(InstructorAlreadyDeactivatedException.class);
    }

    private <T> Optional<T> findFirstEventWithClass(List<InstructorEvent> events,
                                                    Class<T> eventClass) {
        return events.stream()
                .filter(eventClass::isInstance)
                .map(eventClass::cast)
                .findFirst();
    }
}
