package com.bourse.wealthwise.domain.entity.security;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.LinkedList;
import java.util.List;

@Getter
@ToString
@Builder
public class Security {

    private String name ;

    private String symbol;

    @Builder.Default
    private SecurityType securityType = SecurityType.STOCK;

    private String isin;



}
