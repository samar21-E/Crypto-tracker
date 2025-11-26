package com.fwkproject.crypto_tracker.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailAlertService {

    private final JavaMailSender mailSender;

    @Value("${alerts.email.to}")
    private String emailTo;

    @Value("${alerts.email.from}")
    private String emailFrom;

    public void sendPriceAlert(String symbol, double currentPrice, double targetPrice) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo(emailTo);
            msg.setFrom(emailFrom);
            msg.setSubject("🚨 Price Alert: " + symbol);
            msg.setText(
                    "Your tracked crypto has reached the alert price!\n\n" +
                            "Symbol: " + symbol + "\n" +
                            "Current Price: " + currentPrice + "\n" +
                            "Alert Price: " + targetPrice + "\n\n" +
                            "— Crypto Tracker"
            );

            mailSender.send(msg);
            log.info("📧 Email alert sent for {}", symbol);

        } catch (Exception e) {
            log.error("Failed to send email alert for {}", symbol, e);
        }
    }
}
