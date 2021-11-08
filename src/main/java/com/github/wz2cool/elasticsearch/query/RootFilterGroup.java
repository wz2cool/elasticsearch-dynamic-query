package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import com.github.wz2cool.elasticsearch.operator.ArrayFilterOperators;
import com.github.wz2cool.elasticsearch.operator.IArrayFilterOperator;
import com.github.wz2cool.elasticsearch.operator.IFilterOperator;
import com.github.wz2cool.elasticsearch.operator.SingleFilterOperators;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;

/**
 * @author Frank
 **/
abstract class RootFilterGroup<T, S extends RootFilterGroup<T, S>> {

    protected final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();
    protected static final SingleFilterOperators<String> STRING_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Integer> INTEGER_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<BigDecimal> BIG_DECIMAL_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Boolean> BOOLEAN_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Byte> BYTE_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Date> DATE_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Double> DOUBLE_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Float> FLOAT_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Long> LONG_FILTER_OPERATORS = new SingleFilterOperators<>();
    protected static final SingleFilterOperators<Short> SHORT_FILTER_OPERATORS = new SingleFilterOperators<>();

    protected static final ArrayFilterOperators<String> STRING_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Integer> INTEGER_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<BigDecimal> BIG_DECIMAL_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Boolean> BOOLEAN_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Byte> BYTE_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Date> DATE_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Double> DOUBLE_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Float> FLOAT_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Long> LONG_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    protected static final ArrayFilterOperators<Short> SHORT_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();

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
        final QueryBuilder queryBuilder = filterOperator.buildQuery(getPropertyFunc);
        FilterMode useFilterMode = Objects.isNull(filterMode) ? filterOperator.getDefaultFilterMode() : filterMode;
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
        final QueryBuilder queryBuilder = apply.buildQuery(getPropertyFunc);
        FilterMode useFilterMode = Objects.isNull(filterMode) ? apply.getDefaultFilterMode() : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }

    protected S andInternal(
            FilterMode filterMode,
            QueryBuilder queryBuilder) {
        FilterMode useFilterMode = Objects.isNull(filterMode) ? FilterMode.MUST : filterMode;
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

    protected <R> ColumnInfo getColumnInfo(GetPropertyFunction<T, R> getPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
        return EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
    }
}
