package com.fwkproject.cryptotracker.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tracked_coins")
public class TrackedCoinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String symbol;

    private Double alertPrice;

    @Column(nullable = false)
    private boolean alertTriggered = false;
}
