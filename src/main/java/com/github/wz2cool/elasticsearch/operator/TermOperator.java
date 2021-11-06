package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;

public class TermOperator<R> implements IFilterOperator<R> {

    private final R value;

    public TermOperator(R value) {
        this.value = value;
    }

    @Override
    public <T> QueryBuilder getQueryBuilder(GetPropertyFunction<T, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new TermQueryBuilder(columnInfo.getColumnName(), value);
    }
}
