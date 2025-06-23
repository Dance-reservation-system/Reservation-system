package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidHallCapacityException;
import com.reservation.hall.domain.exception.InvalidHallNameException;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class HallTest {

    private final HallId validId = new HallId(UUID.randomUUID());
    private final Set<Equipment> validEquipment = Set.of(new Equipment("Pole"), new Equipment("Sound System"));

    @Test
    void shouldCreateHall() {
        Hall hall = new Hall(validId, "Main Hall", 10, validEquipment);
        assertEquals(validId, hall.getId());
        assertEquals("Main Hall", hall.getName());
        assertEquals(10, hall.getCapacity());
        assertEquals(validEquipment, hall.getEquipment());
        assertTrue(hall.isActive());
    }

    @Test
    void shouldThrowWhenCreatingHallWithEmptyName() {
        assertThrows(InvalidHallNameException.class,
                () -> new Hall(validId, " ", 10, validEquipment));
    }

    @Test
    void shouldThrowWhenCreatingHallWithInvalidCapacity() {
        assertThrows(InvalidHallCapacityException.class,
                () -> new Hall(validId, "Hall A", 0, validEquipment));
    }

    @Test
    void shouldRenameHallWithValidName() {
        Hall hall = new Hall(validId, "Old Name", 10, validEquipment);
        hall.rename("New Name");
        assertEquals("New Name", hall.getName());
    }

    @Test
    void shouldThrowWhenRenamingHallWithEmptyName() {
        Hall hall = new Hall(validId, "Old Name", 10, validEquipment);
        assertThrows(InvalidHallNameException.class, () -> hall.rename(" "));
    }

    @Test
    void shouldResizeHallWithValidCapacity() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        hall.resize(20);
        assertEquals(20, hall.getCapacity());
    }

    @Test
    void shouldThrowWhenResizingHallWithInvalidCapacity() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        assertThrows(InvalidHallCapacityException.class, () -> hall.resize(0));
    }

    @Test
    void shouldUpdateEquipment() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        Set<Equipment> newEquipment = Set.of(new Equipment("Resistance Bands"));
        hall.updateEquipments(newEquipment);
        assertEquals(newEquipment, hall.getEquipment());
    }

    @Test
    void shouldDeactivateHall() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        hall.deactivate();
        assertFalse(hall.isActive());
        assertEquals(HallStatus.INACTIVE, hall.getStatus());
    }

    @Test
    void shouldActivateHall() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        hall.deactivate();
        hall.activate();
        assertTrue(hall.isActive());
        assertEquals(HallStatus.ACTIVE, hall.getStatus());
    }

    @Test
    void shouldMarkHallAsUnderMaintenance() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        hall.markUnderMaintenance();
        assertFalse(hall.isActive());
        assertEquals(HallStatus.UNDER_MAINTENANCE, hall.getStatus());
    }

    @Test
    void shouldReturnTrueIfCanHostSessionWithEnoughCapacity() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        assertTrue(hall.canHostSessionWithCapacity(5));
        assertTrue(hall.canHostSessionWithCapacity(10));
    }

    @Test
    void shouldReturnFalseIfCannotHostSessionDueToInactiveOrInsufficientCapacity() {
        Hall hall = new Hall(validId, "Hall", 10, validEquipment);
        assertFalse(hall.canHostSessionWithCapacity(15));

        hall.deactivate();
        assertFalse(hall.canHostSessionWithCapacity(5));
    }
}
