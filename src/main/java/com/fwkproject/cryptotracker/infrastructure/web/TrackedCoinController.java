package com.fwkproject.cryptotracker.infrastructure.web;

import com.fwkproject.cryptotracker.application.service.TrackedCoinService;
import com.fwkproject.cryptotracker.domain.model.TrackedCoin;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tracked-coins")
@RequiredArgsConstructor
public class TrackedCoinController {

    private final TrackedCoinService service;

    @GetMapping
    public List<TrackedCoin> getAll() {
        return service.findAll();
    }

    @PostMapping
    public TrackedCoin create(@RequestBody TrackedCoin coin) {
        return service.create(coin);
    }

    @PutMapping("/{id}")
    public TrackedCoin update(@PathVariable Long id, @RequestBody TrackedCoin coin) {
        return service.update(id, coin);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
