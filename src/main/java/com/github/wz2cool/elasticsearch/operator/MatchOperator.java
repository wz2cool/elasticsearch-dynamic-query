package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.Objects;

public class MatchOperator implements IFilterOperator<String> {

    private String value;
    private String analyzer;
    private String fuzziness;

    MatchOperator(String value) {
        this.value = value;
    }

    @Override
    public FilterMode getDefaultFilterMode() {
        return FilterMode.MUST;
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, String> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        final MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder(columnInfo.getColumnName(), value);
        if (Objects.nonNull(analyzer)) {
            matchQueryBuilder.analyzer(analyzer);
        }
        if (Objects.nonNull(fuzziness)) {
            matchQueryBuilder.fuzziness(fuzziness);
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
}
