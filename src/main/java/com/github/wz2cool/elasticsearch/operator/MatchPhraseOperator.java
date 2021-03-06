package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.MatchPhraseQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Objects;

public class MatchPhraseOperator implements IFilterOperator<String> {

    private String value;
    private String analyzer;
    private Integer slop;

    MatchPhraseOperator(String value) {
        this.value = value;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {

        final MatchPhraseQueryBuilder matchPhraseQueryBuilder = new MatchPhraseQueryBuilder(columnName, value);
        if (Objects.nonNull(analyzer)) {
            matchPhraseQueryBuilder.analyzer(analyzer);
        }
        if (Objects.nonNull(slop)) {
            matchPhraseQueryBuilder.slop(slop);
        }
        return matchPhraseQueryBuilder;
    }

    /**
     * see also {@link MatchPhraseQueryBuilder#analyzer(String)}
     */
    public MatchPhraseOperator analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    /**
     * see also {@link MatchPhraseQueryBuilder#slop(int)}
     */
    public MatchPhraseOperator slop(Integer slop) {
        this.slop = slop;
        return this;
    }
}
