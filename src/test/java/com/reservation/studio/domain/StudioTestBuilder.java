package com.reservation.studio.domain;

import java.time.Duration;
import java.time.LocalTime;

class StudioTestBuilder {
    private final StudioId studioId = StudioId.next();
    private final OwnerId ownerId = OwnerId.next();
    private StudioName studioName = new StudioName("StudioName");
    private BusinessHours businessHours = new BusinessHoursTestBuilder().openAllDays(LocalTime.of(8, 0), LocalTime.of(16, 0)).build();
    private ReservationCancellationPolicy reservationCancellationPolicy = new ReservationCancellationPolicy(Duration.ofHours(1));
    private ContactDetails contactDetails = new ContactDetails("Address", "123456789");

    public StudioTestBuilder withStudioName(StudioName studioName) {
        this.studioName = studioName;
        return this;
    }

    public StudioTestBuilder withBusinessHours(LocalTime from, LocalTime to) {
        this.businessHours = new BusinessHoursTestBuilder().openAllDays(from, to).build();
        return this;
    }

    public StudioTestBuilder withCancellationPolicy(ReservationCancellationPolicy reservationCancellationPolicy) {
        this.reservationCancellationPolicy = reservationCancellationPolicy;
        return this;
    }

    public StudioTestBuilder withContactDetails(ContactDetails contactDetails) {
        this.contactDetails = contactDetails;
        return this;
    }

    public Studio build() {
        return Studio.create(studioId, ownerId, studioName, businessHours, reservationCancellationPolicy, contactDetails);
    }
}
