package com.bourse.wealthwise.domain.entity.action.utils;


import com.bourse.wealthwise.domain.entity.action.Buy;
import com.bourse.wealthwise.domain.entity.action.Deposit;
import com.bourse.wealthwise.domain.entity.action.Sale;
import com.bourse.wealthwise.domain.entity.action.Withdrawal;

public interface ActionVisitor {
    String visit(Buy buy);
    String visit(Sale sale);
    String visit(Deposit deposit);
    String visit(Withdrawal withdrawal);
}