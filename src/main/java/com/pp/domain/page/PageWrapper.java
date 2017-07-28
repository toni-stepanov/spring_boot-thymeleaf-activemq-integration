package com.pp.domain.page;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

/**
 * Created by astepanov
 */
@Getter
@Setter
public final class PageWrapper<T> {

    private final int number;
    private final Page<T> content;
    private final int totalPages;

    public PageWrapper(final Page content) {
        this.number = content.getNumber();
        this.content = content;
        this.totalPages = content.getTotalPages();
    }

}
