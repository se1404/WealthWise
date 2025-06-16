package com.bourse.wealthwise.domain.entity.action;

import com.bourse.wealthwise.domain.entity.action.utils.ActionVisitor;
import com.bourse.wealthwise.domain.entity.balance.BalanceChange;
import com.bourse.wealthwise.domain.entity.security.Security;
import com.bourse.wealthwise.domain.entity.security.SecurityChange;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
public class Buy extends BaseAction {
    private final Security security;
    private final BigInteger volume;
    private final Integer price;
    private final BigInteger totalValue;

    @Override
    public List<BalanceChange> getBalanceChanges() {
        return List.of(
                BalanceChange.builder()
                        .uuid(UUID.randomUUID())
                        .portfolio(this.portfolio)
                        .datetime(this.datetime)
                        .change_amount(this.totalValue.negate()) // Deduct total cost
                        .action(this)
                        .build()
        );
    }

    @Override
    public List<SecurityChange> getSecurityChanges() {
        return List.of(
                SecurityChange.builder()
                        .uuid(UUID.randomUUID())
                        .portfolio(this.portfolio)
                        .datetime(this.datetime)
                        .security(this.security)
                        .volumeChange(this.volume)
                        .action(this)
                        .build()
        );
    }

    @Override
    public String accept(ActionVisitor visitor) {
        return visitor.visit(this);
    }

    private void setActionType() {
        this.actionType = ActionType.BUY;
    }

}
