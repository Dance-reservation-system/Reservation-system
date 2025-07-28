package com.reservation.studio.domain;

import com.reservation.common.AggregateRoot;
import com.reservation.studio.domain.exception.StudioAlreadyActiveException;
import com.reservation.studio.domain.exception.StudioAlreadyClosedException;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Studio implements AggregateRoot<StudioEvent> {
    private final StudioId studioId;
    private final OwnerId ownerId;
    private StudioName studioName;
    private BusinessHours businessHours;
    private ReservationCancellationPolicy reservationCancellationPolicy;
    private StudioStatus studioStatus;
    private ContactDetails contactDetails;

    private final List<StudioEvent> studioEvents = new ArrayList<>();

    private Studio(StudioId studioId, OwnerId ownerId, StudioName studioName, BusinessHours businessHours, ReservationCancellationPolicy reservationCancellationPolicy, ContactDetails contactDetails) {
        this.studioId = Objects.requireNonNull(studioId);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.studioName = Objects.requireNonNull(studioName);
        this.businessHours = Objects.requireNonNull(businessHours);
        this.reservationCancellationPolicy = Objects.requireNonNull(reservationCancellationPolicy);
        this.contactDetails = Objects.requireNonNull(contactDetails);
        this.studioStatus = StudioStatus.INACTIVE;
    }

    static Studio create(StudioId studioId, OwnerId ownerId, StudioName studioName, BusinessHours businessHours, ReservationCancellationPolicy reservationCancellationPolicy, ContactDetails contactDetails) {
        Studio studio = new Studio(studioId, ownerId, studioName, businessHours, reservationCancellationPolicy, contactDetails);
        studio.registerEvent(new StudioCreatedEvent(studioId, ownerId));
        return studio;
    }

    StudioId getStudioId() {
        return studioId;
    }

    OwnerId getOwnerId() {
        return ownerId;
    }

    void rename(StudioName newStudioName) {
        this.studioName = Objects.requireNonNull(newStudioName);
        registerEvent(new StudioRenamedEvent(studioId));
    }

    void updateBusinessHours(BusinessHours newBusinessHours) {
        this.businessHours = Objects.requireNonNull(newBusinessHours);
        registerEvent(new BusinessHoursChangedEvent(studioId, businessHours));
    }

    void updateCancellationPolicy(ReservationCancellationPolicy newReservationCancellationPolicy) {
        this.reservationCancellationPolicy = Objects.requireNonNull(newReservationCancellationPolicy);
        registerEvent(new ReservationCancellationPolicyUpdatedEvent(studioId, reservationCancellationPolicy));
    }

    void updateContactDetails(ContactDetails newContactDetails) {
        this.contactDetails = Objects.requireNonNull(newContactDetails);
        registerEvent(new ContactDetailsUpdatedEvent(studioId, contactDetails));
    }

    void activate() {
        if (studioStatus.isActive()) {
            throw new StudioAlreadyActiveException();
        }
        this.studioStatus = StudioStatus.ACTIVE;
        registerEvent(new StudioActivatedEvent(studioId));
    }

    void close() {
        if (!studioStatus.isActive()) {
            throw new StudioAlreadyClosedException();
        }
        this.studioStatus = StudioStatus.CLOSED;
        registerEvent(new StudioClosedEvent(studioId));
    }

    boolean canAcceptReservationAt(DayOfWeek day, LocalTime time) {
        return studioStatus.isActive() && businessHours.isOpenOn(day, time);
    }

    boolean hasName(StudioName studioName) {
        return this.studioName.equals(studioName);
    }

    boolean isActive() {
        return studioStatus.isActive();
    }

    @Override
    public List<StudioEvent> pullEvents() {
        List<StudioEvent> copyEvents = List.copyOf(studioEvents);
        studioEvents.clear();
        return copyEvents;
    }

    @Override
    public void registerEvent(StudioEvent event) {
        studioEvents.add(Objects.requireNonNull(event));
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Studio studio = (Studio) o;
        return Objects.equals(studioId, studio.studioId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studioId);
    }
}
