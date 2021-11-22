package com.github.wz2cool.elasticsearch.query;

import org.elasticsearch.index.query.QueryBuilder;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> extends AndOrFilterGroup<T, S> {

    public QueryBuilder getFilterQuery() {
        return this.booleanQueryBuilder;
    }
}
