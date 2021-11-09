package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;

public class TermOperator<R extends Comparable> implements IArrayFilterOperator<R> {

    private final R value;

    TermOperator(R value) {
        this.value = value;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.FILTER;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        return new TermQueryBuilder(columnName, getFilterValue(value));
    }
}
