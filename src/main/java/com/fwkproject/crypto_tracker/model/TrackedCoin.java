package com.fwkproject.crypto_tracker.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TrackedCoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    private Double alertPrice;

    @Column(nullable = false)
    private boolean alertTriggered = false;
}
