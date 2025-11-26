package com.fwkproject.crypto_tracker.repository;

import com.fwkproject.crypto_tracker.model.TrackedCoin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TrackedCoinRepository extends JpaRepository<TrackedCoin, Long> {
}
