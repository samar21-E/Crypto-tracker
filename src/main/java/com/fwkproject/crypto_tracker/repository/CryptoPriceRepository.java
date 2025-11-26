package com.fwkproject.crypto_tracker.repository;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.model.CryptoPriceId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, CryptoPriceId> {

    // Get the latest price for one symbol
    CryptoPrice findFirstById_SymbolOrderById_TimeDesc(String symbol);

    // Get a page of latest prices (for "last N points")
    Page<CryptoPrice> findById_SymbolOrderById_TimeDesc(String symbol, Pageable pageable);

    // Get prices in a time range
    List<CryptoPrice> findById_SymbolAndId_TimeBetweenOrderById_TimeAsc(
            String symbol,
            Instant from,
            Instant to
    );
}
