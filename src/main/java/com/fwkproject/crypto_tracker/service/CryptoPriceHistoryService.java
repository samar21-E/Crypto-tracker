package com.fwkproject.crypto_tracker.service;

import com.fwkproject.crypto_tracker.model.CryptoPriceHistory;
import com.fwkproject.crypto_tracker.repository.CryptoPriceHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
@Slf4j
public class CryptoPriceHistoryService {

    private final BinancePriceService binancePriceService;
    private final CryptoPriceHistoryRepository historyRepo;

    @Value("${history.symbol:BTCUSDT}")
    private String defaultSymbol;

    // every 1 minute
    @Scheduled(fixedRateString = "${history.interval.ms:60000}")
    public void capturePrice() {
        try {
            double price = binancePriceService.getPrice(defaultSymbol);

            CryptoPriceHistory h = new CryptoPriceHistory();
            h.setSymbol(defaultSymbol);
            h.setPrice(price);
            h.setTime(Instant.now());

            historyRepo.save(h);

            log.info("📈 Saved {} price history {}", defaultSymbol, price);

        } catch (Exception ex) {
            log.error("Failed to capture price history", ex);
        }
    }
}
