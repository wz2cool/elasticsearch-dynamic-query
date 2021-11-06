package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.FilterOperators;
import com.github.wz2cool.elasticsearch.operator.IFilterOperator;
import com.github.wz2cool.elasticsearch.operator.MultiMatchOperator;
import com.github.wz2cool.elasticsearch.operator.MultiMatchOperators;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> {

    private final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();

    public S and(GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators, IFilterOperator<String>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable, GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators, IFilterOperator<String>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators, IFilterOperator<String>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators, IFilterOperator<String>> operatorFunc) {
        return null;
    }

    public S and(String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return and(true, FilterMode.MUST, value, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 String value,
                 Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return null;
    }

    public S and(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, FilterMode.MUST, groupConsumer);
    }

    public S and(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(enable, FilterMode.MUST, groupConsumer);
    }

    public S and(FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, filterMode, groupConsumer);
    }

    public S and(boolean enable, FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (enable) {
            FilterGroup<T> filterGroup = new FilterGroup<>();
            final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).buildQuery();
            if (filterMode == FilterMode.MUST) {
                booleanQueryBuilder.must(queryBuilder);
            } else if (filterMode == FilterMode.MUST_NOT) {
                booleanQueryBuilder.mustNot(queryBuilder);
            } else {
                booleanQueryBuilder.filter(queryBuilder);
            }
        }
        return (S) this;
    }

    public S or(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return or(true, groupConsumer);
    }

    public S or(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (enable) {
            FilterGroup<T> filterGroup = new FilterGroup<>();
            booleanQueryBuilder.should(groupConsumer.apply(filterGroup).buildQuery());
        }
        return (S) this;
    }

    public QueryBuilder buildQuery() {
        return this.booleanQueryBuilder;
    }
}
