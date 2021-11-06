package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;

public class TermOperator<R> implements IFilterOperator<R> {

    private final R value;

    TermOperator(R value) {
        this.value = value;
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new TermQueryBuilder(columnInfo.getColumnName(), getFilterValue(value));
    }
}
