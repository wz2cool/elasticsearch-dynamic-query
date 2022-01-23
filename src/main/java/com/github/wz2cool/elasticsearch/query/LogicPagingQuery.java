package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.helper.LogicPagingHelper;
import com.github.wz2cool.elasticsearch.lambda.GetLongPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.*;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.FetchSourceFilter;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SourceFilter;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.BiConsumer;

public class LogicPagingQuery<T> extends BaseFilterGroup<T, LogicPagingQuery<T>> implements IElasticsearchQuery {

    private final Class<T> clazz;
    private final UpDown upDown;
    private final SortOrder sortOrder;
    private final GetLongPropertyFunction<T> pagingPropertyFunc;
    private QueryBuilder queryBuilder;
    private int pageSize = 10;
    private Long lastStartPageId;
    private Long lastEndPageId;
    private boolean upAutomaticSupplement = true;
    private HighlightResultMapper highlightResultMapper = new HighlightResultMapper();
    private HighlightBuilder highlightBuilder = new HighlightBuilder();
    private final QueryMode queryMode;
    private final String route;
    private String[] selectedColumns = new String[]{};
    private String[] ignoredColumns = new String[]{};

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

    private LogicPagingQuery(Class<T> clazz, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown, String route) {
        this(clazz, QueryMode.QUERY, pagingPropertyFunc, sortOrder, upDown, route);
    }

    private LogicPagingQuery(Class<T> clazz, QueryMode queryMode, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown, String route) {
        this.clazz = clazz;
        this.upDown = upDown;
        this.sortOrder = sortOrder;
        this.pagingPropertyFunc = pagingPropertyFunc;
        this.queryMode = queryMode;
        this.route = route;
    }

    public static <T> LogicPagingQuery<T> createQuery(
            Class<T> clazz, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown) {
        return new LogicPagingQuery<>(clazz, pagingPropertyFunc, sortOrder, upDown, null);
    }

    public static <T> LogicPagingQuery<T> createQuery(
            Class<T> clazz, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown, String route) {
        return new LogicPagingQuery<>(clazz, pagingPropertyFunc, sortOrder, upDown, route);
    }

    public static <T> LogicPagingQuery<T> createQuery(
            Class<T> clazz, QueryMode queryMode, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown) {
        return new LogicPagingQuery<>(clazz, queryMode, pagingPropertyFunc, sortOrder, upDown, null);
    }

    public static <T> LogicPagingQuery<T> createQuery(
            Class<T> clazz, QueryMode queryMode, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown, String route) {
        return new LogicPagingQuery<>(clazz, queryMode, pagingPropertyFunc, sortOrder, upDown, route);
    }

    public LogicPagingQuery<T> scoreMapping(BiConsumer<T, Float> setScorePropertyFunc) {
        highlightResultMapper.registerScoreMapping(this.clazz, setScorePropertyFunc);
        return this;
    }

    public LogicPagingQuery<T> highlightMapping(GetPropertyFunction<T, String> getSearchPropertyFunc,
                                                BiConsumer<T, String> setHighLightPropertyFunc) {
        PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getSearchPropertyFunc);
        ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        highlightBuilder.field(columnInfo.getColumnName());
        highlightResultMapper.registerHitMapping(this.clazz, getSearchPropertyFunc, setHighLightPropertyFunc);
        return this;
    }

    @SafeVarargs
    public final LogicPagingQuery<T> select(GetPropertyFunction<T, Object>... getPropertyFunctions) {
        String[] newSelectProperties = new String[getPropertyFunctions.length];
        for (int i = 0; i < getPropertyFunctions.length; i++) {
            newSelectProperties[i] = getColumnName(getPropertyFunctions[i]);
        }
        this.addSelectedColumns(newSelectProperties);
        return this;
    }

    @SafeVarargs
    public final LogicPagingQuery<T> ignore(GetPropertyFunction<T, Object>... getPropertyFunctions) {
        String[] newIgnoreProperties = new String[getPropertyFunctions.length];
        for (int i = 0; i < getPropertyFunctions.length; i++) {
            newIgnoreProperties[i] = getColumnName(getPropertyFunctions[i]);
        }
        this.ignoreSelectedColumns(newIgnoreProperties);
        return this;
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

    public QueryBuilder getQueryBuilder() {
        return queryBuilder != null ? queryBuilder : getFilterQuery();
    }

    public void setQueryBuilder(QueryBuilder queryBuilder) {
        this.queryBuilder = queryBuilder;
    }

    public Class<T> getClazz() {
        return clazz;
    }

    public UpDown getUpDown() {
        return upDown;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public GetLongPropertyFunction<T> getPagingPropertyFunc() {
        return pagingPropertyFunc;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getLastStartPageId() {
        return lastStartPageId;
    }

    public void setLastStartPageId(Long lastStartPageId) {
        this.lastStartPageId = lastStartPageId;
    }

    public Long getLastEndPageId() {
        return lastEndPageId;
    }

    public void setLastEndPageId(Long lastEndPageId) {
        this.lastEndPageId = lastEndPageId;
    }

    public HighlightResultMapper getHighlightResultMapper() {
        return highlightResultMapper;
    }

    public void setHighlightResultMapper(HighlightResultMapper highlightResultMapper) {
        this.highlightResultMapper = highlightResultMapper;
    }

    public HighlightBuilder getHighlightBuilder() {
        return highlightBuilder;
    }

    public void setHighlightBuilder(HighlightBuilder highlightBuilder) {
        this.highlightBuilder = highlightBuilder;
    }

    public QueryMode getQueryMode() {
        return queryMode;
    }

    public String getRoute() {
        return route;
    }

    public boolean isUpAutomaticSupplement() {
        return upAutomaticSupplement;
    }

    public void setUpAutomaticSupplement(boolean upAutomaticSupplement) {
        this.upAutomaticSupplement = upAutomaticSupplement;
    }

    @Override
    public NativeSearchQuery buildNativeSearch() {
        int queryPageSize = getPageSize() + 1;
        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Map.Entry<SortDescriptor, QueryBuilder> mapEntry = LogicPagingHelper.getPagingSortFilterMap(
                getPagingPropertyFunc(),
                getSortOrder(),
                getLastStartPageId(),
                getLastEndPageId(),
                getUpDown());
        if (Objects.nonNull(getQueryBuilder())) {
            boolQueryBuilder.must(getQueryBuilder());
        }
        if (Objects.nonNull(mapEntry.getValue())) {
            boolQueryBuilder.must(mapEntry.getValue());
        }
        NativeSearchQueryBuilder esQuery = new NativeSearchQueryBuilder();
        if (StringUtils.isNotBlank(route)) {
            esQuery.withRoute(route);
        }
        if (getQueryMode() == QueryMode.QUERY) {
            esQuery.withQuery(boolQueryBuilder);
        } else {
            esQuery.withFilter(boolQueryBuilder);
        }
        final Optional<SourceFilter> sourceFilterOptional = getSourceFilter();
        sourceFilterOptional.ifPresent(esQuery::withSourceFilter);
        esQuery.withPageable(PageRequest.of(0, queryPageSize));
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPagingPropertyFunc());
        final ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        esQuery.withSort(SortBuilders.fieldSort(columnInfo.getColumnName())
                .order(mapEntry.getKey().getSortOrder()));
        esQuery.withHighlightBuilder(getHighlightBuilder());
        return esQuery.build();
    }
}


