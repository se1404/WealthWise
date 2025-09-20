package com.bourse.wealthwise.domain.services;

import com.bourse.wealthwise.domain.entity.action.Buy;
import com.bourse.wealthwise.domain.entity.security.Security;
import com.bourse.wealthwise.domain.entity.security.SecurityChange;
import com.bourse.wealthwise.repository.ActionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigInteger;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PortfolioHoldingTest {

    @Autowired
    private ActionRepository actionRepository;

    @Test
    void buyAction_volumeChange_shouldBeBasedOnVolumeNotTotalValue() {
        Buy buyAction = Buy.builder()
                .volume(BigInteger.valueOf(10))
                .totalValue(BigInteger.valueOf(2000))
                .build();
        SecurityChange securityChange = buyAction.getSecurityChanges().get(0);
        assertThat(securityChange.getVolumeChange()).isEqualTo(BigInteger.valueOf(10));
    }

}