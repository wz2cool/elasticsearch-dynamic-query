package com.github.wz2cool.elasticsearch.operator;

import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;

import java.util.Objects;

public class RangeOperator<R> implements IFilterOperator<R> {

    private R gt;
    private R gte;
    private R lt;
    private R lte;
    private String timezone;
    private String relation;

    /**
     * @see RangeQueryBuilder#gt(Object)
     */
    public RangeOperator<R> gt(boolean enable, R from) {
        if (enable) {
            this.gt = from;
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#gt(Object)
     */
    public RangeOperator<R> gt(R from) {
        this.gt = from;
        return this;
    }

    /**
     * @see RangeQueryBuilder#gte(Object)
     */
    public RangeOperator<R> gte(boolean enable, R from) {
        if (enable) {
            this.gte = from;
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#gte(Object)
     */
    public RangeOperator<R> gte(R from) {
        this.gte = from;
        return this;
    }

    /**
     * @see RangeQueryBuilder#lt(Object)
     */
    public RangeOperator<R> lt(boolean enable, R to) {
        if (enable) {
            this.lt = to;
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#lt(Object)
     */
    public RangeOperator<R> lt(R to) {
        this.lt = to;
        return this;
    }

    /**
     * @see RangeQueryBuilder#lte(Object)
     */
    public RangeOperator<R> lte(boolean enable, R to) {
        if (enable) {
            this.lte = to;
        }
        return this;
    }

    /**
     * @see RangeQueryBuilder#lte(Object)
     */
    public RangeOperator<R> lte(R to) {
        this.lte = to;
        return this;
    }

    /**
     * @see RangeQueryBuilder#timeZone(String)
     */
    public RangeOperator<R> timezone(String timeZone) {
        this.timezone = timeZone;
        return this;
    }

    /**
     * @see RangeQueryBuilder#relation(String)
     */
    public RangeOperator<R> relation(String relation) {
        this.relation = relation;
        return this;
    }

    @Override
    public <T> QueryBuilder buildQuery(GetPropertyFunction<T, R> getPropertyFunc) {
        final ColumnInfo columnInfo = getColumnInfo(getPropertyFunc);
        RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder(columnInfo.getColumnName());
        if (Objects.nonNull(gt)) {
            rangeQueryBuilder.gt(gt);
        }
        if (Objects.nonNull(gte)) {
            rangeQueryBuilder.gte(gte);
        }
        if (Objects.nonNull(lt)) {
            rangeQueryBuilder.lt(lt);
        }
        if (Objects.nonNull(lte)) {
            rangeQueryBuilder.lte(lte);
        }
        if (Objects.nonNull(timezone)) {
            rangeQueryBuilder.timeZone(timezone);
        }
        if (Objects.nonNull(relation)) {
            rangeQueryBuilder.relation(relation);
        }
        return rangeQueryBuilder;
    }
}
