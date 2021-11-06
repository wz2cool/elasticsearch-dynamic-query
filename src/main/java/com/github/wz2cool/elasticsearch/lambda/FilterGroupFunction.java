package com.github.wz2cool.elasticsearch.lambda;

import com.github.wz2cool.elasticsearch.query.FilterGroup;

import java.util.function.UnaryOperator;

public interface FilterGroupFunction<T> extends UnaryOperator<FilterGroup<T>> {
}
