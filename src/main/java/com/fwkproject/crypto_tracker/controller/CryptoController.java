package com.fwkproject.crypto_tracker.controller;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.service.CryptoPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/crypto")
@RequiredArgsConstructor
public class CryptoController {

    private final CryptoPriceService cryptoPriceService;

    /**
     * ✅ Get latest price for a crypto (default = BTCUSDT)
     */
    @GetMapping("/latest")
    public CryptoPrice getLatest(
            @RequestParam(defaultValue = "BTCUSDT") String symbol
    ) {
        return cryptoPriceService.getLatest(symbol);
    }

    /**
     * ✅ Get last N collected prices
     */
    @GetMapping("/recent")
    public List<CryptoPrice> getRecent(
            @RequestParam(defaultValue = "BTCUSDT") String symbol,
            @RequestParam(defaultValue = "100") int limit
    ) {
        return cryptoPriceService.getRecent(symbol, limit);
    }

    /**
     * ✅ Get entire stored price history (debug/demo)
     */
    @GetMapping("/all")
    public List<CryptoPrice> getAll() {
        return cryptoPriceService.getAll();
    }
}
