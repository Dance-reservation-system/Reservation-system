package com.reservation.hall.domain;

import com.reservation.hall.domain.exception.InvalidEquipmentNameException;

record Equipment(String name) {
    public Equipment {
        if (name.isBlank()) {
            throw new InvalidEquipmentNameException(name);
        }
    }
}
