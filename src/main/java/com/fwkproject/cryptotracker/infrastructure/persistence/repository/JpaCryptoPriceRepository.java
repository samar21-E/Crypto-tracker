package com.fwkproject.cryptotracker.infrastructure.persistence.repository;

import com.fwkproject.cryptotracker.infrastructure.persistence.entity.CryptoPriceEntity;
import com.fwkproject.cryptotracker.infrastructure.persistence.entity.CryptoPriceId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.Instant;
import java.util.List;

public interface JpaCryptoPriceRepository extends JpaRepository<CryptoPriceEntity, CryptoPriceId> {
    
    // Find latest price for a symbol
    CryptoPriceEntity findFirstById_SymbolOrderById_TimeDesc(String symbol);

    // Find history within range
    List<CryptoPriceEntity> findById_SymbolAndId_TimeBetweenOrderById_TimeAsc(String symbol, Instant from, Instant to);
}
