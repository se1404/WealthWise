package com.bourse.wealthwise.domain.entity.security;

import com.bourse.wealthwise.domain.entity.action.BaseAction;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import lombok.Builder;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public class SecurityChange {
    private UUID uuid;
    private LocalDateTime datetime;

    private Portfolio portfolio;

    private Security security;

    private BaseAction action;

    private Boolean isTradable;

    private BigInteger volumeChange;


}
