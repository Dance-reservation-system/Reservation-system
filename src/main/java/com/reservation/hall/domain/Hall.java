package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallStatusChangeException;
import lombok.EqualsAndHashCode;

import java.util.Objects;
import java.util.Set;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Hall {
    @EqualsAndHashCode.Include
    private final HallId id;
    private HallName name;
    private Capacity capacity;
    private Set<Equipment> equipment ;
    private HallStatus status;

    private Hall(HallId id, HallName name, Capacity capacity, Set<Equipment> equipment) {
        this.id = Objects.requireNonNull(id);
        this.name = Objects.requireNonNull(name);
        this.capacity = Objects.requireNonNull(capacity);
        this.equipment = Set.copyOf(Objects.requireNonNull(equipment));
        this.status = HallStatus.ACTIVE;
    }

    public static Hall create(HallId id, HallName name, Capacity capacity, Set<Equipment> equipment) {
        return new Hall(id, name, capacity, equipment);
    }

    public HallId getHallId() {
        return this.id;
    }

    public void rename(HallName name) {
        this.name = Objects.requireNonNull(name);
    }

    public void resize(Capacity capacity) {
        this.capacity = Objects.requireNonNull(capacity);
    }

    public void updateEquipment(Set<Equipment> equipment) {
        this.equipment = Set.copyOf(Objects.requireNonNull(equipment));
    }

    public void activate() {
        if(isActive()) {
            throw new InvalidHallStatusChangeException("Hall is already active");
        }
        this.status = HallStatus.ACTIVE;
    }

    public void deactivate() {
        if(this.status == HallStatus.INACTIVE) {
            throw new InvalidHallStatusChangeException("Hall is already inactive");
        }
        this.status = HallStatus.INACTIVE;
    }

    public void markUnderMaintenance() {
        if(this.status == HallStatus.UNDER_MAINTENANCE) {
            throw new InvalidHallStatusChangeException("Hall is already under maintenance");
        }
        this.status = HallStatus.UNDER_MAINTENANCE;
    }

    public boolean hasName(HallName name) {
        return Objects.equals(this.name, name);
    }

    public boolean hasCapacity(Capacity capacity) {
        return Objects.equals(this.capacity, capacity);
    }

    public boolean supportsEquipment(Equipment equipment) {
        return equipment != null && this.equipment.contains(equipment);
    }

    public boolean isActive() {
        return this.status == HallStatus.ACTIVE;
    }

    public boolean isInactive() {
        return this.status == HallStatus.INACTIVE;
    }

    public boolean isUnderMaintenance() {
        return this.status == HallStatus.UNDER_MAINTENANCE;
    }

    public boolean canHostSessionWithCapacity(Capacity requiredCapacity) {
        return this.isActive() && this.capacity.isGreaterThanOrEqual(requiredCapacity);
    }
}
