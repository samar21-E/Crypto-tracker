package com.fwkproject.cryptotracker.infrastructure.adapter;

import com.fwkproject.cryptotracker.application.port.output.CryptoPriceProvider;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
public class BinanceCryptoPriceProvider implements CryptoPriceProvider {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String BINANCE_API = "https://api.binance.com/api/v3/ticker/price?symbol=";

    @Override
    public double getPrice(String symbol) {
        try {
            BinancePriceResponse response =
                    restTemplate.getForObject(BINANCE_API + symbol, BinancePriceResponse.class);

            if (response == null || response.getPrice() == null) {
                throw new RuntimeException("Invalid Binance response for " + symbol);
            }

            return Double.parseDouble(response.getPrice());

        } catch (Exception e) {
            log.error("Error fetching price for {}", symbol, e);
            throw new RuntimeException("Could not fetch price for " + symbol);
        }
    }

    @Data
    private static class BinancePriceResponse {
        private String symbol;
        private String price;
    }
}
