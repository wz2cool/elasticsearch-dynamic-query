package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;

/**
 * A Query that matches documents containing a term.
 *
 * @author Frank
 */
public class TermExtQueryBuilder<T, P extends Comparable> implements ExtQueryBuilder {

    private final TermQueryBuilder termQueryBuilder;

    /**
     * @see TermQueryBuilder#TermQueryBuilder(String, Object)
     */
    public TermExtQueryBuilder(GetPropertyFunction<T, P> getPropertyFunc, P value) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.termQueryBuilder = new TermQueryBuilder(columnInfo.getColumnName(), getFilterValue(value));
    }

    /**
     * @see TermQueryBuilder#TermQueryBuilder(String, Object)
     */
    public TermExtQueryBuilder(GetArrayPropertyFunction<T, P> getPropertyFunc, P value) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.termQueryBuilder = new TermQueryBuilder(columnInfo.getColumnName(), getFilterValue(value));
    }

    @Override
    public QueryBuilder build() {
        return this.termQueryBuilder;
    }
}
