package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidEquipmentNameException;
import com.reservation.hall.domain.exception.InvalidHallCapacityException;
import com.reservation.hall.domain.exception.InvalidHallNameException;
import com.reservation.hall.domain.exception.InvalidHallStatusChangeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class HallTest {

    private final HallId validId = HallId.next();
    private final Set<Equipment> validEquipment = Set.of(new Equipment("Pole"), new Equipment("Sound System"));
    private HallName name;
    private Capacity capacity;
    private Hall hall;

    @BeforeEach
    void setUp() {
        name = new HallName("Hall");
        capacity = new Capacity(10);
        hall = Hall.create(validId, name, capacity, validEquipment);
    }

    @Test

    void shouldCreateHall() {
        assertEquals(validId, hall.getHallId());
        assertTrue(hall.hasName(name));
        assertTrue(hall.hasCapacity(capacity));
        assertEquipmentContains(validEquipment);
        assertTrue(hall.isActive());
    }

    @Test
    void shouldThrowWhenCreatingHallWithEmptyName() {
        assertThrows(InvalidHallNameException.class,
                () -> Hall.create(validId, new HallName(" "), capacity, validEquipment));
    }

    @Test
    void shouldThrowWhenCreatingHallWithInvalidCapacity() {
        assertThrows(InvalidHallCapacityException.class,
                () -> Hall.create(validId, name, new Capacity(0), validEquipment));
    }

    @Test
    void shouldRenameHallWithValidName() {
        HallName newName = new HallName("New Hall");
        hall.rename(newName);

        assertTrue(hall.hasName(newName));
    }

    @Test
    void shouldThrowWhenRenamingHallWithEmptyName() {
        assertThrows(InvalidHallNameException.class, () -> hall.rename(new HallName(" ")));
    }

    @Test
    void shouldResizeHallWithValidCapacity() {
        Capacity newCapacity = new Capacity(20);
        hall.resize(newCapacity);

        assertTrue(hall.hasCapacity(newCapacity));
    }

    @Test
    void shouldThrowWhenResizingHallWithInvalidCapacity() {
        assertThrows(InvalidHallCapacityException.class, () -> hall.resize(new Capacity(0)));
    }

    @Test
    void shouldUpdateEquipment() {
        Set<Equipment> newEquipment = Set.of(new Equipment("Resistance Bands"));
        hall.updateEquipment(newEquipment);
        assertEquipmentContains(newEquipment);
    }

    @Test
    void shouldThrowWhenUpdatingEquipmentWithNull() {
        assertThrows(NullPointerException.class, () -> hall.updateEquipment(null));
    }

    @Test
    void shouldThrowWhenUpdatingEquipmentWithEmptySet() {
        assertThrows(InvalidEquipmentNameException.class, () -> hall.updateEquipment(Set.of(new Equipment(" "))));
    }

    @Test
    void shouldDeactivateHall() {
        hall.deactivate();

        assertTrue(hall.isInactive());
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
    }

    @Test
    void shouldThrowWhenActivatingAlreadyActiveHall() {
        assertThrows(InvalidHallStatusChangeException.class, hall::activate);
    }

    @Test
    void shouldMarkHallAsUnderMaintenance() {
        hall.markUnderMaintenance();

        assertTrue(hall.isUnderMaintenance());
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

    private void assertEquipmentContains(Set<Equipment> expectedEquipment) {
        for (Equipment equipment : expectedEquipment) {
            assertTrue(hall.supportsEquipment(equipment));
        }
    }
}
