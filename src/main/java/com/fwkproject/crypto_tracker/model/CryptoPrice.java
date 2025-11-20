package com.fwkproject.crypto_tracker.model;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crypto_prices")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CryptoPrice {

    @EmbeddedId
    private CryptoPriceId id;

    private double price;
}
