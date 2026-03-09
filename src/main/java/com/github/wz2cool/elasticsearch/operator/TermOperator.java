package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;

public class TermOperator<R extends Comparable> implements IArrayFilterOperator<R> {

    private final R value;
    private float boost = 1.0f;

    TermOperator(R value) {
        this.value = value;
    }

    public TermOperator<R> boost(float boost) {
        this.boost = boost;
        return this;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.FILTER;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        return new TermQueryBuilder(columnName, getFilterValue(value)).boost(boost);
    }
}
