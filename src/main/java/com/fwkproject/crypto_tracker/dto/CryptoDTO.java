package com.fwkproject.crypto_tracker.dto;

import lombok.Data;

@Data
public class CryptoDTO {
    private String id;
    private String symbol;
    private String name;
    private Double priceUsd;
    private Double marketCapUsd;
    private Double volume24h;
}
