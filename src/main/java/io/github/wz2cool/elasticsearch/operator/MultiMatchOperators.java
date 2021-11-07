package io.github.wz2cool.elasticsearch.operator;

import io.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;

public class MultiMatchOperators<T> {

    @SafeVarargs
    public final MultiMatchOperator<T> multiMatch(GetStringPropertyFunction<T>... getPropertyFuncs) {
        return new MultiMatchOperator<>(getPropertyFuncs);
    }
}
