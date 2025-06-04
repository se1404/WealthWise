package com.bourse.wealthwise.domain.services;

import com.bourse.wealthwise.repository.ActionRepository;
import com.bourse.wealthwise.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BalanceActionService {

    private final ActionRepository actionRepository;
    private final PortfolioRepository portfolioRepository;

    public BigInteger getBalanceForPortfolio(String portfolioId, LocalDateTime localDateTime) {
        return actionRepository.findAllActionsOf(
                        portfolioRepository.findById(portfolioId)
                                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"))
                                .getUuid()
                ).stream()
                .filter(action -> action.getDatetime() != null && action.getDatetime().isBefore(localDateTime))
                .map(action -> action.getBalanceChanges().getFirst().getChange_amount())
                .reduce(BigInteger.ZERO, BigInteger::add);
    }

}
