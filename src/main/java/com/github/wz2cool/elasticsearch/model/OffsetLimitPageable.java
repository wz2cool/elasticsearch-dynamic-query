package com.github.wz2cool.elasticsearch.model;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.Optional;

public class OffsetLimitPageable implements Pageable {

    /**
     * 偏移下标
     */
    private final int offset;
    /**
     * 页码
     */
    private final int pageNumber;
    /**
     * 页数
     */
    private final int pageSize;

    private static final Sort sort = Sort.unsorted();

    private OffsetLimitPageable(int offset, int pageNumber, int pageSize) {
        if (offset < 0) {
            throw new IllegalArgumentException("Offset must not be less than zero!");
        }

        if (pageNumber < 0) {
            throw new IllegalArgumentException("Page index must not be less than zero!");
        }

        if (pageSize < 1) {
            throw new IllegalArgumentException("Page size must not be less than one!");
        }
        this.offset = offset;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
    }

    public static OffsetLimitPageable of(int offset, int pageNumber, int pageSize) {
        return new OffsetLimitPageable(offset, pageNumber, pageSize);
    }

    @Override
    public boolean isPaged() {
        return Pageable.super.isPaged();
    }

    @Override
    public boolean isUnpaged() {
        return Pageable.super.isUnpaged();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getPageNumber()
     */
    @Override
    public int getPageNumber() {
        return pageNumber;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getPageSize()
     */
    @Override
    public int getPageSize() {
        return pageSize;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getOffset()
     */
    @Override
    public long getOffset() {
        return offset + (long) pageNumber * pageSize;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#getSort()
     */
    @Override
    public Sort getSort() {
        return sort;
    }

    @Override
    public Sort getSortOr(Sort sort) {
        return Pageable.super.getSortOr(sort);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#next()
     */
    public Pageable next() {
        return of(offset, pageNumber + 1, pageSize);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#previousOrFirst()
     */
    public Pageable previousOrFirst() {
        return hasPrevious() ? of(offset, pageNumber - 1, pageSize) : first();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#first()
     */
    public Pageable first() {
        return of(offset, 0, pageSize);
    }

    @Override
    public Pageable withPage(int pageNumber) {
        return of(offset, pageNumber, pageSize);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.domain.Pageable#hasPrevious()
     */
    public boolean hasPrevious() {
        return pageNumber > 0;
    }

    @Override
    public Optional<Pageable> toOptional() {
        return Pageable.super.toOptional();
    }
}
