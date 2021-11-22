package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.*;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Function;
import java.util.function.UnaryOperator;

class AndFilterGroup<T, S extends AndFilterGroup<T, S>> extends RootFilterGroup<T, S> {

    /// region and single

    public <R extends Comparable> S and(
            GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(true, FilterMode.DEFAULT, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S and(
            boolean enable, GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(enable, FilterMode.DEFAULT, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S and(
            FilterMode filterMode, GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S and(
            boolean enable, FilterMode filterMode, GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, getSingleFilterOperators(), operatorFunc);
    }

    public S and(String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return and(true, value, operatorFunc);
    }

    public S and(boolean enable,
                 String value,
                 Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final MultiMatchOperator<T> operator = operatorFunc.apply(getMultiMatchOperators());
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        return andInternal(operator.getDefaultFilterMode(), queryBuilder);
    }

    /// endregion

    /// region and single nested one

    public <P1, R extends Comparable> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(true, FilterMode.DEFAULT, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1, R extends Comparable> S and(
            boolean enable,
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(enable, FilterMode.DEFAULT, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1, R extends Comparable> S and(
            FilterMode filterMode,
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1, R extends Comparable> S and(
            boolean enable,
            FilterMode filterMode,
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, getSingleFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region and array

    public <R extends Comparable> S and(
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(true, FilterMode.DEFAULT, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    public <R extends Comparable> S and(
            boolean enable,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(enable, FilterMode.DEFAULT, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    public <R extends Comparable> S and(
            FilterMode filterMode,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(true, filterMode, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    public <R extends Comparable> S and(
            boolean enable,
            FilterMode filterMode,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region and array nested one

    public <P1, R extends Comparable> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(true, FilterMode.DEFAULT, getP1Func, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    public <P1, R extends Comparable> S and(
            boolean enable,
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(enable, FilterMode.DEFAULT, getP1Func, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    public <P1, R extends Comparable> S and(
            FilterMode filterMode,
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(true, filterMode, getP1Func, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    public <P1, R extends Comparable> S and(
            boolean enable,
            FilterMode filterMode,
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, getArrayFilterOperators(), operatorFunc);
    }

    /// endregion

    /// region group

    public S and(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, FilterMode.DEFAULT, groupConsumer);
    }

    public S and(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(enable, FilterMode.DEFAULT, groupConsumer);
    }

    public S and(FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, filterMode, groupConsumer);
    }

    public S and(boolean enable, FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (!enable) {
            return (S) this;
        }
        FilterGroup<T> filterGroup = new FilterGroup<>();
        final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).getFilterQuery();
        final FilterMode userFilterMode = FilterMode.DEFAULT.equals(filterMode) ? FilterMode.MUST : filterMode;
        return andInternal(userFilterMode, queryBuilder);
    }

    public S and(FilterGroup<T> filterGroup) {
        return and(true, FilterMode.DEFAULT, filterGroup);
    }

    public S and(boolean enable, FilterGroup<T> filterGroup) {
        return and(enable, FilterMode.DEFAULT, filterGroup);
    }

    public S and(FilterMode filterMode, FilterGroup<T> filterGroup) {
        return and(true, filterMode, filterGroup);
    }

    public S and(boolean enable, FilterMode filterMode, FilterGroup<T> filterGroup) {
        if (!enable) {
            return (S) this;
        }
        final QueryBuilder queryBuilder = filterGroup.getFilterQuery();
        final FilterMode userFilterMode = FilterMode.DEFAULT.equals(filterMode) ? FilterMode.MUST : filterMode;
        return andInternal(userFilterMode, queryBuilder);
    }

    /// endregion

    /// region custom

    public S and(QueryBuilder queryBuilder) {
        return and(true, FilterMode.DEFAULT, queryBuilder);
    }

    public S and(boolean enable, QueryBuilder queryBuilder) {
        return and(enable, FilterMode.DEFAULT, queryBuilder);
    }

    public S and(FilterMode filterMode, QueryBuilder queryBuilder) {
        return and(true, filterMode, queryBuilder);
    }

    public S and(boolean enable, FilterMode filterMode, QueryBuilder queryBuilder) {
        if (!enable) {
            return (S) this;
        }

        FilterMode useFilterMode = FilterMode.DEFAULT.equals(filterMode) ? FilterMode.MUST : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }

    /// endregion

    /// region internal

    protected <R extends Comparable> S andInternal(
            boolean enable,
            FilterMode filterMode,
            GetPropertyFunction<T, R> getPropertyFunc,
            SingleFilterOperators<R> singleFilterOperators,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(singleFilterOperators);
        String columnName = getColumnName(getPropertyFunc);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(columnName);
        FilterMode useFilterMode = FilterMode.DEFAULT.equals(filterMode) ? filterOperator.getDefaultFilterMode() : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }

    protected <P1, R extends Comparable> S andInternal(
            boolean enable,
            FilterMode filterMode,
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc,
            SingleFilterOperators<R> singleFilterOperators,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final String columnName = getColumnName(getP1Func, getPropertyFunc);
        final IFilterOperator<R> filterOperator = operatorFunc.apply(singleFilterOperators);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(columnName);
        FilterMode useFilterMode = FilterMode.DEFAULT.equals(filterMode) ? filterOperator.getDefaultFilterMode() : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }

    protected <R extends Comparable> S andInternal(
            boolean enable,
            FilterMode filterMode,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            ArrayFilterOperators<R> arrayFilterOperators,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IArrayFilterOperator<R> apply = operatorFunc.apply(arrayFilterOperators);
        String columnName = getColumnName(getPropertyFunc);
        final QueryBuilder queryBuilder = apply.buildQuery(columnName);
        FilterMode useFilterMode = FilterMode.DEFAULT.equals(filterMode) ? apply.getDefaultFilterMode() : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }

    protected <P1, R extends Comparable> S andInternal(
            boolean enable,
            FilterMode filterMode,
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc,
            ArrayFilterOperators<R> arrayFilterOperators,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IArrayFilterOperator<R> apply = operatorFunc.apply(arrayFilterOperators);
        final String columnName = getColumnName(getP1Func, getPropertyFunc);
        final QueryBuilder queryBuilder = apply.buildQuery(columnName);
        FilterMode useFilterMode = FilterMode.DEFAULT.equals(filterMode) ? apply.getDefaultFilterMode() : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }


    protected S andInternal(
            FilterMode filterMode,
            QueryBuilder queryBuilder) {
        FilterMode useFilterMode = FilterMode.DEFAULT.equals(filterMode) ? FilterMode.MUST : filterMode;
        if (useFilterMode == FilterMode.MUST) {
            booleanQueryBuilder.must(queryBuilder);
        } else if (useFilterMode == FilterMode.MUST_NOT) {
            booleanQueryBuilder.mustNot(queryBuilder);
        } else {
            booleanQueryBuilder.filter(queryBuilder);
        }
        return (S) this;
    }

    /// endregion
}
