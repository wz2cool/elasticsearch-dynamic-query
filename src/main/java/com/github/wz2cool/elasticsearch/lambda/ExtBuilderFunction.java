package com.github.wz2cool.elasticsearch.lambda;

import com.github.wz2cool.elasticsearch.query.builder.ExtQueryBuilder;
import com.github.wz2cool.elasticsearch.query.builder.ExtQueryBuilders;

import java.util.function.Function;

public interface ExtBuilderFunction<T> extends Function<ExtQueryBuilders<T>, ExtQueryBuilder> {
}
