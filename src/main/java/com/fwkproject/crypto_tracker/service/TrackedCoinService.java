package com.fwkproject.crypto_tracker.service;

import com.fwkproject.crypto_tracker.model.TrackedCoin;
import com.fwkproject.crypto_tracker.repository.TrackedCoinRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TrackedCoinService {

    private final TrackedCoinRepository repository;
    private final CryptoPriceService cryptoPriceService;

    /** ✅ Return all tracked alerts */
    public List<TrackedCoin> findAll() {
        return repository.findAll();
    }

    /** ✅ Find single alert by ID */
    public TrackedCoin findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tracked coin not found"));
    }

    /** ✅ Create new tracked alert */
    public TrackedCoin create(TrackedCoin coin) {
        coin.setAlertTriggered(false); // reset on creation
        return repository.save(coin);
    }

    /** ✅ Update tracked alert */
    public TrackedCoin update(Long id, TrackedCoin updated) {
        TrackedCoin existing = findById(id);

        existing.setSymbol(updated.getSymbol());
        existing.setAlertPrice(updated.getAlertPrice());
        existing.setAlertTriggered(false); // reset alert if value changed

        return repository.save(existing);
    }

    /** ✅ Delete alert */
    public void delete(Long id) {
        repository.deleteById(id);
    }

    /**
     * ✅ Check price every 10 seconds and trigger alert
     */
    @Scheduled(fixedRate = 10_000)
    public void checkAlerts() {
        List<TrackedCoin> trackedCoins = repository.findAll();

        for (TrackedCoin coin : trackedCoins) {
            // Skip already triggered alerts
            if (coin.isAlertTriggered()) continue;

            double latestPrice = cryptoPriceService.getLatest(coin.getSymbol()).getPrice();

            if (latestPrice >= coin.getAlertPrice()) {
                System.out.println("🚨 ALERT: " + coin.getSymbol() +
                        " reached " + latestPrice + " (target: " + coin.getAlertPrice() + ")");

                coin.setAlertTriggered(true);
                repository.save(coin);

                // TODO: Send notification (email, SMS, Telegram, WebSocket, etc.)
            }
        }
    }
}
