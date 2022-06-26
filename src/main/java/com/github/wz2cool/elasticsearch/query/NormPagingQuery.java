package com.github.wz2cool.elasticsearch.query;

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
        this.setClazz(clazz);
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static <T> NormPagingQuery<T> createQuery(
            Class<T> clazz, int pageNum, int pageSize) {
        return new NormPagingQuery<>(clazz, pageNum, pageSize);
    }

}
