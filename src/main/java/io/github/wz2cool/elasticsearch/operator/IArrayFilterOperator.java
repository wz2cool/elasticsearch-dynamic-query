package io.github.wz2cool.elasticsearch.operator;

import io.github.wz2cool.elasticsearch.lambda.GetArrayPropertyFunction;
import org.elasticsearch.index.query.QueryBuilder;

public interface IArrayFilterOperator<R extends Comparable> extends IFilterOperator<R> {

    <T> QueryBuilder buildQuery(GetArrayPropertyFunction<T, R> getPropertyFunc);
}
