package com.reservation.common;

import java.util.List;

public interface AggregateRoot<E> {
    List<E> pullEvents();
    void registerEvent(E event);
}
