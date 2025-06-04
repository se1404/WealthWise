package com.bourse.wealthwise.domain.services;

import com.bourse.wealthwise.domain.entity.account.User;
import com.bourse.wealthwise.domain.entity.action.*;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import com.bourse.wealthwise.domain.entity.security.Security;
import com.bourse.wealthwise.repository.ActionRepository;
import com.bourse.wealthwise.repository.PortfolioRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;


@SpringBootTest
public class BalanceActionServiceTest {
    @Autowired
    private PortfolioRepository portfolioRepository;
    @Autowired
    private BalanceActionService balanceActionService;
    @Autowired
    private ActionRepository actionRepository;

    private Portfolio portfolio;
    private Security security;

    @BeforeEach
    public void setUp() {
        User user = User.builder().build();
        this.security = Security.builder().build();
        this.portfolio = new Portfolio("21e42b92-cef6-453f-9e52-fa76b1d830f6",
                user,
                "portfo");

        portfolioRepository.save(portfolio);

    }

    @Test
    public void noActionsForPortfolio_getBalance_zeroBalanceReturned(){
        assertEquals(balanceActionService.getBalanceForPortfolio(
                "21e42b92-cef6-453f-9e52-fa76b1d830f6",
                LocalDateTime.now()), BigInteger.ZERO);
    }

    @Test
    public void newDepositActionEnters_getNeBalance_increasedBalanceReturned(){
        Deposit deposit = Deposit.builder()
                .uuid(UUID.randomUUID().toString())
                .portfolio(portfolio)
                .datetime(LocalDateTime.now().minusMinutes(1))
                .amount(BigInteger.valueOf(1000))
                .actionType(ActionType.DEPOSIT)
                .build();
        actionRepository.save(deposit);

        assertEquals(
                BigInteger.valueOf(1000),
                balanceActionService.getBalanceForPortfolio(
                        "21e42b92-cef6-453f-9e52-fa76b1d830f6",
                        LocalDateTime.now()
                )
        );
        actionRepository.deleteById(deposit.getUuid());
    }

    @Test
    public void newBuyActionEnters_getNewBalance_DecreasedBalanceReturned(){
        Deposit deposit = Deposit.builder()
                .uuid(UUID.randomUUID().toString())
                .portfolio(portfolio)
                .datetime(LocalDateTime.now())
                .amount(BigInteger.valueOf(1000))
                .actionType(ActionType.DEPOSIT)
                .build();
        actionRepository.save(deposit);

        Buy buy = Buy.builder()
                .uuid(UUID.randomUUID().toString())
                .portfolio(portfolio)
                .datetime(LocalDateTime.now())
                .volume(BigInteger.valueOf(100))
                .price(3)
                .totalValue(BigInteger.valueOf(300))
                .security(security)
                .actionType(ActionType.BUY)
                .build();
        actionRepository.save(buy);

        assertEquals(
                BigInteger.valueOf(1000 - 300),
                balanceActionService.getBalanceForPortfolio(
                        "21e42b92-cef6-453f-9e52-fa76b1d830f6",
                        LocalDateTime.now()
                )
        );
        actionRepository.deleteById(buy.getUuid());
        actionRepository.deleteById(deposit.getUuid());
    }

    @Test
    public void newSaleActionEnters_getNewBalance_increasedBalanceReturned(){
        Sale sale = Sale.builder()
                .uuid(UUID.randomUUID().toString())
                .portfolio(portfolio)
                .datetime(LocalDateTime.now())
                .volume(BigInteger.valueOf(100))
                .price(3)
                .totalValue(BigInteger.valueOf(300))
                .security(security)
                .actionType(ActionType.SALE)
                .build();
        actionRepository.save(sale);

        assertEquals(
                BigInteger.valueOf(300),
                balanceActionService.getBalanceForPortfolio(
                        "21e42b92-cef6-453f-9e52-fa76b1d830f6",
                        LocalDateTime.now()
                )
        );
        actionRepository.deleteById(sale.getUuid());
    }

    @Test
    public void newWithdrawalActionEnters_getNewBalance_decreasedBalanceReturned(){
        Withdrawal withdrawal = Withdrawal.builder()
                .uuid(UUID.randomUUID().toString())
                .portfolio(portfolio)
                .datetime(LocalDateTime.now())
                .amount(BigInteger.valueOf(1000))
                .actionType(ActionType.WITHDRAWAL)
                .build();
        actionRepository.save(withdrawal);

        assertEquals(
                BigInteger.valueOf(-1000),
                balanceActionService.getBalanceForPortfolio(
                        "21e42b92-cef6-453f-9e52-fa76b1d830f6",
                        LocalDateTime.now()
                )
        );
        actionRepository.deleteById(withdrawal.getUuid());
    }

}