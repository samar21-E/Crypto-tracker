package com.fwkproject.crypto_tracker.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
@Table(name = "crypto_price_history")
public class CryptoPriceHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String symbol;

    private double price;

    private Instant time;  // TimescaleDB optimizes this automatically
}
