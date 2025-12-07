package com.fwkproject.cryptotracker.application.port.output;

public interface CryptoPriceProvider {
    double getPrice(String symbol);
}
