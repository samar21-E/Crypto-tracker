package com.fwkproject.cryptotracker.infrastructure.persistence.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class CryptoPriceId implements Serializable {
    private Instant time;
    private String symbol;
}
