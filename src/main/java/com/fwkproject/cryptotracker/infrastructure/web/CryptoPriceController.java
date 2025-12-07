package com.fwkproject.cryptotracker.infrastructure.web;

import com.fwkproject.cryptotracker.application.service.CryptoPriceService;
import com.fwkproject.cryptotracker.domain.model.CryptoPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api/crypto-prices")
@RequiredArgsConstructor
public class CryptoPriceController {

    private final CryptoPriceService service;

    @GetMapping("/latest")
    public CryptoPrice getLatest(@RequestParam(defaultValue = "BTCUSDT") String symbol) {
        return service.getLatest(symbol);
    }

    @GetMapping("/history")
    public List<CryptoPrice> getHistory(
            @RequestParam String symbol,
            @RequestParam Instant from,
            @RequestParam Instant to
    ) {
        return service.getHistory(symbol, from, to);
    }
}
