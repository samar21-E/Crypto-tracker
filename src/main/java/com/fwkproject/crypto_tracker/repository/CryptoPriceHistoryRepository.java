package com.fwkproject.crypto_tracker.repository;

import com.fwkproject.crypto_tracker.model.CryptoPriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CryptoPriceHistoryRepository extends JpaRepository<CryptoPriceHistory, Long> {

    List<CryptoPriceHistory> findBySymbolOrderByTimeAsc(String symbol);
}
