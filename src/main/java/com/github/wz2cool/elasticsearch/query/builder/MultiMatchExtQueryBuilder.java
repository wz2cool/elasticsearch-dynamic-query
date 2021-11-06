package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetStringPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

/**
 * Same as {@link MatchQueryBuilder} but supports multiple fields.
 *
 * @author Frank
 */
public class MultiMatchExtQueryBuilder<T> implements ExtQueryBuilder {

    private final MultiMatchQueryBuilder multiMatchQueryBuilder;

    /**
     * See also {@link MultiMatchQueryBuilder#MultiMatchQueryBuilder(Object value, String... fields)}
     */
    @SafeVarargs
    public MultiMatchExtQueryBuilder(String value, GetStringPropertyFunction<T>... getPropertyFuncs) {
        this.multiMatchQueryBuilder = new MultiMatchQueryBuilder(value);
        for (GetStringPropertyFunction<T> getPropertyFunc : getPropertyFuncs) {
            field(getPropertyFunc);
        }
    }

    /**
     * See also {@link MultiMatchQueryBuilder#field(String field)}
     */
    public MultiMatchExtQueryBuilder<T> field(GetStringPropertyFunction<T> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.multiMatchQueryBuilder.field(columnInfo.getColumnName());
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#field(String field, float boost)}
     */
    public MultiMatchExtQueryBuilder<T> field(GetStringPropertyFunction<T> getPropertyFunc, float boost) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.multiMatchQueryBuilder.field(columnInfo.getColumnName(), boost);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#minimumShouldMatch(String minimumShouldMatch)}
     */
    public MultiMatchExtQueryBuilder<T> minimumShouldMatch(String minimumShouldMatch) {
        this.multiMatchQueryBuilder.minimumShouldMatch(minimumShouldMatch);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#analyzer(String analyzer)}
     */
    public MultiMatchExtQueryBuilder<T> analyzer(String analyzer) {
        this.multiMatchQueryBuilder.analyzer(analyzer);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#slop(int slop)}
     */
    public MultiMatchExtQueryBuilder<T> slop(int slop) {
        this.multiMatchQueryBuilder.slop(slop);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#fuzziness(Object fuzziness)}
     */
    public MultiMatchExtQueryBuilder<T> fuzziness(String fuzziness) {
        this.multiMatchQueryBuilder.fuzziness(fuzziness);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#fuzziness(Object fuzziness)}
     */
    public MultiMatchExtQueryBuilder<T> fuzziness(int fuzziness) {
        this.multiMatchQueryBuilder.fuzziness(fuzziness);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#prefixLength(int prefixLength)}
     */
    public MultiMatchExtQueryBuilder<T> prefixLength(int prefixLength) {
        this.multiMatchQueryBuilder.prefixLength(prefixLength);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#tieBreaker(float tieBreaker)}
     */
    public MultiMatchExtQueryBuilder<T> tieBreaker(float tieBreaker) {
        this.multiMatchQueryBuilder.tieBreaker(tieBreaker);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#cutoffFrequency(float cutoff)}
     */
    public MultiMatchExtQueryBuilder<T> cutoffFrequency(float cutoff) {
        this.multiMatchQueryBuilder.cutoffFrequency(cutoff);
        return this;
    }

    /**
     * See also {@link MultiMatchQueryBuilder#fuzzyTranspositions(boolean fuzzyTranspositions)}
     */
    public MultiMatchExtQueryBuilder<T> fuzzyTranspositions(boolean fuzzyTranspositions) {
        this.multiMatchQueryBuilder.fuzzyTranspositions(fuzzyTranspositions);
        return this;
    }

    @Override
    public QueryBuilder build() {
        return this.multiMatchQueryBuilder;
    }
}
