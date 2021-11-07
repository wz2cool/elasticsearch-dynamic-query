package io.github.wz2cool.elasticsearch.operator;

import io.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import io.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class PrefixOperator implements IFilterOperator<String> {

    private final String prefix;

    public PrefixOperator(String prefix) {
        this.prefix = prefix;
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, String> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new PrefixQueryBuilder(columnInfo.getColumnName(), prefix);
    }
}
