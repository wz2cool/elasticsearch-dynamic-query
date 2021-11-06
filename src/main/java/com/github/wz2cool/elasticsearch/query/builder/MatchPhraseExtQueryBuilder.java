package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Match query is a query that analyzes the text and constructs a phrase query
 * as the result of the analysis.
 *
 * @author Frank
 */
public class MatchPhraseExtQueryBuilder<T> implements ExtQueryBuilder {

    private final MatchPhraseQueryBuilder matchPhraseQueryBuilder;

    /**
     * see also {@link MatchPhraseQueryBuilder#MatchPhraseQueryBuilder(String, Object)}
     */
    public MatchPhraseExtQueryBuilder(GetStringPropertyFunction<T> getPropertyFunc, String text) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.matchPhraseQueryBuilder = new MatchPhraseQueryBuilder(columnInfo.getColumnName(), text);
    }

    /**
     * see also {@link MatchPhraseQueryBuilder#analyzer(String)}
     */
    public MatchPhraseExtQueryBuilder<T> analyzer(String analyzer) {
        this.matchPhraseQueryBuilder.analyzer(analyzer);
        return this;
    }

    /**
     * see also {@link MatchPhraseQueryBuilder#slop(int)}
     */
    public MatchPhraseExtQueryBuilder<T> slop(int slop) {
        this.matchPhraseQueryBuilder.slop(slop);
        return this;
    }

    @Override
    public QueryBuilder build() {
        return this.matchPhraseQueryBuilder;
    }
}
