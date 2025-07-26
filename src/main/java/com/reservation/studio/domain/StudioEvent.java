package com.reservation.studio.domain;

public sealed interface StudioEvent permits BusinessHoursChanged, CancellationPolicyUpdated, StudioActivated, StudioClosed, StudioCreated, StudioRenamed {
}
