package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;

public class RegexpOperator implements IFilterOperator<String> {

    private final String regexp;

    public RegexpOperator(String regexp) {
        this.regexp = regexp;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, String> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new RegexpQueryBuilder(columnInfo.getColumnName(), regexp);
    }
}
