package com.pp.common;

import org.springframework.data.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by astepanov
 */
public class PageBuilder<T> {

    private List<T> elements = new ArrayList<>();
    private Pageable pageRequest;
    private int totalElements;

    public PageBuilder() {}

    public PageBuilder<T> elements(final List<T> elements) {
        this.elements = elements;
        return this;
    }

    public PageBuilder<T> pageRequest(final Pageable pageRequest) {
        this.pageRequest = pageRequest;
        return this;
    }

    public PageBuilder<T> totalElements(final int totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public Page<T> build() {
        return new PageImpl<T>(elements, pageRequest, totalElements);
    }

    public Page<T> createOneElemPage(final T elem, final int pageNumber, final int pageSize) {
        final Sort sort = new Sort(Sort.Direction.ASC, "title");
        pageRequest = new PageRequest(pageNumber, pageSize, sort);
        final Page<T> emptyPage = elements(Collections.singletonList((T) elem))
                .pageRequest(pageRequest)
                .totalElements(0)
                .build();
        return emptyPage;
    }
}