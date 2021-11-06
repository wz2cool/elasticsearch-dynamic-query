package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RegexpQueryBuilder;

/**
 * @see RegexpQueryBuilder
 */
public class RegexpExtQueryBuilder<T> implements ExtQueryBuilder {

    private final RegexpQueryBuilder regexpQueryBuilder;

    /**
     * @see RegexpQueryBuilder#RegexpQueryBuilder(String, String)
     */
    public RegexpExtQueryBuilder(GetStringPropertyFunction<T> getPropertyFunc, String regexp) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.regexpQueryBuilder = new RegexpQueryBuilder(columnInfo.getColumnName(), regexp);
    }

    @Override
    public QueryBuilder build() {
        return this.regexpQueryBuilder;
    }
}
