package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.operator.*;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Function;
import java.util.function.UnaryOperator;

public class AndOrFilterGroup<T, S extends AndOrFilterGroup<T, S>> extends AndFilterGroup<T, S> {

    /// region or single

    public <R extends Comparable> S or(
            GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S or(
            boolean enable,
            GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, getSingleFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region or single nested one

    public <P1, R extends Comparable> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1, R extends Comparable> S or(
            boolean enable,
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, getSingleFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region or array

    public <R extends Comparable> S or(
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S or(
            boolean enable,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region or array nested one

    public <P1, R extends Comparable> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1, R extends Comparable> S or(
            boolean enable,
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region group

    public S or(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return or(true, groupConsumer);
    }

    public S or(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (enable) {
            FilterGroup<T> filterGroup = new FilterGroup<>();
            booleanQueryBuilder.should(groupConsumer.apply(filterGroup).getFilterQuery());
        }
        return (S) this;
    }

    public S or(FilterGroup<T> filterGroup) {
        return or(true, filterGroup);
    }

    public S or(boolean enable, FilterGroup<T> filterGroup) {
        if (enable) {
            booleanQueryBuilder.should(filterGroup.getFilterQuery());
        }
        return (S) this;
    }

    public S or(String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return this.or(true, value, operatorFunc);
    }

    public S or(boolean enable, String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        if (enable) {
            MultiMatchOperator<T> operator = (MultiMatchOperator) operatorFunc.apply(this.getMultiMatchOperators());
            QueryBuilder queryBuilder = operator.buildQuery(value);
            booleanQueryBuilder.should(queryBuilder);
        }
        return (S) this;
    }
    /// endregion

    /// region custom

    public S or(QueryBuilder queryBuilder) {
        return or(true, queryBuilder);
    }

    public S or(boolean enable, QueryBuilder queryBuilder) {
        if (!enable) {
            return (S) this;
        }
        this.booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    /// endregion

    /// region internal

    protected <R extends Comparable> S orInternal(
            boolean enable,
            GetPropertyFunction<T, R> getPropertyFunc,
            SingleFilterOperators<R> singleFilterOperators,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(singleFilterOperators);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(getColumnName(getPropertyFunc));
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    protected <P1, R extends Comparable> S orInternal(
            boolean enable,
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            SingleFilterOperators<R> singleFilterOperators,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(singleFilterOperators);
        String columnName = getColumnName(getP1Func, getPropertyFunc);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(columnName);
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    protected <R extends Comparable> S orInternal(
            boolean enable,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            ArrayFilterOperators<R> arrayFilterOperators,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IArrayFilterOperator<R> apply = operatorFunc.apply(arrayFilterOperators);
        final QueryBuilder queryBuilder = apply.buildQuery(getColumnName(getPropertyFunc));
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    protected <P1, R extends Comparable> S orInternal(
            boolean enable,
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            ArrayFilterOperators<R> arrayFilterOperators,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IArrayFilterOperator<R> apply = operatorFunc.apply(arrayFilterOperators);
        String columnName = getColumnName(getP1Func, getPropertyFunc);
        final QueryBuilder queryBuilder = apply.buildQuery(columnName);
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    /// endregion

}
