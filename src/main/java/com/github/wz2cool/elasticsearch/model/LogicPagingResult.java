package com.github.wz2cool.elasticsearch.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Frank
 **/
public class LogicPagingResult<T> {
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int pageSize;
    private long startPageId;
    private long endPageId;
    private List<T> list = new ArrayList<>();

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public long getStartPageId() {
        return startPageId;
    }

    public void setStartPageId(long startPageId) {
        this.startPageId = startPageId;
    }

    public long getEndPageId() {
        return endPageId;
    }

    public void setEndPageId(long endPageId) {
        this.endPageId = endPageId;
    }

    public List<T> getList() {
        return list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    public void setList(List<T> list) {
        this.list = list == null ? new ArrayList<>() : new ArrayList<>(list);
    }

    /**
     * 转化对象
     *
     * @param mapper 映射mapper
     * @param <R>    泛型
     * @return 泛型分页对象
     */
    @SuppressWarnings("unchecked")
    public <R> LogicPagingResult<R> convert(Function<? super T, ? extends R> mapper) {
        List<R> collect = this.getList().stream().map(mapper).collect(Collectors.toList());
        LogicPagingResult<R> dto = ((LogicPagingResult<R>) this);
        dto.setList(collect);
        return dto;
    }
}
