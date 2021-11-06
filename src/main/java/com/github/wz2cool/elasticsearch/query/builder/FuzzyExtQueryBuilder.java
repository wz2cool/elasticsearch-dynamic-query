package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.FuzzyQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * A Query that does fuzzy matching for a specific value.
 *
 * @author Frank
 */
public class FuzzyExtQueryBuilder<T> implements ExtQueryBuilder {

    private FuzzyQueryBuilder fuzzyQueryBuilder;

    /**
     * @see FuzzyQueryBuilder#FuzzyQueryBuilder(String, Object)
     */
    public FuzzyExtQueryBuilder(GetStringPropertyFunction<T> getPropertyFunc, String value) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.fuzzyQueryBuilder = new FuzzyQueryBuilder(columnInfo.getColumnName(), value);
    }

    @Override
    public QueryBuilder build() {
        return this.fuzzyQueryBuilder;
    }
}
