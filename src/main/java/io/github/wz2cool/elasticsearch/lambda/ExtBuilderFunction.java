package io.github.wz2cool.elasticsearch.lambda;

import io.github.wz2cool.elasticsearch.query.builder.ExtQueryBuilder;
import io.github.wz2cool.elasticsearch.query.builder.ExtQueryBuilders;

import java.util.function.Function;

public interface ExtBuilderFunction<T> extends Function<ExtQueryBuilders<T>, ExtQueryBuilder> {
}
