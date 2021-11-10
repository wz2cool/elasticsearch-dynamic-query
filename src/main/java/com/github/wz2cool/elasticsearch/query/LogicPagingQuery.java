package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.helper.LogicPagingHelper;
import com.github.wz2cool.elasticsearch.lambda.GetLongPropertyFunction;
import com.github.wz2cool.elasticsearch.lambda.GetPropertyFunction;
import com.github.wz2cool.elasticsearch.model.*;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.Map;
import java.util.Objects;
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
    private HighlightResultMapper highlightResultMapper = new HighlightResultMapper();
    private HighlightBuilder highlightBuilder = new HighlightBuilder();
    private final QueryMode queryMode;

    private LogicPagingQuery(Class<T> clazz, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown) {
        this(clazz, QueryMode.QUERY, pagingPropertyFunc, sortOrder, upDown);
    }

    private LogicPagingQuery(Class<T> clazz, QueryMode queryMode, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown) {
        this.clazz = clazz;
        this.upDown = upDown;
        this.sortOrder = sortOrder;
        this.pagingPropertyFunc = pagingPropertyFunc;
        this.queryMode = queryMode;
    }

    public static <T> LogicPagingQuery<T> createQuery(
            Class<T> clazz, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown) {
        return new LogicPagingQuery<>(clazz, pagingPropertyFunc, sortOrder, upDown);
    }

    public static <T> LogicPagingQuery<T> createQuery(
            Class<T> clazz, QueryMode queryMode, GetLongPropertyFunction<T> pagingPropertyFunc, SortOrder sortOrder, UpDown upDown) {
        return new LogicPagingQuery<>(clazz, queryMode, pagingPropertyFunc, sortOrder, upDown);
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
        if (getQueryMode() == QueryMode.QUERY) {
            esQuery.withQuery(boolQueryBuilder);
        } else {
            esQuery.withFilter(boolQueryBuilder);
        }
        esQuery.withPageable(PageRequest.of(0, queryPageSize));
        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(getPagingPropertyFunc());
        final ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        esQuery.withSort(SortBuilders.fieldSort(columnInfo.getColumnName())
                .order(mapEntry.getKey().getSortOrder()));
        esQuery.withHighlightBuilder(getHighlightBuilder());
        return esQuery.build();
    }
}


