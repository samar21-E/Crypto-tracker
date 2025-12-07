package com.fwkproject.cryptotracker.application.service;

import com.fwkproject.cryptotracker.domain.model.TrackedCoin;
import com.fwkproject.cryptotracker.domain.repository.TrackedCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackedCoinService {

    private final TrackedCoinRepository repository;

    public List<TrackedCoin> findAll() {
        return repository.findAll();
    }

    public TrackedCoin findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tracked coin not found"));
    }

    public TrackedCoin create(TrackedCoin coin) {
        coin.setAlertTriggered(false);
        return repository.save(coin);
    }

    public TrackedCoin update(Long id, TrackedCoin updated) {
        TrackedCoin existing = findById(id);
        existing.setSymbol(updated.getSymbol());
        existing.setAlertPrice(updated.getAlertPrice());
        existing.setAlertTriggered(false);
        return repository.save(existing);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
