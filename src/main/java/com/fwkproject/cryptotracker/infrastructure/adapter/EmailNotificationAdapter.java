package com.fwkproject.cryptotracker.infrastructure.adapter;

import com.fwkproject.cryptotracker.application.port.output.NotificationPort;
import com.fwkproject.cryptotracker.domain.model.TrackedCoin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailNotificationAdapter implements NotificationPort {

    private final JavaMailSender mailSender;

    @Value("${alerts.email.to:}")
    private String alertRecipient;

    @Value("${alerts.email.from:no-reply@crypto-tracker.local}")
    private String alertFrom;

    @Override
    public void sendPriceAlert(TrackedCoin coin, double currentPrice) {
        if (alertRecipient == null || alertRecipient.isBlank()) {
            log.warn("No alerts.email.to configured – skipping email for {}", coin.getSymbol());
            return;
        }

        String subject = "Price alert: " + coin.getSymbol();
        String text = """
                Price alert triggered!

                Symbol: %s
                Current price: %f
                Alert price: %f

                This alert comes from your Crypto Tracker Project.
                """.formatted(coin.getSymbol(), currentPrice, coin.getAlertPrice());

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(alertFrom);
            message.setTo(alertRecipient);
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            log.info("📧 Email alert sent for {} to {}", coin.getSymbol(), alertRecipient);
        } catch (Exception e) {
            log.error("Failed to send email alert for {}", coin.getSymbol(), e);
        }
    }
}
