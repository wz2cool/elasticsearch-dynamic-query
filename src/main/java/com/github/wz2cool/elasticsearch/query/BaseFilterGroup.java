package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.query.builder.ExtQueryBuilder;
import com.github.wz2cool.elasticsearch.query.builder.ExtQueryBuilders;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;

import java.util.function.Function;
import java.util.function.UnaryOperator;

@SuppressWarnings("unchecked")
public abstract class BaseFilterGroup<T, S extends BaseFilterGroup<T, S>> {

    private final BoolQueryBuilder booleanQueryBuilder = new BoolQueryBuilder();
    private final ExtQueryBuilders<T> extQueryBuilders = new ExtQueryBuilders<>();

    public S and(Function<ExtQueryBuilders<T>, ExtQueryBuilder> filter) {
        return and(true, filter);
    }

    public S and(boolean enable, Function<ExtQueryBuilders<T>, ExtQueryBuilder> filter) {
        return and(enable, FilterMode.MUST, filter);
    }

    public S and(FilterMode filterMode, Function<ExtQueryBuilders<T>, ExtQueryBuilder> filter) {
        return and(true, filterMode, filter);
    }

    public S and(boolean enable, FilterMode filterMode, Function<ExtQueryBuilders<T>, ExtQueryBuilder> filter) {
        if (enable) {
            final ExtQueryBuilder extQueryBuilder = filter.apply(extQueryBuilders);
            if (filterMode == FilterMode.MUST) {
                booleanQueryBuilder.must(extQueryBuilder.build());
            } else if (filterMode == FilterMode.MUST_NOT) {
                booleanQueryBuilder.mustNot(extQueryBuilder.build());
            } else {
                booleanQueryBuilder.filter(extQueryBuilder.build());
            }
        }
        return (S) this;
    }

    public S or(Function<ExtQueryBuilders<T>, ExtQueryBuilder> filter) {
        return or(true, filter);
    }

    public S or(boolean enable, Function<ExtQueryBuilders<T>, ExtQueryBuilder> filter) {
        if (enable) {
            final ExtQueryBuilder extQueryBuilder = filter.apply(extQueryBuilders);
            booleanQueryBuilder.should(extQueryBuilder.build());
        }
        return (S) this;
    }

    public S andGroup(UnaryOperator<FilterGroup<T>> groupConsumer) {
        return andGroup(true, groupConsumer);
    }

    public S andGroup(boolean enable, UnaryOperator<FilterGroup<T>> groupConsumer) {
        if (enable) {
            FilterGroup<T> filterGroup = new FilterGroup<>();
            booleanQueryBuilder.must(groupConsumer.apply(filterGroup).buildQuery());
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
