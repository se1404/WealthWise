package com.bourse.wealthwise.domain.entity.balance;

import com.bourse.wealthwise.domain.entity.action.BaseAction;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.UUID;

@Getter
@Builder
public class BalanceChange {
    private UUID uuid;
    private LocalDateTime datetime;
    private Portfolio portfolio;
    private BigInteger change_amount;
    private BaseAction action;




}
