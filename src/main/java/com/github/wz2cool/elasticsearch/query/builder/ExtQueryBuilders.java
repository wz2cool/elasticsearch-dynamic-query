package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.*;

import java.math.BigDecimal;
import java.util.Date;

public class ExtQueryBuilders<T> {

    public MatchExtQueryBuilder<T> match(GetStringPropertyFunction<T> getPropertyFunc, String text) {
        return new MatchExtQueryBuilder<>(getPropertyFunc, text);
    }

    @SafeVarargs
    public final MultiMatchExtQueryBuilder<T> multiMatch(String value, GetStringPropertyFunction<T>... getPropertyFuncs) {
        return new MultiMatchExtQueryBuilder<>(value, getPropertyFuncs);
    }

    public MatchPhraseExtQueryBuilder<T> matchPhrase(GetStringPropertyFunction<T> getPropertyFunc, String text) {
        return new MatchPhraseExtQueryBuilder<>(getPropertyFunc, text);
    }

    public MatchPhrasePrefixExtQueryBuilder<T> matchPhrasePrefix(GetStringPropertyFunction<T> getPropertyFunc, String text) {
        return new MatchPhrasePrefixExtQueryBuilder<>(getPropertyFunc, text);
    }

    public TermExtQueryBuilder<T, String> term(GetStringArrayPropertyFunction<T> getPropertyFunc, String value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Integer> term(GetIntegerArrayPropertyFunction<T> getPropertyFunc, int value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Long> term(GetLongArrayPropertyFunction<T> getPropertyFunc, long value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Float> term(GetFloatArrayPropertyFunction<T> getPropertyFunc, float value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Double> term(GetDoubleArrayPropertyFunction<T> getPropertyFunc, double value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Date> term(GetDateArrayPropertyFunction<T> getPropertyFunc, Date value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, BigDecimal> term(GetBigDecimalArrayPropertyFunction<T> getPropertyFunc, BigDecimal value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, String> term(GetStringPropertyFunction<T> getPropertyFunc, String value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Integer> term(GetIntegerPropertyFunction<T> getPropertyFunc, int value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Long> term(GetLongPropertyFunction<T> getPropertyFunc, long value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Float> term(GetFloatPropertyFunction<T> getPropertyFunc, float value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Double> term(GetDoublePropertyFunction<T> getPropertyFunc, double value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Boolean> term(GetBooleanPropertyFunction<T> getPropertyFunc, boolean value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, Date> term(GetDatePropertyFunction<T> getPropertyFunc, Date value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }

    public TermExtQueryBuilder<T, BigDecimal> term(GetBigDecimalPropertyFunction<T> getPropertyFunc, BigDecimal value) {
        return new TermExtQueryBuilder<>(getPropertyFunc, value);
    }


    public TermsExtQueryBuilder<T, String> terms(GetStringArrayPropertyFunction<T> getPropertyFunc, String... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Integer> terms(GetIntegerArrayPropertyFunction<T> getPropertyFunc, Integer... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Long> terms(GetLongArrayPropertyFunction<T> getPropertyFunc, Long... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Float> terms(GetFloatArrayPropertyFunction<T> getPropertyFunc, Float... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Double> terms(GetDoubleArrayPropertyFunction<T> getPropertyFunc, Double... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Date> terms(GetDateArrayPropertyFunction<T> getPropertyFunc, Date... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, BigDecimal> terms(GetBigDecimalArrayPropertyFunction<T> getPropertyFunc, BigDecimal... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, String> terms(GetStringPropertyFunction<T> getPropertyFunc, String... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Integer> terms(GetIntegerPropertyFunction<T> getPropertyFunc, Integer... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Long> terms(GetLongPropertyFunction<T> getPropertyFunc, Long... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Float> terms(GetFloatPropertyFunction<T> getPropertyFunc, Float... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Double> terms(GetDoublePropertyFunction<T> getPropertyFunc, Double... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Boolean> terms(GetBooleanPropertyFunction<T> getPropertyFunc, Boolean... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, Date> terms(GetDatePropertyFunction<T> getPropertyFunc, Date... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public TermsExtQueryBuilder<T, BigDecimal> terms(GetBigDecimalPropertyFunction<T> getPropertyFunc, BigDecimal... values) {
        return new TermsExtQueryBuilder<>(getPropertyFunc, values);
    }

    public FuzzyExtQueryBuilder<T> fuzzy(GetStringPropertyFunction<T> getPropertyFunc, String value) {
        return new FuzzyExtQueryBuilder<>(getPropertyFunc, value);
    }

    public PrefixExtQueryBuilder<T> prefix(GetStringPropertyFunction<T> getPropertyFunc, String prefix) {
        return new PrefixExtQueryBuilder<>(getPropertyFunc, prefix);
    }

    public RangeExtQueryBuilder<T, Integer> range(GetIntegerPropertyFunction<T> getPropertyFunc) {
        return new RangeExtQueryBuilder<>(getPropertyFunc);
    }

    public RangeExtQueryBuilder<T, Long> range(GetLongPropertyFunction<T> getPropertyFunc) {
        return new RangeExtQueryBuilder<>(getPropertyFunc);
    }

    public RangeExtQueryBuilder<T, Float> range(GetFloatPropertyFunction<T> getPropertyFunc) {
        return new RangeExtQueryBuilder<>(getPropertyFunc);
    }

    public RangeExtQueryBuilder<T, Double> range(GetDoublePropertyFunction<T> getPropertyFunc) {
        return new RangeExtQueryBuilder<>(getPropertyFunc);
    }

    public RangeExtQueryBuilder<T, Date> range(GetDatePropertyFunction<T> getPropertyFunc) {
        return new RangeExtQueryBuilder<>(getPropertyFunc);
    }

    public RangeExtQueryBuilder<T, BigDecimal> range(GetBigDecimalPropertyFunction<T> getPropertyFunc) {
        return new RangeExtQueryBuilder<>(getPropertyFunc);
    }

    public WildcardExtQueryBuilder<T> wildcard(GetStringPropertyFunction<T> getPropertyFunc, String query) {
        return new WildcardExtQueryBuilder<>(getPropertyFunc, query);
    }

    public RegexpExtQueryBuilder<T> regexp(GetStringPropertyFunction<T> getPropertyFunc, String regexp) {
        return new RegexpExtQueryBuilder<>(getPropertyFunc, regexp);
    }
}
