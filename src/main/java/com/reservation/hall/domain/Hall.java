package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallCapacityException;
import com.reservation.hall.domain.exception.InvalidHallNameException;
import lombok.Getter;

import java.util.Objects;
import java.util.Set;

@Getter
public class Hall {
    private final HallId id;
    private String name;
    private int capacity;
    private Set<Equipment> equipment ;
    private HallStatus status;

    Hall(HallId id, String name, int capacity, Set<Equipment> equipment) {
        this.id = Objects.requireNonNull(id);
        Objects.requireNonNull(name);
        this.equipment = Set.copyOf(Objects.requireNonNull(equipment));
        this.status = HallStatus.ACTIVE;

        if (name.isBlank()) {
            throw new InvalidHallNameException(name);
        }
        if (capacity <= 0) {
            throw new InvalidHallCapacityException(capacity);
        }
        this.name = name;
        this.capacity = capacity;
    }

    public void rename(String name) {
        Objects.requireNonNull(name);
        if (name.isBlank()) {
            throw new InvalidHallNameException(name);
            }
        this.name = name;
    }

    public void resize(int capacity) {
        if (capacity <= 0) {
            throw new InvalidHallCapacityException(capacity);
        }
        this.capacity = capacity;
    }

    public void updateEquipments(Set<Equipment> equipment) {
        this.equipment = Set.copyOf(Objects.requireNonNull(equipment));
    }

    public void activate() {
        this.status = HallStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = HallStatus.INACTIVE;
    }

    public void markUnderMaintenance() {
        this.status = HallStatus.UNDER_MAINTENANCE;
    }

    public boolean isActive() {
        return this.status == HallStatus.ACTIVE;
    }

    public boolean canHostSessionWithCapacity(int requiredCapacity) {
        return this.isActive() && this.capacity >= requiredCapacity;
    }
}
