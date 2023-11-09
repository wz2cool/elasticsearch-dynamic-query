package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.lambda.GetCommonPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.ColumnInfo;
import com.github.wz2cool.elasticsearch.model.PropertyInfo;
import com.github.wz2cool.elasticsearch.model.QueryMode;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;

@SuppressWarnings("java:S3740")
public class DynamicQuery<T> extends BaseFilterGroup<T, DynamicQuery<T>> implements IElasticsearchQuery {

    private final Class<T> clazz;
    private final HighlightResultMapper highlightResultMapper = new HighlightResultMapper();
    private final HighlightBuilder highlightBuilder = new HighlightBuilder();
    private final List<SortBuilder> sortBuilders = new ArrayList<>();
    private final QueryMode queryMode;
    private final String route;

    private String[] selectedColumns = new String[]{};
    private String[] ignoredColumns = new String[]{};

    public DynamicQuery(Class<T> clazz, QueryMode queryMode, String route) {
        this.clazz = clazz;
        this.queryMode = queryMode;
        this.route = route;
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz) {
        return new DynamicQuery<>(clazz, QueryMode.QUERY, null);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode) {
        return new DynamicQuery<>(clazz, queryMode, null);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, String route) {
        return new DynamicQuery<>(clazz, QueryMode.QUERY, route);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode, String route) {
        return new DynamicQuery<>(clazz, queryMode, route);
    }

    public String[] getSelectedColumns() {
        return selectedColumns;
    }

    public void setSelectedColumns(String[] selectedColumns) {
        this.selectedColumns = selectedColumns;
    }

    public String[] getIgnoredColumns() {
        return ignoredColumns;
    }

    public void setIgnoredColumns(String[] ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
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

    public DynamicQuery<T> orderBy(FieldSortBuilder sortBuilder) {
        return this.orderBy(true, sortBuilder);
    }

    public DynamicQuery<T> orderBy(boolean enable, FieldSortBuilder sortBuilder) {
        if (enable) {
            this.sortBuilders.add(sortBuilder);
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

    @SafeVarargs
    public final DynamicQuery<T> select(GetPropertyFunction<T, Object>... getPropertyFunctions) {
        String[] newSelectProperties = new String[getPropertyFunctions.length];
        for (int i = 0; i < getPropertyFunctions.length; i++) {
            newSelectProperties[i] = getColumnName(getPropertyFunctions[i]);
        }
        this.addSelectedColumns(newSelectProperties);
        return this;
    }

    @SafeVarargs
    public final DynamicQuery<T> ignore(GetPropertyFunction<T, Object>... getPropertyFunctions) {
        String[] newIgnoreProperties = new String[getPropertyFunctions.length];
        for (int i = 0; i < getPropertyFunctions.length; i++) {
            newIgnoreProperties[i] = getColumnName(getPropertyFunctions[i]);
        }
        this.ignoreSelectedColumns(newIgnoreProperties);
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

    @Override
    public NativeSearchQuery buildNativeSearch() {
        NativeSearchQueryBuilder esQuery = new NativeSearchQueryBuilder();
        if (StringUtils.isNotBlank(route)) {
            esQuery.withRoute(route);
        }
        if (getQueryMode() == QueryMode.QUERY) {
            esQuery.withQuery(getFilterQuery());
        } else {
            esQuery.withFilter(getFilterQuery());
        }
        for (SortBuilder sortBuilder : getSortBuilders()) {
            esQuery.withSort(sortBuilder);
        }
        final Optional<SourceFilter> sourceFilterOptional = getSourceFilter();
        sourceFilterOptional.ifPresent(esQuery::withSourceFilter);
        esQuery.withHighlightBuilder(getHighlightBuilder());
        return esQuery.build();
    }

    public void addSelectedColumns(String... newSelectedProperties) {
        setSelectedColumns(ArrayUtils.addAll(selectedColumns, newSelectedProperties));
    }

    public void ignoreSelectedColumns(String... newIgnoreProperties) {
        setIgnoredColumns(ArrayUtils.addAll(ignoredColumns, newIgnoreProperties));
    }

    private Optional<SourceFilter> getSourceFilter() {
        if (ArrayUtils.isEmpty(this.selectedColumns) && ArrayUtils.isEmpty(this.ignoredColumns)) {
            return Optional.empty();
        }

        final FetchSourceFilter fetchSourceFilter = new FetchSourceFilter(this.selectedColumns, this.ignoredColumns);
        return Optional.of(fetchSourceFilter);
    }

}
