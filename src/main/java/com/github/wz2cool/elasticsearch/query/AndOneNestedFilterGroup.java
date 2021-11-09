package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.*;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.ArrayFilterOperators;
import com.github.wz2cool.elasticsearch.operator.IArrayFilterOperator;
import com.github.wz2cool.elasticsearch.operator.IFilterOperator;
import com.github.wz2cool.elasticsearch.operator.SingleFilterOperators;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Frank
 **/
abstract class AndOneNestedFilterGroup<T, S extends AndOneNestedFilterGroup<T, S>> extends AndFilterGroup<T, S> {

    /// region and

    /// region single

    /// region string

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetStringPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetStringPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetStringPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetStringPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetIntegerPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetIntegerPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetIntegerPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetIntegerPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetBigDecimalPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBigDecimalPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBigDecimalPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBigDecimalPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Boolean

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetBooleanPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBooleanPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBooleanPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBooleanPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Byte

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetBytePropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBytePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBytePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBytePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetDatePropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDatePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDatePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDatePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetDoublePropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDoublePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDoublePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDoublePropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetFloatPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetFloatPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetFloatPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetFloatPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetLongPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetLongPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetLongPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetLongPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetShortPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetShortPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetShortPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetShortPropertyFunction<P1> getPropertyFunc,
                      Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// region array

    /// region string

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetStringArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetStringArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetStringArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetStringArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, STRING_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetIntegerArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetIntegerArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetIntegerArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetIntegerArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, INTEGER_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region boolean

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetBooleanArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBooleanArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBooleanArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBooleanArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, BOOLEAN_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region byte

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetByteArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetByteArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetByteArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetByteArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, BYTE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetBigDecimalArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBigDecimalArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBigDecimalArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetBigDecimalArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, BIG_DECIMAL_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetDateArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDateArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDateArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDateArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, DATE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetDoubleArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDoubleArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDoubleArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetDoubleArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, DOUBLE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public <P1> S and(GetFloatArrayPropertyFunction<P1> getPropertyFunc,
                      GetPropertyFunction<T, P1> getP1Func,
                      Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetFloatArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetFloatArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetFloatArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, FLOAT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetLongArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetLongArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetLongArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetLongArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, LONG_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public <P1> S and(
            GetPropertyFunction<T, P1> getP1Func,
            GetShortArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(true, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetShortArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(enable, null, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetShortArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return and(true, filterMode, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S and(boolean enable,
                      FilterMode filterMode,
                      GetPropertyFunction<T, P1> getP1Func,
                      GetShortArrayPropertyFunction<P1> getPropertyFunc,
                      Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return andInternal(enable, filterMode, getP1Func, getPropertyFunc, SHORT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// endregion
}
