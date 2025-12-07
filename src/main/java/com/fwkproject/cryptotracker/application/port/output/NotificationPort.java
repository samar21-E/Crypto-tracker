package com.fwkproject.cryptotracker.application.port.output;

import com.fwkproject.cryptotracker.domain.model.TrackedCoin;

public interface NotificationPort {
    void sendPriceAlert(TrackedCoin coin, double currentPrice);
}
