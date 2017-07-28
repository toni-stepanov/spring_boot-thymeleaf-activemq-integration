package com.pp.domain.page;

import lombok.Getter;
import lombok.Setter;

import java.util.Optional;

/**
 * Created by astepanov
 */
public final class PageNumber {

    private final Optional<Integer> number;

    public PageNumber(Optional<Integer> number) {
        this.number = number;
    }

    public int evalPageNumber(Optional<Integer> pageNumber) {
        return this.number.orElse(1) - 1;
    }
}
