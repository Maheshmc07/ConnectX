package org.mc.connectx.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenEnity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String token;

    private Instant expiryDate;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;





}
