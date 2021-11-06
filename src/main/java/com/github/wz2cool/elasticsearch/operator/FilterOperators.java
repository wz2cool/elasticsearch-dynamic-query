package com.github.wz2cool.elasticsearch.operator;

import java.util.Arrays;
import java.util.Collection;

public final class FilterOperators {

    public <T> TermOperator<T> term(T value) {
        return new TermOperator<>(value);
    }

    public <T> TermsOperator<T> terms(Collection<T> values) {
        return new TermsOperator<>(values);
    }

    @SafeVarargs
    public final <T> TermsOperator<T> terms(T... values) {
        return new TermsOperator<>(Arrays.asList(values));
    }

    public FuzzyOperator fuzzy(String value) {
        return new FuzzyOperator(value);
    }
}
