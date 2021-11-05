package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.lambda.GetBigDecimalPropertyFunction;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.operator.FilterOperators;
import com.github.wz2cool.elasticsearch.operator.IFilterOperator;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> {

    private final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();


    public S and(boolean enable,
                 GetBigDecimalPropertyFunction<T> getPropertyFunc,
                 Function<FilterOperators, IFilterOperator<T>> filterOperatorFunc) {
        if (!enable) {
            return (S) this;
        }
        return null;
    }

    public S andGroup(boolean enable, FilterMode filterMode, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (enable) {
            FilterGroup<T> filterGroup = new FilterGroup<>();
            final QueryBuilder queryBuilder = groupConsumer.apply(filterGroup).buildQuery();
            if (filterMode == FilterMode.MUST) {
                booleanQueryBuilder.must(queryBuilder);
            } else if (filterMode == FilterMode.MUST_NOT) {
                booleanQueryBuilder.mustNot(queryBuilder);
            } else {
                booleanQueryBuilder.filter(queryBuilder);
            }
        }
        return (S) this;
    }

    public S orGroup(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return orGroup(true, groupConsumer);
    }

    public S orGroup(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (enable) {
            FilterGroup<T> filterGroup = new FilterGroup<>();
            booleanQueryBuilder.should(groupConsumer.apply(filterGroup).buildQuery());
        }
        return (S) this;
    }

    public QueryBuilder buildQuery() {
        return this.booleanQueryBuilder;
    }
}
