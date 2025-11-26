package com.fwkproject.crypto_tracker.service;

import com.fwkproject.crypto_tracker.model.TrackedCoin;

public interface AlertNotificationService {

    void sendPriceAlert(TrackedCoin coin, double currentPrice);

}
