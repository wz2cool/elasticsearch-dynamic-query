package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;

public class MultiMatchOperators {

    @SafeVarargs
    public final <T> MultiMatchOperator<T> multiMatch(GetStringPropertyFunction<T>... getPropertyFuncs) {
        return new MultiMatchOperator<>(getPropertyFuncs);
    }
}
