package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.*;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> {

    private final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();
    private static final SingleFilterOperators<String> STRING_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Integer> INTEGER_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<BigDecimal> BIG_DECIMAL_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Boolean> BOOLEAN_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Byte> BYTE_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Date> DATE_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Double> DOUBLE_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Float> FLOAT_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Long> LONG_FILTER_OPERATORS = new SingleFilterOperators<>();
    private static final SingleFilterOperators<Short> SHORT_FILTER_OPERATORS = new SingleFilterOperators<>();

    private static final ArrayFilterOperators<String> STRING_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Integer> INTEGER_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<BigDecimal> BIG_DECIMAL_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Boolean> BOOLEAN_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Byte> BYTE_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Date> DATE_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Double> DOUBLE_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Float> FLOAT_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Long> LONG_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();
    private static final ArrayFilterOperators<Short> SHORT_ARRAY_FILTER_OPERATORS = new ArrayFilterOperators<>();

    /// region and

    /// region single

    /// region string

    public S and(GetStringPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable, GetStringPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetStringPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetStringPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    public S and(String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return and(true, null, value, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 String value,
                 Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final MultiMatchOperator<T> operator = operatorFunc.apply(new MultiMatchOperators<>());
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        FilterMode useFilterMode = Objects.isNull(filterMode) ? operator.getDefaultFilterMode() : filterMode;
        return andInternal(useFilterMode, queryBuilder);
    }

    /// endregion

    /// region integer

    public S and(GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S and(GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Boolean

    public S and(GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Byte

    public S and(GetBytePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S and(GetDatePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S and(GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S and(GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S and(GetLongPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S and(GetShortPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// region array

    /// region string

    public S and(GetStringArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable, GetStringArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetStringArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetStringArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, STRING_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public S and(GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, INTEGER_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region boolean

    public S and(GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BOOLEAN_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region byte

    public S and(GetByteArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetByteArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetByteArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetByteArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BYTE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S and(GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, BIG_DECIMAL_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S and(GetDateArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDateArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetDateArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetDateArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, DATE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S and(GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, DOUBLE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S and(GetFloatArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetFloatArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetFloatArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetFloatArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, FLOAT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S and(GetLongArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetLongArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetLongArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetLongArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, LONG_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S and(GetShortArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetShortArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public S and(FilterMode filterMode,
                 GetShortArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 FilterMode filterMode,
                 GetShortArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, filterMode, getPropertyFunc, SHORT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// endregion

    /// region or

    /// region single

    /// region string

    public S or(GetStringPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetStringPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    public S or(String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return or(true, value, operatorFunc);
    }

    public S or(boolean enable,
                String value,
                Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final MultiMatchOperator<T> operator = operatorFunc.apply(new MultiMatchOperators<>());
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        this.booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    /// endregion

    /// region integer

    public S or(GetIntegerPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetIntegerPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S or(
            GetBigDecimalPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetBigDecimalPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Boolean

    public S or(
            GetBooleanPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetBooleanPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Byte

    public S or(
            GetBytePropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetBytePropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S or(
            GetDatePropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetDatePropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S or(
            GetDoublePropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetDoublePropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S or(
            GetFloatPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetFloatPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S or(
            GetLongPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetLongPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S or(
            GetShortPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetShortPropertyFunction<T> getPropertyFunc,
                Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// region array

    /// region string

    public S or(
            GetStringArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetStringArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, STRING_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public S or(
            GetIntegerArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, INTEGER_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region boolean

    public S or(
            GetBooleanArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, BOOLEAN_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region byte

    public S or(
            GetByteArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetByteArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, BYTE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S or(
            GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, BIG_DECIMAL_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S or(
            GetDateArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetDateArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, DATE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S or(
            GetDoubleArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, DOUBLE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S or(
            GetFloatArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetFloatArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, FLOAT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S or(
            GetLongArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetLongArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, LONG_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S or(
            GetShortArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public S or(boolean enable,
                GetShortArrayPropertyFunction<T> getPropertyFunc,
                Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return orInternal(enable, getPropertyFunc, SHORT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// endregion


    /// region group

    public S and(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, null, groupConsumer);
    }

    public S and(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(enable, null, groupConsumer);
    }

    public S and(FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, filterMode, groupConsumer);
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

    /// endregion

    /// region custom

    public S and(QueryBuilder queryBuilder) {
        return and(true, null, queryBuilder);
    }

    public S and(boolean enable, QueryBuilder queryBuilder) {
        return and(enable, null, queryBuilder);
    }

    public S and(FilterMode filterMode, QueryBuilder queryBuilder) {
        return and(true, filterMode, queryBuilder);
    }

    public S and(boolean enable, FilterMode filterMode, QueryBuilder queryBuilder) {
        if (!enable) {
            return (S) this;
        }
        return andInternal(filterMode, queryBuilder);
    }

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

    /// region

    /// region internal

    private <R extends Comparable> S andInternal(
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

    private <R extends Comparable> S andInternal(
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

    private <R extends Comparable> S orInternal(
            boolean enable,
            GetPropertyFunction<T, R> getPropertyFunc,
            SingleFilterOperators<R> singleFilterOperators,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(singleFilterOperators);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(getPropertyFunc);
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    private <R extends Comparable> S orInternal(
            boolean enable,
            GetArrayPropertyFunction<T, R> getPropertyFunc,
            ArrayFilterOperators<R> arrayFilterOperators,
            Function<ArrayFilterOperators<R>, IArrayFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IArrayFilterOperator<R> apply = operatorFunc.apply(arrayFilterOperators);
        final QueryBuilder queryBuilder = apply.buildQuery(getPropertyFunc);
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

    /// endregion

    public QueryBuilder buildQuery() {
        return this.booleanQueryBuilder;
    }
}
