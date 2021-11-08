package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> extends AndOrFilterGroup<T, S> {

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

    /// endregion

    public QueryBuilder buildQuery() {
        return this.booleanQueryBuilder;
    }
}
