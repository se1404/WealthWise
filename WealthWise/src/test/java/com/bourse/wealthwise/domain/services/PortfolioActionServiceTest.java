package com.bourse.wealthwise.domain.services;

import com.bourse.wealthwise.domain.entity.account.User;
import com.bourse.wealthwise.domain.entity.action.Actor;
import com.bourse.wealthwise.domain.entity.action.BaseAction;
import com.bourse.wealthwise.domain.entity.action.Buy;
import com.bourse.wealthwise.domain.entity.action.utils.ActionDescriptionVisitor;
import com.bourse.wealthwise.domain.entity.action.utils.ActionVisitor;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import com.bourse.wealthwise.domain.entity.portfolio.PortfolioStatus;
import com.bourse.wealthwise.domain.entity.security.Security;
import com.bourse.wealthwise.repository.ActionRepository;
import com.bourse.wealthwise.repository.PortfolioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


@SpringBootTest
public class PortfolioActionServiceTest {
    @Autowired
    private PortfolioActionService portfolioActionService;
    @Autowired
    private PortfolioRepository portfolioRepository;

    private final ActionVisitor visitor = new ActionDescriptionVisitor();
    @Autowired
    private ActionRepository actionRepository;

    @BeforeEach
    public void setUp() {
        User user = User.builder().build();
        Portfolio a = new Portfolio("21e42b92-cef6-453f-9e52-fa76b1d830f6",
                User.builder().build(),
                "first_portfo");
        Portfolio b = new Portfolio("22e42b92-cef6-453f-9e52-fa76b1d830f6",
                user,
                "second_portfo");

        portfolioRepository.save(a);
        portfolioRepository.save(b);
    }

    @Test
    public void somePortfoliosExist_lookForPortfolioWithWrongID_exceptionThrown() {
        assertThrows(IllegalArgumentException.class, () -> {
            portfolioActionService.getActionsForPortfolio(UUID.randomUUID().toString(), LocalDateTime.now());
        });
    }

    @Test
    public void somePortfoliosExist_lookForPortfolioWithNoActions_portfolioFound() {
        assertEquals(portfolioActionService.getActionsForPortfolio(
                "22e42b92-cef6-453f-9e52-fa76b1d830f6",
                LocalDateTime.now()),
                List.of());
    }

    @Test
    public void somePortfoliosExist_lookForPortfolioWithActions_portfolioFound() {
        Portfolio newPortfo = new Portfolio("23e42b92-cef6-453f-9e52-fa76b1d830f6",
                User.builder().build(),
                "new_portfo");
        portfolioRepository.save(newPortfo);

        Buy buy = Buy.builder()
                .uuid(UUID.randomUUID().toString())
                .portfolio(newPortfo)
                .security(Security.builder().build())
                .datetime(LocalDateTime.now())
                .volume(BigInteger.TEN)
                .price(1)
                .totalValue(BigInteger.TEN)
                .actor(Actor.MANUAL)
                .build();

        actionRepository.save(buy);

        assertEquals(portfolioActionService.getActionsForPortfolio(
                        "23e42b92-cef6-453f-9e52-fa76b1d830f6",
                        LocalDateTime.now()),
                List.of(buy.accept(visitor)));
    }
}

