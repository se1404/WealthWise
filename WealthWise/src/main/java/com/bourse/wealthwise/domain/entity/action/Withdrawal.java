package com.bourse.wealthwise.domain.entity.action;

import com.bourse.wealthwise.domain.entity.action.utils.ActionVisitor;
import com.bourse.wealthwise.domain.entity.balance.BalanceChange;
import com.bourse.wealthwise.domain.entity.security.SecurityChange;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.math.BigInteger;
import java.util.List;
import java.util.UUID;

@SuperBuilder
@Getter
public class Withdrawal extends BaseAction {
    private BigInteger amount;

    @Override
    public List<BalanceChange> getBalanceChanges() {
        return List.of(
                BalanceChange.builder()
                        .uuid(UUID.randomUUID())
                        .portfolio(this.portfolio)
                        .datetime(this.datetime)
                        .change_amount(this.amount.negate()) // -1 * amount
                        .action(this).build()
        );
    }

    @Override
    public List<SecurityChange> getSecurityChanges(){
        return List.of();
    }

    private void setActionType(){
        this.actionType = ActionType.WITHDRAWAL;
    }

    @Override
    public String accept(ActionVisitor visitor) {
        return visitor.visit(this);
    }
}
