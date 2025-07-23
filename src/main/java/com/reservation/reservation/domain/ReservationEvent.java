package com.reservation.reservation.domain;

public sealed interface ReservationEvent permits ReservationCancelled, ReservationCreated {
}
