package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallStatusChangeException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
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
        this.capacity = capacity;
        this.equipment = Set.copyOf(Objects.requireNonNull(equipment));
        this.status = HallStatus.ACTIVE;
    }

    public static Hall create(HallId id, HallName name, Capacity capacity, Set<Equipment> equipment) {
        return new Hall(id, name, capacity, equipment);
    }

    public void rename(HallName name) {
        this.name = Objects.requireNonNull(name);
    }

    public void resize(Capacity capacity) {
        this.capacity = capacity;
    }

    public void updateEquipments(Set<Equipment> equipment) {
        this.equipment = Set.copyOf(Objects.requireNonNull(equipment));
    }

    public void activate() {
        if(this.status == HallStatus.ACTIVE) {
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

    public boolean isActive() {
        return this.status == HallStatus.ACTIVE;
    }

    public boolean canHostSessionWithCapacity(Capacity requiredCapacity) {
        return this.isActive() && this.capacity.isGreaterThanOrEqual(requiredCapacity);
    }
}
