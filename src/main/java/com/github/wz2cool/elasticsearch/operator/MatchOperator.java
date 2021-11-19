package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Objects;

public class MatchOperator implements IFilterOperator<String> {

    private String value;
    private String analyzer;
    private String fuzziness;
    private Float boost;
    private Operator operator;

    MatchOperator(String value) {
        this.value = value;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public QueryBuilder buildQuery(String columnName) {
        final MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(columnName, value);
        if (Objects.nonNull(analyzer)) {
            matchQueryBuilder.analyzer(analyzer);
        }
        if (Objects.nonNull(fuzziness)) {
            matchQueryBuilder.fuzziness(fuzziness);
        }
        if (Objects.nonNull(operator)) {
            matchQueryBuilder.operator(operator);
        }
        if (Objects.nonNull(boost)) {
            matchQueryBuilder.boost(boost);
        }
        return matchQueryBuilder;
    }

    /**
     * see also {@link MatchQueryBuilder#analyzer(String)}
     */
    public MatchOperator analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    /**
     * see also {@link MatchQueryBuilder#fuzziness(Object)} )}
     */
    public MatchOperator fuzziness(String fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    public MatchOperator operator(Operator operator) {
        this.operator = operator;
        return this;
    }

    public MatchOperator boost(Float boost) {
        this.boost = boost;
        return this;
    }
}
