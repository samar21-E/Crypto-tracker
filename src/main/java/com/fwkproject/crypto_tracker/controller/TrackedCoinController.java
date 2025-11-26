package com.fwkproject.crypto_tracker.controller;

import com.fwkproject.crypto_tracker.model.TrackedCoin;
import com.fwkproject.crypto_tracker.repository.TrackedCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/tracked-coins")
@CrossOrigin
public class TrackedCoinController {

    private final TrackedCoinRepository trackedCoinRepository;

    // =======================
    // GET ALL
    // =======================
    @GetMapping
    public ResponseEntity<List<TrackedCoin>> getAll() {
        return ResponseEntity.ok(trackedCoinRepository.findAll());
    }

    // =======================
    // ADD NEW
    // =======================
    @PostMapping
    public ResponseEntity<TrackedCoin> add(@RequestBody TrackedCoin coin) {
        coin.setAlertTriggered(false);
        return ResponseEntity.ok(trackedCoinRepository.save(coin));
    }

    // =======================
    // UPDATE
    // =======================
    @PutMapping("/{id}")
    public ResponseEntity<TrackedCoin> update(
            @PathVariable Long id,
            @RequestBody TrackedCoin updated) {

        return trackedCoinRepository.findById(id)
                .map(existing -> {

                    existing.setSymbol(updated.getSymbol());
                    existing.setAlertPrice(updated.getAlertPrice());
                    existing.setAlertTriggered(false); // reset when user changes the price

                    return ResponseEntity.ok(trackedCoinRepository.save(existing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =======================
    // RESET ALERT
    // =======================
    @PatchMapping("/{id}/reset")
    public ResponseEntity<TrackedCoin> resetAlert(@PathVariable Long id) {

        return trackedCoinRepository.findById(id)
                .map(coin -> {
                    coin.setAlertTriggered(false);
                    return ResponseEntity.ok(trackedCoinRepository.save(coin));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // =======================
    // DELETE
    // =======================
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!trackedCoinRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }

        trackedCoinRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
