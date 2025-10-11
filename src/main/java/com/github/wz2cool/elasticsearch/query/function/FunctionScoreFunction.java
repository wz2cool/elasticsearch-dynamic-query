package com.github.wz2cool.elasticsearch.query.function;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilder;

public class FunctionScoreFunction {
    private QueryBuilder filter;
    private final ScoreFunctionBuilder<?> functionBuilder;

    public FunctionScoreFunction(ScoreFunctionBuilder<?> functionBuilder) {
        this.functionBuilder = functionBuilder;
    }

    public FunctionScoreFunction(QueryBuilder filter, ScoreFunctionBuilder<?> functionBuilder) {
        this.filter = filter;
        this.functionBuilder = functionBuilder;
    }

    public QueryBuilder getFilter() {
        return filter;
    }

    public ScoreFunctionBuilder<?> getFunctionBuilder() {
        return functionBuilder;
    }
}
