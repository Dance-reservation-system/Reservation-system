package com.reservation.owner.domain.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Owner {
    private Long id;
    private String firstName;
    private String lastName;
    private String keycloakUserId;
}
