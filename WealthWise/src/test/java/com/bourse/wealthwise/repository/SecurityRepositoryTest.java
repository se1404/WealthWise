package com.bourse.wealthwise.repository;

import com.bourse.wealthwise.domain.entity.security.Security;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
public class SecurityRepositoryTest {
    @Autowired
    private SecurityRepository securityRepository;


    @BeforeEach
    public void setUp() {
        Security a = Security.builder().isin("1234567890").name("foolad-mobarake").symbol("foolad").build();
        securityRepository.addSecurity(a);
    }

    @Test
    public void someSecurityExists_tryToGetSecurityByISIN_securityReturnedCorrectly(){
        Security foundSecurity = securityRepository.findSecurityByIsin("1234567890");

        assertThat(foundSecurity.getName()).isEqualTo("foolad-mobarake");
        assertThat(foundSecurity.getSymbol()).isEqualTo("foolad");
        assertThat(foundSecurity.getIsin()).isEqualTo("1234567890");
    }


    @Test
    public void someSecurityExists_tryToGetSecurityByWrongISIN_noSecurityFound(){
        Security foundSecurity = securityRepository.findSecurityByIsin("0987654321");
        assertNull(foundSecurity);
    }


}
