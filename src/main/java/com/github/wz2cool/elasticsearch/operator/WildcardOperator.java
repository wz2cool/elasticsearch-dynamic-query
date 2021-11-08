package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;

public class WildcardOperator implements IFilterOperator<String> {

    private final String query;

    public WildcardOperator(String query) {
        this.query = query;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        return new WildcardQueryBuilder(columnName, query);
    }
}
