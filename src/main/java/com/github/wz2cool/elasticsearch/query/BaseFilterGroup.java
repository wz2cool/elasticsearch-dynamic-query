package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.GetIntegerPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
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
    private static final MultiMatchOperators MULTI_MATCH_OPERATORS = new MultiMatchOperators();

    public S and(GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable, GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetStringPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(String value, Function<MultiMatchOperators, MultiMatchOperator<T>> operatorFunc) {
        return and(true, FilterMode.MUST, value, operatorFunc);
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

    public S and(boolean enable,
                 FilterMode filterMode,
                 String value,
                 Function<MultiMatchOperators, MultiMatchOperator<T>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final MultiMatchOperator<T> operator = operatorFunc.apply(MULTI_MATCH_OPERATORS);
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        return andInternal(filterMode, queryBuilder);
    }

    public S and(boolean enable, FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (!enable) {
            return (S) this;
        }
        FilterGroup<T> filterGroup = new FilterGroup<>();
        final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).buildQuery();
        return andInternal(filterMode, queryBuilder);
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

    private <R> S andInternal(boolean enable,
                              FilterMode filterMode,
                              GetPropertyFunction<T, R> getPropertyFunc,
                              Function<FilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final FilterOperators<R> objectFilterOperators = new FilterOperators<>();
        final IFilterOperator<R> filterOperator = operatorFunc.apply(objectFilterOperators);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(getPropertyFunc);
        return andInternal(filterMode, queryBuilder);
    }

    private S andInternal(
            FilterMode filterMode,
            QueryBuilder queryBuilder) {
        if (filterMode == FilterMode.MUST) {
            booleanQueryBuilder.must(queryBuilder);
        } else if (filterMode == FilterMode.MUST_NOT) {
            booleanQueryBuilder.mustNot(queryBuilder);
        } else {
            booleanQueryBuilder.filter(queryBuilder);
        }
        return (S) this;
    }

    public QueryBuilder buildQuery() {
        return this.booleanQueryBuilder;
    }
}
