package com.reservation.instructor.domain;

import java.util.Set;

class InstructorTestBuilder {

    private InstructorId instructorId = InstructorId.next();
    private SystemUserId systemUserId = SystemUserId.next();
    private InstructorName name = new InstructorName("John Doe");
    private InstructorSpecialty specialty = new InstructorSpecialty("Yoga");
    private InstructorBio bio = new InstructorBio("Experienced instructor");

    InstructorTestBuilder withInstructorId(InstructorId id) {
        this.instructorId = id;
        return this;
    }

    InstructorTestBuilder withSystemUserId(SystemUserId id) {
        this.systemUserId = id;
        return this;
    }

    InstructorTestBuilder withName(String name) {
        this.name = new InstructorName(name);
        return this;
    }

    InstructorTestBuilder withSpecialty(String specialty) {
        this.specialty = new InstructorSpecialty(specialty);
        return this;
    }

    InstructorTestBuilder withBio(String bio) {
        this.bio = new InstructorBio(bio);
        return this;
    }

    InstructorProfile buildProfile() {
        return new InstructorProfile(name, Set.of(specialty), bio);
    }

    Instructor build() {
        return Instructor.create(instructorId, systemUserId, buildProfile());
    }
}
