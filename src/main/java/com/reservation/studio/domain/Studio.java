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
    private CancellationPolicy cancellationPolicy;
    private StudioStatus studioStatus;
    private ContactDetails contactDetails;

    private final List<StudioEvent> studioEvents = new ArrayList<>();

    private Studio(StudioId studioId, OwnerId ownerId, StudioName studioName, BusinessHours businessHours, CancellationPolicy cancellationPolicy, ContactDetails contactDetails) {
        this.studioId = Objects.requireNonNull(studioId);
        this.ownerId = Objects.requireNonNull(ownerId);
        this.studioName = Objects.requireNonNull(studioName);
        this.businessHours = Objects.requireNonNull(businessHours);
        this.cancellationPolicy = Objects.requireNonNull(cancellationPolicy);
        this.contactDetails = Objects.requireNonNull(contactDetails);
        this.studioStatus = StudioStatus.INACTIVE;
    }

    public static Studio create(StudioId studioId, OwnerId ownerId, StudioName studioName, BusinessHours businessHours, CancellationPolicy cancellationPolicy, ContactDetails contactDetails) {
        Studio studio = new Studio(studioId, ownerId, studioName, businessHours, cancellationPolicy, contactDetails);
        studio.registerEvent(new StudioCreated(studioId, ownerId));
        return studio;
    }

    public void rename(StudioName newStudioName) {
        this.studioName = Objects.requireNonNull(newStudioName);
        registerEvent(new StudioRenamed(studioId));
    }

    public void updateBusinessHours(BusinessHours newBusinessHours) {
        this.businessHours = Objects.requireNonNull(newBusinessHours);
        registerEvent(new BusinessHoursChanged(studioId, businessHours));
    }

    public void updateCancellationPolicy(CancellationPolicy newCancellationPolicy) {
        this.cancellationPolicy = Objects.requireNonNull(newCancellationPolicy);
        registerEvent(new CancellationPolicyUpdated(studioId, cancellationPolicy));
    }

    public void activate() {
        if (studioStatus.isActive()) {
            throw new StudioAlreadyActiveException();
        }
        this.studioStatus = StudioStatus.ACTIVE;
        registerEvent(new StudioActivated(studioId));
    }

    public void close() {
        if (!studioStatus.isActive()) {
            throw new StudioAlreadyClosedException();
        }
        this.studioStatus = StudioStatus.CLOSED;
        registerEvent(new StudioClosed(studioId));
    }

    public boolean canAcceptReservationAt(DayOfWeek day, LocalTime time) {
        return studioStatus.isActive() && businessHours.isOpenOn(day, time);
    }

    public boolean hasName(StudioName studioName) {
        return this.studioName.equals(studioName);
    }

    public boolean isActive() {
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
}
