package com.fwkproject.cryptotracker.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPrice {
    private Instant time;
    private String symbol;
    private double price;
}
