package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.MatchPhrasePrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

public class MatchPhrasePrefixExtQueryBuilder<T> implements ExtQueryBuilder {

    private final MatchPhrasePrefixQueryBuilder matchPhrasePrefixQueryBuilder;

    public MatchPhrasePrefixExtQueryBuilder(GetStringPropertyFunction<T> getPropertyFunc, String text) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.matchPhrasePrefixQueryBuilder = new MatchPhrasePrefixQueryBuilder(columnInfo.getColumnName(), text);
    }

    @Override
    public QueryBuilder build() {
        return this.matchPhrasePrefixQueryBuilder;
    }
}
