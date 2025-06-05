package com.reservation.client;

import com.reservation.common.AbstractEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(schema = "public", name = "client")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class Client extends AbstractEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;
}
