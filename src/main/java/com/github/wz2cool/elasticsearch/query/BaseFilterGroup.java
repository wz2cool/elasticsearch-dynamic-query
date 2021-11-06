package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.*;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.FilterOperators;
import com.github.wz2cool.elasticsearch.operator.IFilterOperator;
import com.github.wz2cool.elasticsearch.operator.MultiMatchOperator;
import com.github.wz2cool.elasticsearch.operator.MultiMatchOperators;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> {

    private final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();
    private static final MultiMatchOperators MULTI_MATCH_OPERATORS = new MultiMatchOperators();
    private static final FilterOperators<String> STRING_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Integer> INTEGER_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<BigDecimal> BIG_DECIMAL_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Boolean> BOOLEAN_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Byte> BYTE_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Date> DATE_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Double> DOUBLE_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Float> FLOAT_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Long> LONG_FILTER_OPERATORS = new FilterOperators<>();
    private static final FilterOperators<Short> SHORT_FILTER_OPERATORS = new FilterOperators<>();

    /// region string

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
        return andInternal(enable, filterMode, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    public S and(String value, Function<MultiMatchOperators, MultiMatchOperator<T>> operatorFunc) {
        return and(true, FilterMode.MUST, value, operatorFunc);
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

    /// endregion

    /// region integer

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
        return andInternal(enable, filterMode, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S and(GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Boolean

    public S and(GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Byte

    public S and(GetBytePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S and(GetDatePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S and(GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S and(GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S and(GetLongPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S and(GetShortPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(enable, FilterMode.MUST, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region group

    public S and(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, FilterMode.MUST, groupConsumer);
    }

    public S and(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(enable, FilterMode.MUST, groupConsumer);
    }

    public S and(FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, filterMode, groupConsumer);
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

    /// endregion

    private <R> S andInternal(boolean enable,
                              FilterMode filterMode,
                              GetPropertyFunction<T, R> getPropertyFunc,
                              FilterOperators<R> filterOperators,
                              Function<FilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(filterOperators);
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
