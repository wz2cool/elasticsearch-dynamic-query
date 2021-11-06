package com.github.wz2cool.elasticsearch.query.builder;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;

/**
 * A Query that matches documents within an range of terms.
 *
 * @author Frank
 */
public class RangeExtQueryBuilder<T, P extends Comparable> implements ExtQueryBuilder {

    private final RangeQueryBuilder rangeQueryBuilder;

    /**
     * @see RangeQueryBuilder#RangeQueryBuilder(String)
     */
    public RangeExtQueryBuilder(GetPropertyFunction<T, P> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        this.rangeQueryBuilder = new RangeQueryBuilder(columnInfo.getColumnName());
    }

    /**
     * @see RangeQueryBuilder#gt(Object)
     */
    public RangeExtQueryBuilder<T, P> gt(boolean enable, P from) {
        if (enable) {
            rangeQueryBuilder.gt(getFilterValue(from));
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#gt(Object)
     */
    public RangeExtQueryBuilder<T, P> gt(P from) {
        rangeQueryBuilder.gt(getFilterValue(from));
        return this;
    }

    /**
     * @see RangeQueryBuilder#gte(Object)
     */
    public RangeExtQueryBuilder<T, P> gte(boolean enable, P from) {
        if (enable) {
            rangeQueryBuilder.gte(getFilterValue(from));
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#gte(Object)
     */
    public RangeExtQueryBuilder<T, P> gte(P from) {
        rangeQueryBuilder.gte(getFilterValue(from));
        return this;
    }

    /**
     * @see RangeQueryBuilder#lt(Object)
     */
    public RangeExtQueryBuilder<T, P> lt(boolean enable, P to) {
        if (enable) {
            rangeQueryBuilder.lt(getFilterValue(to));
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#lt(Object)
     */
    public RangeExtQueryBuilder<T, P> lt(P to) {
        rangeQueryBuilder.lt(getFilterValue(to));
        return this;
    }

    /**
     * @see RangeQueryBuilder#lte(Object)
     */
    public RangeExtQueryBuilder<T, P> lte(boolean enable, P to) {
        if (enable) {
            rangeQueryBuilder.lte(getFilterValue(to));
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#lte(Object)
     */
    public RangeExtQueryBuilder<T, P> lte(P to) {
        rangeQueryBuilder.lte(getFilterValue(to));
        return this;
    }

    /**
     * @see RangeQueryBuilder#timeZone(String)
     */
    public RangeExtQueryBuilder<T, P> timezone(String timeZone) {
        rangeQueryBuilder.timeZone(timeZone);
        return this;
    }

    /**
     * @see RangeQueryBuilder#relation(String)
     */
    public RangeExtQueryBuilder<T, P> relation(String relation) {
        rangeQueryBuilder.relation(relation);
        return this;
    }

    @Override
    public QueryBuilder build() {
        return this.rangeQueryBuilder;
    }
}
