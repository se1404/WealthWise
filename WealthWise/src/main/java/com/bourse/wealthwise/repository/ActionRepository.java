package com.bourse.wealthwise.repository;

import com.bourse.wealthwise.domain.entity.action.BaseAction;
import com.bourse.wealthwise.domain.entity.portfolio.Portfolio;
import org.springframework.stereotype.Component;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ActionRepository {

    private final Map<String, BaseAction> actions = new ConcurrentHashMap<>();

    public BaseAction save(BaseAction action) {
        actions.put(action.getUuid(), action);
        return action;
    }

    public Optional<BaseAction> findById(String uuid) {
        return Optional.ofNullable(actions.get(uuid));
    }

    public List<BaseAction> findAllActionsOf(String portfolioId) {
        return actions.values().stream()
                .filter(action -> action.getPortfolio().getUuid().equals(portfolioId))
                .sorted(Comparator.comparing(BaseAction::getDatetime))
                .toList();
    }

    public void deleteById(String uuid) {
        actions.remove(uuid);
    }

}
