package com.fwkproject.cryptotracker.infrastructure.persistence;

import com.fwkproject.cryptotracker.domain.model.TrackedCoin;
import com.fwkproject.cryptotracker.domain.repository.TrackedCoinRepository;
import com.fwkproject.cryptotracker.infrastructure.persistence.entity.TrackedCoinEntity;
import com.fwkproject.cryptotracker.infrastructure.persistence.repository.JpaTrackedCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class TrackedCoinRepositoryAdapter implements TrackedCoinRepository {

    private final JpaTrackedCoinRepository jpaRepository;

    @Override
    public List<TrackedCoin> findAll() {
        return jpaRepository.findAll().stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<TrackedCoin> findBySymbol(String symbol) {
        return jpaRepository.findBySymbol(symbol).map(this::toDomain);
    }

    @Override
    public Optional<TrackedCoin> findById(Long id) {
        return jpaRepository.findById(id).map(this::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        jpaRepository.deleteById(id);
    }

    @Override
    public TrackedCoin save(TrackedCoin coin) {
        TrackedCoinEntity entity = toEntity(coin);
        TrackedCoinEntity saved = jpaRepository.save(entity);
        return toDomain(saved);
    }

    private TrackedCoin toDomain(TrackedCoinEntity entity) {
        return new TrackedCoin(entity.getId(), entity.getSymbol(), entity.getAlertPrice(), entity.isAlertTriggered());
    }

    private TrackedCoinEntity toEntity(TrackedCoin coin) {
        TrackedCoinEntity entity = new TrackedCoinEntity();
        entity.setId(coin.getId());
        entity.setSymbol(coin.getSymbol());
        entity.setAlertPrice(coin.getAlertPrice());
        entity.setAlertTriggered(coin.isAlertTriggered());
        return entity;
    }
}
