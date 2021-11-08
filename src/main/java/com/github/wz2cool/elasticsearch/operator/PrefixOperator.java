package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class PrefixOperator implements IFilterOperator<String> {

    private final String prefix;

    public PrefixOperator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        return new PrefixQueryBuilder(columnName, prefix);
    }
}
