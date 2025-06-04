package com.bourse.wealthwise.domain.entity.account;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;


@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
public class User {
    @Getter
    private String firstName;

    @Getter
    private String lastName;

    @Getter
    @EqualsAndHashCode.Include
    private String uuid;


}
