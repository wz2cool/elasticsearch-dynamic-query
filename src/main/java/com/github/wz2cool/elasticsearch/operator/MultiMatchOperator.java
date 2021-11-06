package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MultiMatchOperator<T> {

    private static final float DEFAULT_BOOST = 1.0f;
    private final Map<String, Float> fieldMap = new HashMap<>();

    private String analyzer;
    private Integer slop;
    private String fuzziness;
    private Integer prefixLength;
    private Integer maxExpansions;
    private String minimumShouldMatch;
    private Float tieBreaker;
    private Boolean lenient;
    private Float cutoffFrequency;
    private Boolean fuzzyTranspositions;


    public MultiMatchOperator(GetStringPropertyFunction<T>[] getPropertyFuncs) {
        for (GetStringPropertyFunction<T> getPropertyFunc : getPropertyFuncs) {
            field(getPropertyFunc);
        }
    }

    /**
     * See also {@link MultiMatchQueryBuilder#field(String field)}
     */
    public MultiMatchOperator<T> field(GetStringPropertyFunction<T> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        fieldMap.put(columnInfo.getColumnName(), DEFAULT_BOOST);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#field(String field, float boost)}
     */
    public MultiMatchOperator<T> field(GetStringPropertyFunction<T> getPropertyFunc, float boost) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        fieldMap.put(columnInfo.getColumnName(), boost);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#minimumShouldMatch(String minimumShouldMatch)}
     */
    public MultiMatchOperator<T> minimumShouldMatch(String minimumShouldMatch) {
        this.minimumShouldMatch = minimumShouldMatch;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#analyzer(String analyzer)}
     */
    public MultiMatchOperator<T> analyzer(String analyzer) {
        this.analyzer = analyzer;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#slop(int slop)}
     */
    public MultiMatchOperator<T> slop(int slop) {
        this.slop = slop;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#fuzziness(Object fuzziness)}
     */
    public MultiMatchOperator<T> fuzziness(String fuzziness) {
        this.fuzziness = fuzziness;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#prefixLength(int prefixLength)}
     */
    public MultiMatchOperator<T> prefixLength(Integer prefixLength) {
        this.prefixLength = prefixLength;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#tieBreaker(float tieBreaker)}
     */
    public MultiMatchOperator<T> tieBreaker(Float tieBreaker) {
        this.tieBreaker = tieBreaker;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#cutoffFrequency(float cutoff)}
     */
    public MultiMatchOperator<T> cutoffFrequency(Float cutoff) {
        this.cutoffFrequency = cutoff;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#fuzzyTranspositions(boolean fuzzyTranspositions)}
     */
    public MultiMatchOperator<T> fuzzyTranspositions(Boolean fuzzyTranspositions) {
        this.fuzzyTranspositions = fuzzyTranspositions;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#maxExpansions(int)}
     */
    public MultiMatchOperator<T> maxExpansions(Integer maxExpansions) {
        this.maxExpansions = maxExpansions;
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#lenient(boolean)}
     */
    public MultiMatchOperator<T> lenient(Boolean lenient) {
        this.lenient = lenient;
        return this;
    }

    public QueryBuilder buildQuery(String value) {
        final MultiMatchQueryBuilder multiMatchQueryBuilder = new MultiMatchQueryBuilder(value);
        for (Map.Entry<String, Float> entry : fieldMap.entrySet()) {
            multiMatchQueryBuilder.field(entry.getKey(), entry.getValue());
        }
        if (Objects.nonNull(analyzer)) {
            multiMatchQueryBuilder.analyzer(analyzer);
        }
        if (Objects.nonNull(slop)) {
            multiMatchQueryBuilder.slop(slop);
        }
        if (Objects.nonNull(fuzziness)) {
            multiMatchQueryBuilder.fuzziness(fuzziness);
        }
        if (Objects.nonNull(prefixLength)) {
            multiMatchQueryBuilder.prefixLength(prefixLength);
        }
        if (Objects.nonNull(maxExpansions)) {
            multiMatchQueryBuilder.maxExpansions(maxExpansions);
        }
        if (Objects.nonNull(minimumShouldMatch)) {
            multiMatchQueryBuilder.minimumShouldMatch(minimumShouldMatch);
        }
        if (Objects.nonNull(tieBreaker)) {
            multiMatchQueryBuilder.tieBreaker(tieBreaker);
        }
        if (Objects.nonNull(lenient)) {
            multiMatchQueryBuilder.lenient(lenient);
        }
        if (Objects.nonNull(cutoffFrequency)) {
            multiMatchQueryBuilder.cutoffFrequency(cutoffFrequency);
        }
        if (Objects.nonNull(fuzzyTranspositions)) {
            multiMatchQueryBuilder.fuzzyTranspositions(fuzzyTranspositions);
        }
        return multiMatchQueryBuilder;
    }

    private <R> ColumnInfo getColumnInfo(GetPropertyFunction<T, R> getPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
        return EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
    }
}
