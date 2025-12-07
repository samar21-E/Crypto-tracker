package com.fwkproject.cryptotracker.domain.repository;

import com.fwkproject.cryptotracker.domain.model.CryptoPrice;

import java.time.Instant;
import java.util.List;

public interface CryptoPriceRepository {
    void save(CryptoPrice price);
    CryptoPrice findLatest(String symbol);
    List<CryptoPrice> findHistory(String symbol, Instant from, Instant to);
}
