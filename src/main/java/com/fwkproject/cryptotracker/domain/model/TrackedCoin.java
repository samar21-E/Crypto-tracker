package com.fwkproject.cryptotracker.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrackedCoin {
    private Long id;
    private String symbol;
    private Double alertPrice;
    private boolean alertTriggered;
}
