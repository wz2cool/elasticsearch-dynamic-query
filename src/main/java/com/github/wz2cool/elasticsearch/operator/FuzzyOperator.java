package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class FuzzyOperator implements IFilterOperator<String> {

    private final String value;

    FuzzyOperator(String value) {
        this.value = value;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        return new FuzzyQueryBuilder(columnName, value);
    }
}
