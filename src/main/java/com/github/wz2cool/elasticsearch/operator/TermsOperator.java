package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
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
    public FilterMode getDefaultFilterMode() {
        return FilterMode.FILTER;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        return new TermsQueryBuilder(columnName,
                values.stream().map(this::getFilterValue).collect(Collectors.toList()));
    }
}
