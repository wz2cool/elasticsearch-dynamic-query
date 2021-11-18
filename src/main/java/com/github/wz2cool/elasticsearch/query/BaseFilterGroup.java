package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> extends AndOrFilterGroup<T, S> {

    /// region group

    public S and(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return and(true, groupConsumer);
    }

    public S and(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (!enable) {
            return (S) this;
        }
        FilterGroup<T> filterGroup = new FilterGroup<>();
        final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).getFilterQuery();
        return andInternal(FilterMode.MUST, queryBuilder);
    }

    public S andNot(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return andNot(true, groupConsumer);
    }

    public S andNot(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (!enable) {
            return (S) this;
        }
        FilterGroup<T> filterGroup = new FilterGroup<>();
        final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).getFilterQuery();
        return andInternal(FilterMode.MUST_NOT, queryBuilder);
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
        return and(true, filterGroup);
    }

    public S and(boolean enable, FilterGroup<T> filterGroup) {
        if (!enable) {
            return (S) this;
        }
        final QueryBuilder queryBuilder = filterGroup.getFilterQuery();
        return andInternal(FilterMode.MUST, queryBuilder);
    }

    public S andNot(FilterGroup<T> filterGroup) {
        return andNot(true, filterGroup);
    }

    public S andNot(boolean enable, FilterGroup<T> filterGroup) {
        if (!enable) {
            return (S) this;
        }
        final QueryBuilder queryBuilder = filterGroup.getFilterQuery();
        return andInternal(FilterMode.MUST_NOT, queryBuilder);
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

    public QueryBuilder getFilterQuery() {
        return this.booleanQueryBuilder;
    }
}
