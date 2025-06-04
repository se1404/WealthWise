package com.bourse.wealthwise.domain.entity.action;

import com.bourse.wealthwise.domain.entity.action.utils.ActionVisitor;
import com.bourse.wealthwise.domain.entity.balance.BalanceChange;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import com.bourse.wealthwise.domain.entity.security.SecurityChange;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@SuperBuilder
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public abstract class BaseAction {
    @EqualsAndHashCode.Include
    protected String uuid;

    protected LocalDateTime datetime;

    protected Portfolio portfolio;

    protected String tracing_number;

    protected ActionType actionType;
    @Setter
    protected Actor actor;

    public abstract List<BalanceChange> getBalanceChanges();
    public abstract List<SecurityChange> getSecurityChanges();
    public abstract String accept(ActionVisitor visitor);


    //TODO: delete any change if update on action occured



}
