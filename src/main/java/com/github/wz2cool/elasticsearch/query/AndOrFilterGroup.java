package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.*;
import com.github.wz2cool.elasticsearch.operator.*;
import org.elasticsearch.index.query.QueryBuilder;

import java.math.BigDecimal;
import java.util.Date;
import java.util.function.Function;

/**
 * @author Frank
 **/
class AndOrFilterGroup<T, S extends AndOrFilterGroup<T, S>> extends AndOneNestedFilterGroup<T, S> {

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

    /// endregion
}
