package com.github.wz2cool.elasticsearch.operator;

import java.util.Arrays;
import java.util.Collection;

public class ArrayFilterOperators<R extends Comparable> {

    public TermOperator<R> term(R value) {
        return new TermOperator<>(value);
    }

    public TermsOperator<R> terms(Collection<R> values) {
        return new TermsOperator<>(values);
    }

    @SafeVarargs
    public final TermsOperator<R> terms(R... values) {
        return new TermsOperator<>(Arrays.asList(values));
    }
}
