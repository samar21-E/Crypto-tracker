package com.fwkproject.crypto_tracker.repository;

import com.fwkproject.crypto_tracker.model.CryptoPrice;
import com.fwkproject.crypto_tracker.model.CryptoPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CryptoPriceRepository extends JpaRepository<CryptoPrice, CryptoPriceId> {

    @Query("""
        SELECT c FROM CryptoPrice c 
        WHERE c.id.symbol = :symbol 
        ORDER BY c.id.time DESC
        """)
    List<CryptoPrice> findLatestBySymbol(String symbol);

    @Query("""
        SELECT c FROM CryptoPrice c 
        WHERE c.id.symbol = :symbol 
        ORDER BY c.id.time DESC
        """)
    CryptoPrice findTopBySymbolOrderByTimeDesc(String symbol);
}
