package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetCommonPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import com.github.wz2cool.elasticsearch.model.QueryMode;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

@SuppressWarnings("java:S3740")
public class DynamicQuery<T> extends BaseFilterGroup<T, DynamicQuery<T>> {

    private final Class<T> clazz;
    private final HighlightResultMapper highlightResultMapper = new HighlightResultMapper();
    private final HighlightBuilder highlightBuilder = new HighlightBuilder();
    private final List<SortBuilder> sortBuilders = new ArrayList<>();
    private final QueryMode queryMode;

    private DynamicQuery(Class<T> clazz, QueryMode queryMode) {
        this.clazz = clazz;
        this.queryMode = queryMode;
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz) {
        return new DynamicQuery<>(clazz, QueryMode.QUERY);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode) {
        return new DynamicQuery<>(clazz, queryMode);
    }

    public DynamicQuery<T> scoreMapping(BiConsumer<T, Float> setScorePropertyFunc) {
        highlightResultMapper.registerScoreMapping(this.clazz, setScorePropertyFunc);
        return this;
    }

    public DynamicQuery<T> highlightMapping(GetPropertyFunction<T, String> getSearchPropertyFunc,
                                            BiConsumer<T, String> setHighLightPropertyFunc) {
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getSearchPropertyFunc);
        final ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        highlightBuilder.field(columnInfo.getColumnName());
        highlightResultMapper.registerHitMapping(this.clazz, getSearchPropertyFunc, setHighLightPropertyFunc);
        return this;
    }

    public DynamicQuery<T> orderByScore(SortOrder sortOrder) {
        return orderByScore(true, sortOrder);
    }

    public DynamicQuery<T> orderByScore(boolean enable, SortOrder sortOrder) {
        if (enable) {
            final ScoreSortBuilder order = new ScoreSortBuilder().order(sortOrder);
            this.sortBuilders.add(order);
        }
        return this;
    }

    public DynamicQuery<T> orderBy(GetCommonPropertyFunction<T> getPropertyFunc, SortOrder sortOrder) {
        return orderBy(true, getPropertyFunc, sortOrder);
    }

    public DynamicQuery<T> orderBy(boolean enable, GetCommonPropertyFunction<T> getPropertyFunc, SortOrder sortOrder) {
        if (enable) {
            final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPropertyFunc);
            ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
            final FieldSortBuilder order = new FieldSortBuilder(columnInfo.getColumnName()).order(sortOrder);
            this.sortBuilders.add(order);
        }
        return this;
    }

    public HighlightResultMapper getHighlightResultMapper() {
        return highlightResultMapper;
    }

    public HighlightBuilder getHighlightBuilder() {
        return highlightBuilder;
    }

    public List<SortBuilder> getSortBuilders() {
        return sortBuilders;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public QueryMode getQueryMode() {
        return queryMode;
    }
}
