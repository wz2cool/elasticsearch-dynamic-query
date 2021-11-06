package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.WildcardQueryBuilder;

/**
 * @see WildcardQueryBuilder
 */
public class WildcardExtQueryBuilder<T> implements ExtQueryBuilder {

    private final WildcardQueryBuilder wildcardQueryBuilder;

    /**
     * @see WildcardQueryBuilder#WildcardQueryBuilder(String, String)
     */
    public WildcardExtQueryBuilder(GetStringPropertyFunction<T> getPropertyFunc, String query) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.wildcardQueryBuilder = new WildcardQueryBuilder(columnInfo.getColumnName(), query);
    }

    @Override
    public QueryBuilder build() {
        return this.wildcardQueryBuilder;
    }
}
