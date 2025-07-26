package com.reservation.studio.domain;

public sealed interface StudioEvent permits BusinessHoursChanged, CancellationPolicyUpdated, ContactDetailsUpdated, StudioActivated, StudioClosed, StudioCreated, StudioRenamed {
}
