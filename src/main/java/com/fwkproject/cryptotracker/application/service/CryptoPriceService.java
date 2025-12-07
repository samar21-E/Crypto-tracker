package com.fwkproject.cryptotracker.application.service;

import com.fwkproject.cryptotracker.application.port.output.CryptoPriceProvider;
import com.fwkproject.cryptotracker.domain.model.CryptoPrice;
import com.fwkproject.cryptotracker.domain.repository.CryptoPriceRepository;
import com.fwkproject.cryptotracker.domain.repository.TrackedCoinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceService {

    private final CryptoPriceRepository priceRepository;
    private final TrackedCoinRepository coinRepository;
    private final CryptoPriceProvider priceProvider;
    private final com.fwkproject.cryptotracker.application.port.output.NotificationPort notificationPort;

    @Scheduled(fixedRate = 10_000)
    public void fetchPricesAndCheckAlerts() {
        // For simplicity, we track BTCUSDT by default + any tracked coins
        List<String> symbols = new java.util.ArrayList<>(List.of("BTCUSDT"));
        coinRepository.findAll().forEach(coin -> {
            if (!symbols.contains(coin.getSymbol())) {
                symbols.add(coin.getSymbol());
            }
        });

        for (String symbol : symbols) {
            try {
                double priceVal = priceProvider.getPrice(symbol);
                CryptoPrice price = CryptoPrice.builder()
                        .time(Instant.now())
                        .symbol(symbol)
                        .price(priceVal)
                        .build();
                priceRepository.save(price);

                checkAlerts(symbol, priceVal);
            } catch (Exception e) {
                log.error("Failed to fetch price for {}", symbol, e);
            }
        }
    }

    private void checkAlerts(String symbol, double price) {
        coinRepository.findBySymbol(symbol).ifPresent(coin -> {
            if (!coin.isAlertTriggered() && price >= coin.getAlertPrice()) {
                log.info("🚨 ALERT: {} reached {} (target: {})", symbol, price, coin.getAlertPrice());
                coin.setAlertTriggered(true);
                coinRepository.save(coin);
                notificationPort.sendPriceAlert(coin, price);
            }
        });
    }

    public CryptoPrice getLatest(String symbol) {
        return priceRepository.findLatest(symbol);
    }

    public List<CryptoPrice> getHistory(String symbol, Instant from, Instant to) {
        return priceRepository.findHistory(symbol, from, to);
    }
}
