package com.github.wz2cool.elasticsearch.query.builder;

import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class MatchAllExtQueryBuilder implements ExtQueryBuilder {

    @Override
    public QueryBuilder build() {
        return new MatchAllQueryBuilder();
    }
}
