package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.PrefixQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * A Query that matches documents containing terms with a specified prefix.
 *
 * @author Frank
 */
public class PrefixExtQueryBuilder<T> implements ExtQueryBuilder {

    private final PrefixQueryBuilder prefixQueryBuilder;

    /**
     * @see PrefixQueryBuilder#PrefixQueryBuilder(String, String)
     */
    public PrefixExtQueryBuilder(GetStringPropertyFunction<T> getPropertyFunc, String prefix) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.prefixQueryBuilder = new PrefixQueryBuilder(columnInfo.getColumnName(), prefix);
    }

    @Override
    public QueryBuilder build() {
        return this.prefixQueryBuilder;
    }
}
