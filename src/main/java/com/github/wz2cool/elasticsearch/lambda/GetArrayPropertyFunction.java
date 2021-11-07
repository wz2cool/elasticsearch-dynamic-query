package com.github.wz2cool.elasticsearch.lambda;

@FunctionalInterface
public interface GetArrayPropertyFunction<T, R extends Comparable> extends GetPropertyFunction<T, R[]> {
}
