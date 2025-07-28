package com.reservation.studio.domain;

record ClosedDay() implements OpeningSchedule {
    public static final ClosedDay CLOSE = new ClosedDay();

    @Override
    public boolean isOpen() {
        return false;
    }
}
