package com.fwkproject.crypto_tracker.controller;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.service.CryptoPriceService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prices")
public class CryptoPriceController {

    private final CryptoPriceService service;

    public CryptoPriceController(CryptoPriceService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<CryptoPrice> all() {
        return service.getAll();
    }

    @GetMapping("/latest")
    public CryptoPrice latest() {
        return service.getLatest();
    }

    @PostMapping
    public CryptoPrice create(@RequestBody CryptoPrice price) {
        return service.save(price);
    }
}
