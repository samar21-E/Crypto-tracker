package com.fwkproject.cryptotracker.infrastructure.persistence;

import com.fwkproject.cryptotracker.domain.model.CryptoPrice;
import com.fwkproject.cryptotracker.domain.repository.CryptoPriceRepository;
import com.fwkproject.cryptotracker.infrastructure.persistence.entity.CryptoPriceEntity;
import com.fwkproject.cryptotracker.infrastructure.persistence.entity.CryptoPriceId;
import com.fwkproject.cryptotracker.infrastructure.persistence.repository.JpaCryptoPriceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CryptoPriceRepositoryAdapter implements CryptoPriceRepository {

    private final JpaCryptoPriceRepository jpaRepository;

    @Override
    public void save(CryptoPrice price) {
        CryptoPriceEntity entity = new CryptoPriceEntity(
                new CryptoPriceId(price.getTime(), price.getSymbol()),
                price.getPrice()
        );
        jpaRepository.save(entity);
    }

    @Override
    public CryptoPrice findLatest(String symbol) {
        CryptoPriceEntity entity = jpaRepository.findFirstById_SymbolOrderById_TimeDesc(symbol);
        if (entity == null) {
            return null;
        }
        return toDomain(entity);
    }

    @Override
    public List<CryptoPrice> findHistory(String symbol, Instant from, Instant to) {
        return jpaRepository.findById_SymbolAndId_TimeBetweenOrderById_TimeAsc(symbol, from, to)
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    private CryptoPrice toDomain(CryptoPriceEntity entity) {
        return new CryptoPrice(
                entity.getId().getTime(),
                entity.getId().getSymbol(),
                entity.getPrice()
        );
    }
}
