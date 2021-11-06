package com.github.wz2cool.elasticsearch.operator;

import java.util.Arrays;
import java.util.Collection;

public final class SingleFilterOperators<R extends Comparable> {

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

    public FuzzyOperator fuzzy(String value) {
        return new FuzzyOperator(value);
    }

    public MatchOperator match(String value) {
        return new MatchOperator(value);
    }

    public MatchPhraseOperator matchPhrase(String value) {
        return new MatchPhraseOperator(value);
    }

    public PrefixOperator prefix(String value) {
        return new PrefixOperator(value);
    }

    public RangeOperator<R> range() {
        return new RangeOperator<>();
    }

    public WildcardOperator wildcard(String query) {
        return new WildcardOperator(query);
    }

    public RegexpOperator regexp(String regexp) {
        return new RegexpOperator(regexp);
    }
}
