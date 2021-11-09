package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;

public interface IFilterOperator<R> {

    FilterMode getDefaultFilterMode();

    QueryBuilder buildQuery(String columnName);

    default Object getFilterValue(Object value) {
        if (value instanceof Date) {
            return ((Date) value).getTime();
        } else if (value instanceof BigDecimal) {
            return value.toString();
        }
        return value;
    }
}
