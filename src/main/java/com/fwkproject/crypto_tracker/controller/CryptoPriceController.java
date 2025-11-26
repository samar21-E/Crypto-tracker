package com.fwkproject.crypto_tracker.controller;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.service.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
@RequiredArgsConstructor
public class CryptoPriceController {

    private final CryptoPriceService service;

    // ⭐ Latest price (default: BTCUSDT)
    @GetMapping("/latest")
    public CryptoPrice latest(@RequestParam(defaultValue = "BTCUSDT") String symbol) {
        return service.getLatest(symbol);
    }

    // 🔢 Last N points (default: 100)
    @GetMapping("/recent")
    public List<CryptoPrice> recent(
            @RequestParam(defaultValue = "BTCUSDT") String symbol,
            @RequestParam(defaultValue = "100") int limit
    ) {
        return service.getRecent(symbol, limit);
    }

    // ⏱️ Range query: prices between from and to
    @GetMapping("/range")
    public List<CryptoPrice> range(
            @RequestParam(defaultValue = "BTCUSDT") String symbol,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant from,
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            Instant to
    ) {
        return service.getBetween(symbol, from, to);
    }

    // For debug: get everything
    @GetMapping
    public List<CryptoPrice> all() {
        return service.getAll();
    }
}
