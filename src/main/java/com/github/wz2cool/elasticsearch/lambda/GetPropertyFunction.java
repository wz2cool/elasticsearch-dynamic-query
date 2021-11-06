package com.github.wz2cool.elasticsearch.lambda;

import java.io.Serializable;
import java.util.function.Function;

/**
 * @author Frank
 */
@FunctionalInterface
public interface GetPropertyFunction<T, R> extends Function<T, R>, Serializable {
}