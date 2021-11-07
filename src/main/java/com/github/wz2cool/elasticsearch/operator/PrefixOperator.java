package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
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
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, String> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new PrefixQueryBuilder(columnInfo.getColumnName(), prefix);
    }
}
