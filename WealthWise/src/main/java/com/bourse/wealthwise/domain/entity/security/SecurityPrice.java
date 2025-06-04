package com.bourse.wealthwise.domain.entity.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@ToString
@AllArgsConstructor
public class SecurityPrice {
    private final String isin;
    private final LocalDate date;
    private final double price;
}
