package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.*;
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
class OrOneNestedFilterGroup<T, S extends OrOneNestedFilterGroup<T, S>> extends AndOneNestedFilterGroup<T, S> {

    /// region or

    /// region single

    /// region string

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetStringPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetStringPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<String>, IFilterOperator<String>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, STRING_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetIntegerPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetIntegerPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Integer>, IFilterOperator<Integer>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, INTEGER_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetBigDecimalPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetBigDecimalPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<BigDecimal>, IFilterOperator<BigDecimal>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, BIG_DECIMAL_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Boolean

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetBooleanPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetBooleanPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Boolean>, IFilterOperator<Boolean>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, BOOLEAN_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region Byte

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetBytePropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetBytePropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Byte>, IFilterOperator<Byte>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, BYTE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetDatePropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetDatePropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Date>, IFilterOperator<Date>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, DATE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetDoublePropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetDoublePropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Double>, IFilterOperator<Double>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, DOUBLE_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetFloatPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetFloatPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Float>, IFilterOperator<Float>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, FLOAT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetLongPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetLongPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Long>, IFilterOperator<Long>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, LONG_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetShortPropertyFunction<P1> getPropertyFunc,
            Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetShortPropertyFunction<P1> getPropertyFunc,
                     Function<SingleFilterOperators<Short>, IFilterOperator<Short>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, SHORT_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// region array

    /// region string

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetStringArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetStringArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<String>, IArrayFilterOperator<String>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, STRING_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region integer

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetIntegerArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetIntegerArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Integer>, IArrayFilterOperator<Integer>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, INTEGER_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region boolean

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetBooleanArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetBooleanArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Boolean>, IArrayFilterOperator<Boolean>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, BOOLEAN_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region byte

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetByteArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetByteArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Byte>, IArrayFilterOperator<Byte>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, BYTE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region BigDecimal

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetBigDecimalArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetBigDecimalArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<BigDecimal>, IArrayFilterOperator<BigDecimal>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, BIG_DECIMAL_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region date

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetDateArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetDateArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Date>, IArrayFilterOperator<Date>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, DATE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region double

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetDoubleArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetDoubleArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Double>, IArrayFilterOperator<Double>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, DOUBLE_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region float

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetFloatArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetFloatArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Float>, IArrayFilterOperator<Float>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, FLOAT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region long

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetLongArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetLongArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Long>, IArrayFilterOperator<Long>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, LONG_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// region short

    public <P1> S or(
            GetPropertyFunction<T, P1> getP1Func,
            GetShortArrayPropertyFunction<P1> getPropertyFunc,
            Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return or(true, getP1Func, getPropertyFunc, operatorFunc);
    }

    public <P1> S or(boolean enable,
                     GetPropertyFunction<T, P1> getP1Func,
                     GetShortArrayPropertyFunction<P1> getPropertyFunc,
                     Function<ArrayFilterOperators<Short>, IArrayFilterOperator<Short>> operatorFunc) {
        return orInternal(enable, getP1Func, getPropertyFunc, SHORT_ARRAY_FILTER_OPERATORS, operatorFunc);
    }

    /// endregion

    /// endregion

    /// endregion
}
