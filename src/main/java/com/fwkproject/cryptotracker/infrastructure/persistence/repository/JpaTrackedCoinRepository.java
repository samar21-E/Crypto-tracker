package com.fwkproject.cryptotracker.infrastructure.persistence.repository;

import com.fwkproject.cryptotracker.infrastructure.persistence.entity.TrackedCoinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface JpaTrackedCoinRepository extends JpaRepository<TrackedCoinEntity, Long> {
    Optional<TrackedCoinEntity> findBySymbol(String symbol);
}
