package com.reservation.studio.domain;

public sealed interface StudioEvent permits BusinessHoursChangedEvent, ReservationCancellationPolicyUpdatedEvent, ContactDetailsUpdatedEvent, StudioActivatedEvent, StudioClosedEvent, StudioCreatedEvent, StudioRenamedEvent {
}
