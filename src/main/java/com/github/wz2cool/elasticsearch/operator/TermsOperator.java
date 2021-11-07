package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermsQueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class TermsOperator<R extends Comparable> implements IArrayFilterOperator<R> {

    private List<R> values;

    TermsOperator(Collection<R> values) {
        if (Objects.isNull(values)) {
            this.values = new ArrayList<>();
        } else {
            this.values = new ArrayList<>(values);
        }
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new TermsQueryBuilder(columnInfo.getColumnName(),
                values.stream().map(this::getFilterValue).collect(Collectors.toList()));
    }

    @Override
    public <T> QueryBuilder buildQuery(GetArrayPropertyFunction<T, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        return new TermsQueryBuilder(columnInfo.getColumnName(),
                values.stream().map(this::getFilterValue).collect(Collectors.toList()));
    }
}
