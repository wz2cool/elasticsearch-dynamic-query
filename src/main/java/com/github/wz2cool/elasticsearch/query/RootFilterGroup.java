package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import com.github.wz2cool.elasticsearch.operator.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Function;

abstract class RootFilterGroup<T, S extends RootFilterGroup<T, S>> {

    protected static final SingleFilterOperators SINGLE_FILTER_OPERATORS = new SingleFilterOperators();
    protected static final ArrayFilterOperators ARRAY_FILTER_OPERATORS = new ArrayFilterOperators();
    protected static final MultiMatchOperators MULTI_MATCH_OPERATORS = new MultiMatchOperators();

    protected final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();

    /// region internal



    /// region help

    protected  <R extends Comparable> SingleFilterOperators<R> getSingleFilterOperators() {
        return (SingleFilterOperators<R>) SINGLE_FILTER_OPERATORS;
    }

    protected <R extends Comparable> ArrayFilterOperators<R> getArrayFilterOperators() {
        return (ArrayFilterOperators<R>) ARRAY_FILTER_OPERATORS;
    }

    protected <R> MultiMatchOperators<R> getMultiMatchOperators() {
        return (MultiMatchOperators<R>) MULTI_MATCH_OPERATORS;
    }

    protected <R> String getColumnName(GetPropertyFunction<T, R> getPropertyFunc) {
        return getColumnInfo(getPropertyFunc).getColumnName();
    }

    protected <P1, R extends Comparable> String getColumnName(
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getP1Func);
        final ColumnInfo columnInfo2 = getColumnInfo(getPropertyFunc);
        return columnInfo.getColumnName() + "." + columnInfo2.getColumnName();
    }

    protected <P1, R extends Comparable> String getColumnName(
            GetPropertyFunction<T, P1> getP1Func,
            GetArrayPropertyFunction<P1, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getP1Func);
        final ColumnInfo columnInfo2 = getColumnInfo(getPropertyFunc);
        return columnInfo.getColumnName() + "." + columnInfo2.getColumnName();
    }

    protected <T1, R> ColumnInfo getColumnInfo(GetPropertyFunction<T1, R> getPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
        return EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
    }

    /// endregion

}