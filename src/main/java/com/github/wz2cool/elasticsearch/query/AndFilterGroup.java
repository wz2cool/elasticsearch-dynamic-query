package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.*;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.*;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Frank
 **/
abstract class AndFilterGroup<T, S extends AndFilterGroup<T, S>> extends RootFilterGroup<T, S> {

    /// region and

    /// region single

    /// region string

    public S and(GetStringPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andInternal(true, null, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    public S and(boolean enable, GetStringPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(GetStringPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable, GetStringPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
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
        final MultiMatchOperator<T> operator = operatorFunc.apply(new MultiMatchOperators<>());
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        return andInternal(operator.getDefaultFilterMode(), queryBuilder);
    }

    public S andNot(String value, Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        return andNot(true, value, operatorFunc);
    }

    public S andNot(boolean enable,
                    String value,
                    Function<MultiMatchOperators<T>, MultiMatchOperator<T>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final MultiMatchOperator<T> operator = operatorFunc.apply(new MultiMatchOperators<>());
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        return andInternal(FilterMode.MUST_NOT, queryBuilder);
    }

    /// endregion

    /// region integer

    public S and(GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetIntegerPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(GetIntegerPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetIntegerPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S and(GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(GetBigDecimalPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetBigDecimalPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Boolean

    public S and(GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBooleanPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(GetBooleanPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetBooleanPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Byte

    public S and(GetBytePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBytePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetBytePropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(
            boolean enable,
            GetBytePropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S and(GetDatePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDatePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetDatePropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetDatePropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S and(GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDoublePropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(GetDoublePropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetDoublePropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S and(GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetFloatPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetFloatPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetFloatPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S and(GetLongPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetLongPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetLongPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetLongPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S and(GetShortPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetShortPropertyFunction<T> getPropertyFunc,
                 Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetShortPropertyFunction<T> getPropertyFunc,
            Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetShortPropertyFunction<T> getPropertyFunc,
                    Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// region array

    /// region string

    public S and(GetStringArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable, GetStringArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, STRING_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetStringArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetStringArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, STRING_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public S and(GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, INTEGER_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetIntegerArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetIntegerArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, INTEGER_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region boolean

    public S and(GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, BOOLEAN_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetBooleanArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetBooleanArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, BOOLEAN_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region byte

    public S and(GetByteArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetByteArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, BYTE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetByteArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetByteArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, BYTE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public S and(GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, BIG_DECIMAL_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetBigDecimalArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, BIG_DECIMAL_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public S and(GetDateArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDateArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, DATE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetDateArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetDateArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, DATE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public S and(GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, DOUBLE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetDoubleArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetDoubleArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, DOUBLE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public S and(GetFloatArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetFloatArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, FLOAT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetFloatArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetFloatArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, FLOAT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public S and(GetLongArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetLongArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, LONG_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetLongArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetLongArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, LONG_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public S and(GetShortArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(true, getPropertyFunc, operatorFunc);
    }

    public S and(boolean enable,
                 GetShortArrayPropertyFunction<T> getPropertyFunc,
                 Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, null, getPropertyFunc, SHORT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    public S andNot(
            GetShortArrayPropertyFunction<T> getPropertyFunc,
            Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return andNot(true, getPropertyFunc, operatorFunc);
    }

    public S andNot(boolean enable,
                    GetShortArrayPropertyFunction<T> getPropertyFunc,
                    Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, FilterMode.MUST_NOT, getPropertyFunc, SHORT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// endregion
}
