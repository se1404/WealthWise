package com.bourse.wealthwise.repository;

import com.bourse.wealthwise.domain.entity.security.SecurityPrice;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.*;

@Component
public class SecurityPriceRepository {

    private final Map<String, Map<LocalDate, Double>> priceMap = new HashMap<>();

    public void addPrice(String isin, LocalDate date, double price) {
        priceMap
                .computeIfAbsent(isin, k -> new HashMap<>())
                .put(date, price);
    }

    public Optional<Double> getPrice(String isin, LocalDate date) {
        return Optional.ofNullable(
                priceMap.getOrDefault(isin, Collections.emptyMap()).get(date)
        );
    }

    public List<SecurityPrice> getPricesForSecurity(String isin) {
        Map<LocalDate, Double> datePriceMap = priceMap.getOrDefault(isin, Collections.emptyMap());
        List<SecurityPrice> prices = new ArrayList<>();
        datePriceMap.forEach((date, price) -> prices.add(new SecurityPrice(isin, date, price)));
        return prices;
    }

    public void clear() {
        priceMap.clear();
    }
}
