package com.bourse.wealthwise.domain.entity.portfolio;


import com.bourse.wealthwise.domain.entity.account.User;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Portfolio {

    @EqualsAndHashCode.Include
    private String uuid;

    private String name;

    private User portfolioManager;

    @Setter
    private PortfolioStatus status;

    public Portfolio(String uuid, User portfolioManager, String name) {
        this.uuid = uuid;
        this.portfolioManager = portfolioManager;
        this.name = name;
    }
}
