package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
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
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, String> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new WildcardQueryBuilder(columnInfo.getColumnName(), query);
    }
}
