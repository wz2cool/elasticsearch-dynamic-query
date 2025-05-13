package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.model.QueryMode;

/**
 * @author Frank
 **/
public class NormPagingQuery<T> extends BaseDynamicQuery<T, NormPagingQuery<T>> {
    private final int pageNum;
    private final int pageSize;

    public int getPageNum() {
        return pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    private NormPagingQuery(Class<T> clazz, int pageNum, int pageSize) {
        this(clazz, QueryMode.QUERY, pageNum, pageSize);
    }

    private NormPagingQuery(Class<T> clazz, QueryMode queryMode, int pageNum, int pageSize) {
        this.setClazz(clazz);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.queryMode = queryMode;
    }

    public static <T> NormPagingQuery<T> createQuery(
            Class<T> clazz, int pageNum, int pageSize) {
        return new NormPagingQuery<>(clazz, pageNum, pageSize);
    }

    public static <T> NormPagingQuery<T> createQuery(
            Class<T> clazz, QueryMode queryMode, int pageNum, int pageSize) {
        return new NormPagingQuery<>(clazz, queryMode, pageNum, pageSize);
    }
}
