package io.github.wz2cool.elasticsearch.operator;

import io.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import io.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class FuzzyOperator implements IFilterOperator<String> {

    private final String value;

    FuzzyOperator(String value) {
        this.value = value;
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, String> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new FuzzyQueryBuilder(columnInfo.getColumnName(), value);
    }
}
