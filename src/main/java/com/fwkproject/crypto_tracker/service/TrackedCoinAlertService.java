package com.fwkproject.crypto_tracker.service;

import com.fwkproject.crypto_tracker.model.TrackedCoin;
import com.fwkproject.crypto_tracker.repository.TrackedCoinRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackedCoinAlertService {

    private final TrackedCoinRepository trackedCoinRepository;
    private final BinancePriceService binancePriceService;
    private final AlertNotificationService alertNotificationService; // 🔔 unified notifier

    @Value("${feature.alerts.enabled:true}")
    private boolean alertsEnabled;

    @Value("${alerts.check-interval-ms:30000}")
    private long checkIntervalMs;

    @Scheduled(fixedDelayString = "${alerts.check-interval-ms:30000}")
    public void checkAlerts() {

        if (!alertsEnabled) {
            log.debug("Price alerts are DISABLED (feature.alerts.enabled=false)");
            return;
        }

        List<TrackedCoin> coins = trackedCoinRepository.findAll();

        if (coins.isEmpty()) {
            log.debug("No tracked coins found – nothing to check.");
            return;
        }

        for (TrackedCoin coin : coins) {
            try {
                double currentPrice = binancePriceService.getPrice(coin.getSymbol());

                log.debug(
                        "Checking {} → current={}, alert={}, triggered={}",
                        coin.getSymbol(),
                        currentPrice,
                        coin.getAlertPrice(),
                        coin.isAlertTriggered()
                );

                // Fire alert once: price >= alertPrice and NOT triggered before
                if (!coin.isAlertTriggered()
                        && coin.getAlertPrice() != null
                        && currentPrice >= coin.getAlertPrice()) {

                    log.info(
                            "🚨 ALERT triggered for {} → current {} >= alert {}",
                            coin.getSymbol(),
                            currentPrice,
                            coin.getAlertPrice()
                    );

                    // 🔔 Send notification via Email/Telegram/Webhook
                    alertNotificationService.sendPriceAlert(coin, currentPrice);

                    // Mark as triggered to avoid duplicates
                    coin.setAlertTriggered(true);
                    trackedCoinRepository.save(coin);
                }

            } catch (Exception e) {
                log.error("Error while checking alert for {}", coin.getSymbol(), e);
            }
        }
    }
}
