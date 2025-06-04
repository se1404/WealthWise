package com.bourse.wealthwise.domain.services;

import com.bourse.wealthwise.domain.entity.action.utils.ActionDescriptionVisitor;
import com.bourse.wealthwise.domain.entity.action.utils.ActionVisitor;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import com.bourse.wealthwise.repository.ActionRepository;
import com.bourse.wealthwise.repository.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PortfolioActionService {

    private final ActionRepository actionRepository;
    private final PortfolioRepository portfolioRepository;

    public List<String> getActionsForPortfolio(String portfolioId, LocalDateTime localDateTime) {
        ActionVisitor visitor = new ActionDescriptionVisitor();

        return actionRepository.findAllActionsOf(
                        portfolioRepository.findById(portfolioId)
                                .orElseThrow(() -> new IllegalArgumentException("Portfolio not found"))
                                .getUuid()
                ).stream()
                .filter(action -> action.getDatetime() != null && action.getDatetime().isBefore(localDateTime))
                .map(action -> action.accept(visitor))
                .toList();
    }
}
