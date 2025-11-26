package com.fwkproject.crypto_tracker.service;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.model.CryptoPriceId;
import com.fwkproject.crypto_tracker.repository.CryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
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

    /**
     * 🔁 Fetch BTC price every 10 seconds and save to TimescaleDB
     * Uncomment @Scheduled below once scheduling is enabled in the application.
     */
    // @Scheduled(fixedRate = 10_000)
    public void fetchCryptoPrice() {
        double price = fetchBTCPrice();

        CryptoPrice entry = new CryptoPrice(
                new CryptoPriceId(Instant.now(), "BTCUSDT"),
                price
        );

        repository.save(entry);
        System.out.println("✅ BTC price saved: " + price);
    }

    /** ✅ Get ALL stored prices (debug/dev use only) */
    public List<CryptoPrice> getAll() {
        return repository.findAll();
    }

    /** ⭐ Get most recent price for a given symbol */
    public CryptoPrice getLatest(String symbol) {
        return repository.findFirstById_SymbolOrderById_TimeDesc(symbol);
    }

    /** ⭐ Default = BTCUSDT */
    public CryptoPrice getLatest() {
        return getLatest("BTCUSDT");
    }

    /** 🔢 Last N collected prices */
    public List<CryptoPrice> getRecent(String symbol, int limit) {
        return repository
                .findById_SymbolOrderById_TimeDesc(symbol, PageRequest.of(0, limit))
                .getContent();
    }

    public List<CryptoPrice> getRecent(int limit) {
        return getRecent("BTCUSDT", limit);
    }

    /** 📈 Get price history between timestamps (ideal for charts) */
    public List<CryptoPrice> getBetween(String symbol, Instant from, Instant to) {
        return repository.findById_SymbolAndId_TimeBetweenOrderById_TimeAsc(symbol, from, to);
    }

    public List<CryptoPrice> getBetween(Instant from, Instant to) {
        return getBetween("BTCUSDT", from, to);
    }

    /** ✅ Manual insert (optional) */
    public CryptoPrice save(CryptoPrice price) {
        return repository.save(price);
    }

    /** 🌍 Call Binance API */
    private double fetchBTCPrice() {
        String url = "https://api.binance.com/api/v3/ticker/price?symbol=BTCUSDT";
        BinanceResponse response = restTemplate.getForObject(url, BinanceResponse.class);
        return Double.parseDouble(response.price());
    }

    /** ✅ Internal record for Binance JSON */
    private record BinanceResponse(String symbol, String price) {}
}
