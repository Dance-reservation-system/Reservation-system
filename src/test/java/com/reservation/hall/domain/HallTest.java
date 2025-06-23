package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidEquipmentNameException;
import com.reservation.hall.domain.exception.InvalidHallCapacityException;
import com.reservation.hall.domain.exception.InvalidHallNameException;
import com.reservation.hall.domain.exception.InvalidHallStatusChangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HallTest {

    private final HallId validId = new HallId(UUID.randomUUID());
    private final Set<Equipment> validEquipment = Set.of(new Equipment("Pole"), new Equipment("Sound System"));
    private HallName name;
    private Capacity capacity;
    private Hall hall;

    @BeforeEach
    void setUp() {
        name = new HallName("Hall");
        capacity = new Capacity(10);
        hall = new Hall(validId, name, capacity, validEquipment);
    }

    @Test
    void shouldCreateHall() {
        assertEquals(validId, hall.getId());
        assertEquals(name, hall.getName());
        assertEquals(capacity, hall.getCapacity());
        assertEquals(validEquipment, hall.getEquipment());
        assertTrue(hall.isActive());
    }

    @Test
    void shouldThrowWhenCreatingHallWithEmptyName() {
        assertThrows(InvalidHallNameException.class,
                () -> new Hall(validId, new HallName(" "), capacity, validEquipment));
    }

    @Test
    void shouldThrowWhenCreatingHallWithInvalidCapacity() {
        assertThrows(InvalidHallCapacityException.class,
                () -> new Hall(validId, name, new Capacity(0), validEquipment));
    }

    @Test
    void shouldRenameHallWithValidName() {
        hall.rename(new HallName("New Name"));

        assertEquals(new HallName("New Name"), hall.getName());
    }

    @Test
    void shouldThrowWhenRenamingHallWithEmptyName() {
        assertThrows(InvalidHallNameException.class, () -> hall.rename(new HallName(" ")));
    }

    @Test
    void shouldResizeHallWithValidCapacity() {
        hall.resize(new Capacity(20));

        assertEquals(new Capacity(20), hall.getCapacity());
    }

    @Test
    void shouldThrowWhenResizingHallWithInvalidCapacity() {
        assertThrows(InvalidHallCapacityException.class, () -> hall.resize(new Capacity(0)));
    }

    @Test
    void shouldUpdateEquipment() {
        Set<Equipment> newEquipment = Set.of(new Equipment("Resistance Bands"));
        hall.updateEquipments(newEquipment);

        assertEquals(newEquipment, hall.getEquipment());
    }

    @Test
    void shouldThrowWhenUpdatingEquipmentWithNull() {
        assertThrows(NullPointerException.class, () -> hall.updateEquipments(null));
    }

    @Test
    void shouldThrowWhenUpdatingEquipmentWithEmptySet() {
        assertThrows(InvalidEquipmentNameException.class, () -> hall.updateEquipments(Set.of(new Equipment(" "))));
    }

    @Test
    void shouldDeactivateHall() {
        hall.deactivate();

        assertFalse(hall.isActive());
        assertEquals(HallStatus.INACTIVE, hall.getStatus());
    }

    @Test
    void shouldThrowWhenDeactivatingAlreadyInactiveHall() {
        hall.deactivate();

        assertThrows(InvalidHallStatusChangeException.class, hall::deactivate);
    }

    @Test
    void shouldActivateHall() {
        hall.deactivate();
        hall.activate();

        assertTrue(hall.isActive());
        assertEquals(HallStatus.ACTIVE, hall.getStatus());
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveHall() {
        assertThrows(InvalidHallStatusChangeException.class, hall::activate);
    }

    @Test
    void shouldMarkHallAsUnderMaintenance() {
        hall.markUnderMaintenance();

        assertFalse(hall.isActive());
        assertEquals(HallStatus.UNDER_MAINTENANCE, hall.getStatus());
    }

    @Test
    void shouldThrowWhenMarkingAlreadyUnderMaintenanceHall() {
        hall.markUnderMaintenance();

        assertThrows(InvalidHallStatusChangeException.class, hall::markUnderMaintenance);
    }

    @Test
    void shouldReturnTrueIfCanHostSessionWithEnoughCapacity() {
        assertTrue(hall.canHostSessionWithCapacity(new Capacity(5)));
        assertTrue(hall.canHostSessionWithCapacity(capacity));
    }

    @Test
    void shouldReturnFalseIfCapacityIsInsufficient() {
        Capacity requiredCapacity = new Capacity(15);

        assertFalse(hall.canHostSessionWithCapacity(requiredCapacity));
    }

    @Test
    void shouldReturnFalseIfHallIsInactive() {
        hall.deactivate();
        Capacity requiredCapacity = new Capacity(5);

        assertFalse(hall.canHostSessionWithCapacity(requiredCapacity));
    }
}
