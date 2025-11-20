package com.fwkproject.crypto_tracker.service;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.model.CryptoPriceId;
import com.fwkproject.crypto_tracker.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CryptoPriceService {

    private final CryptoPriceRepository repository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Scheduled(fixedRate = 10000)
    public void fetchCryptoPrice() {
        double price = fetchBTCPrice();

        CryptoPriceId id = new CryptoPriceId(
                Instant.now(),
                "BTCUSDT"
        );

        CryptoPrice entry = new CryptoPrice(
                id,
                price
        );

        repository.save(entry);

        System.out.println("💾 Prix BTC sauvegardé : " + price);
    }

    public List<CryptoPrice> getAll() {
        return repository.findAll();
    }

    public CryptoPrice getLatest() {
        // use the updated repository method with embedded ID
        return repository.findTopBySymbolOrderByTimeDesc("BTCUSDT");
    }

    public CryptoPrice save(CryptoPrice price) {
        return repository.save(price);
    }

    private double fetchBTCPrice() {
        String url = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";
        var response = restTemplate.getForObject(url, BinanceResponse.class);
        return Double.parseDouble(response.price());
    }

    private record BinanceResponse(String symbol, String price) {}
}
