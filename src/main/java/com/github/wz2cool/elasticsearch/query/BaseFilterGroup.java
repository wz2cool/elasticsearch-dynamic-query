package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import com.github.wz2cool.elasticsearch.operator.IFilterOperator;
import com.github.wz2cool.elasticsearch.operator.MultiMatchOperator;
import com.github.wz2cool.elasticsearch.operator.MultiMatchOperators;
import com.github.wz2cool.elasticsearch.operator.SingleFilterOperators;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> {

    protected final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();
    private static final SingleFilterOperators SINGLE_FILTER_OPERATORS = new SingleFilterOperators();
    private static final MultiMatchOperators MULTI_MATCH_OPERATORS = new MultiMatchOperators();


    /// region and

    public <R extends Comparable> S and(
            GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(true, null, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S and(
            boolean enable, GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(enable, null, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S and(
            FilterMode filterMode, GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return and(true, filterMode, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S and(
            boolean enable, FilterMode filterMode, GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(SINGLE_FILTER_OPERATORS);
        String columnName = getColumnName(getPropertyFunc);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(columnName);
        final FilterMode userFilterMode = Objects.isNull(filterMode) ? filterOperator.getDefaultFilterMode() : filterMode;
        return andInternal(userFilterMode, queryBuilder);
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

    /// region or

    public <R extends Comparable> S or(
            GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        return or(true, getPropertyFunc, operatorFunc);
    }

    public <R extends Comparable> S or(
            boolean enable,
            GetPropertyFunction<T, R> getPropertyFunc,
            Function<SingleFilterOperators<R>, IFilterOperator<R>> operatorFunc) {
        if (!enable) {
            return (S) this;
        }
        final IFilterOperator<R> filterOperator = operatorFunc.apply(SINGLE_FILTER_OPERATORS);
        final QueryBuilder queryBuilder = filterOperator.buildQuery(getColumnName(getPropertyFunc));
        booleanQueryBuilder.should(queryBuilder);
        return (S) this;
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
        final MultiMatchOperator<T> operator = operatorFunc.apply(MULTI_MATCH_OPERATORS);
        final QueryBuilder queryBuilder = operator.buildQuery(value);
        this.booleanQueryBuilder.should(queryBuilder);
        return (S) this;
    }

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
        final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).getFilterQuery();
        final FilterMode userFilterMode = Objects.isNull(filterMode) ? FilterMode.MUST : filterMode;
        return andInternal(userFilterMode, queryBuilder);
    }

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

    public S and(FilterGroup<T> filterGroup) {
        return and(true, null, filterGroup);
    }

    public S and(boolean enable, FilterGroup<T> filterGroup) {
        return and(enable, null, filterGroup);
    }

    public S and(FilterMode filterMode, FilterGroup<T> filterGroup) {
        return and(true, filterMode, filterGroup);
    }

    public S and(boolean enable, FilterMode filterMode, FilterGroup<T> filterGroup) {
        if (!enable) {
            return (S) this;
        }
        final QueryBuilder queryBuilder = filterGroup.getFilterQuery();
        final FilterMode userFilterMode = Objects.isNull(filterMode) ? FilterMode.MUST : filterMode;
        return andInternal(userFilterMode, queryBuilder);
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

    /// endregion

    /// region custom

    public S and(QueryBuilder queryBuilder) {
        return and(true, queryBuilder);
    }

    public S and(boolean enable, QueryBuilder queryBuilder) {
        if (!enable) {
            return (S) this;
        }
        return andInternal(FilterMode.MUST, queryBuilder);
    }

    public S andNot(QueryBuilder queryBuilder) {
        return andNot(true, queryBuilder);
    }

    public S andNot(boolean enable, QueryBuilder queryBuilder) {
        if (!enable) {
            return (S) this;
        }
        return andInternal(FilterMode.MUST_NOT, queryBuilder);
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

    /// endregion

    /// region help

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

    protected <P1, R extends Comparable, P2> String getColumnName(
            GetPropertyFunction<T, P1> getP1Func,
            GetPropertyFunction<P1, P2> getP2Func,
            GetPropertyFunction<P2, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getP1Func);
        final ColumnInfo columnInfo1 = getColumnInfo(getP2Func);
        final ColumnInfo columnInfo2 = getColumnInfo(getPropertyFunc);
        return columnInfo.getColumnName() + "." + columnInfo1.getColumnName() + "." + columnInfo2.getColumnName();
    }

    protected <T1, R> ColumnInfo getColumnInfo(GetPropertyFunction<T1, R> getPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
        return EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
    }

    /// endregion

    public QueryBuilder getFilterQuery() {
        return this.booleanQueryBuilder;
    }
}
