package com.kcosic.jwp.shared.model;

import com.kcosic.jwp.shared.model.entities.BaseEntity;

import java.util.List;

public class PaginationResponse <T extends BaseEntity> {
    List<T> data;
    int pages;
    int perPage;
    int total;
    boolean canGoNext;
    boolean canGoPrev;
    int currentPage;

    public PaginationResponse(List<T> data, int pages, int perPage, int total, boolean canGoNext, boolean canGoPrev, int currentPage) {
        this.data = data;
        this.pages = pages;
        this.perPage = perPage;
        this.total = total;
        this.canGoNext = canGoNext;
        this.canGoPrev = canGoPrev;
        this.currentPage = currentPage;
    }

    public List<T> getData() {
        return data;
    }

    public int getPages() {
        return pages;
    }

    public int getPerPage() {
        return perPage;
    }

    public int getTotal() {
        return total;
    }

    public boolean isCanGoNext() {
        return canGoNext;
    }

    public boolean isCanGoPrev() {
        return canGoPrev;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
