package com.bourse.wealthwise.repository;

import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class PortfolioRepository {

    private final Map<String, Portfolio> portfolios = new ConcurrentHashMap<>();

    public Portfolio save(Portfolio portfolio) {
        portfolios.put(portfolio.getUuid(), portfolio);
        return portfolio;
    }

    public Optional<Portfolio> findById(String uuid) {
        return Optional.ofNullable(portfolios.get(uuid));
    }

    public List<Portfolio> findAll() {
        return new ArrayList<>(portfolios.values());
    }

    public void deleteById(String uuid) {
        portfolios.remove(uuid);
    }
}
