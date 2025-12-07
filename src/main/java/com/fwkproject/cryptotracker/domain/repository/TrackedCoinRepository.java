package com.fwkproject.cryptotracker.domain.repository;

import com.fwkproject.cryptotracker.domain.model.TrackedCoin;
import java.util.List;
import java.util.Optional;

public interface TrackedCoinRepository {
    List<TrackedCoin> findAll();
    Optional<TrackedCoin> findBySymbol(String symbol);
    Optional<TrackedCoin> findById(Long id);
    TrackedCoin save(TrackedCoin coin);
    void deleteById(Long id);
}
