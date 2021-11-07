package com.github.wz2cool.elasticsearch.repository.support;

import com.github.wz2cool.elasticsearch.cache.EntityCache;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.CommonsHelper;
import com.github.wz2cool.elasticsearch.helper.LogicPagingHelper;
import com.github.wz2cool.elasticsearch.model.*;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.LogicPagingQuery;
import com.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Frank
 * @date 2021/11/07
 **/
public class SimpleElasticsearchExtRepository<T, I> extends SimpleElasticsearchRepository<T, I> implements ElasticsearchExtRepository<T, I> {

    public SimpleElasticsearchExtRepository() {
        super();
    }

    public SimpleElasticsearchExtRepository(ElasticsearchEntityInformation<T, I> metadata,
                                            ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
    }

    public SimpleElasticsearchExtRepository(ElasticsearchOperations elasticsearchOperations) {
        super(elasticsearchOperations);
    }

    @Override
    public List<T> selectByDynamicQuery(DynamicQuery<T> dynamicQuery) {
        return selectByDynamicQuery(dynamicQuery, 0, 100);
    }

    @Override
    public List<T> selectByDynamicQuery(DynamicQuery<T> dynamicQuery, int page, int pageSize) {
        NativeSearchQueryBuilder esQuery = new NativeSearchQueryBuilder();
        if (dynamicQuery.getQueryMode() == QueryMode.QUERY) {
            esQuery.withQuery(dynamicQuery.buildQuery());
        } else {
            esQuery.withFilter(dynamicQuery.buildQuery());
        }
        for (SortBuilder sortBuilder : dynamicQuery.getSortBuilders()) {
            esQuery.withSort(sortBuilder);
        }
        esQuery.withHighlightBuilder(dynamicQuery.getHighlightBuilder());
        final PageRequest pageRequest = PageRequest.of(page, pageSize);
        esQuery.withPageable(pageRequest);
        Page<T> ts = elasticsearchOperations.queryForPage(
                esQuery.build(), dynamicQuery.getClazz(), dynamicQuery.getHighlightResultMapper());
        return new ArrayList<>(ts.getContent());
    }

    @Override
    public Optional<T> selectFirstByDynamicQuery(DynamicQuery<T> dynamicQuery) {
        final List<T> ts = selectByDynamicQuery(dynamicQuery, 0, 1);
        if (ts == null || ts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ts.get(0));
    }

    @Override
    public void deleteByDynamicQuery(DynamicQuery<T> dynamicQuery) {
        final QueryBuilder queryBuilder = dynamicQuery.buildQuery();
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(queryBuilder);
        elasticsearchOperations.delete(deleteQuery, dynamicQuery.getClazz());
    }

    @Override
    public LogicPagingResult<T> selectByLogicPaging(LogicPagingQuery<T> logicPagingQuery) {
        int pageSize = logicPagingQuery.getPageSize();
        int queryPageSize = pageSize + 1;
        final BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        Map.Entry<SortDescriptor, QueryBuilder> mapEntry = LogicPagingHelper.getPagingSortFilterMap(
                logicPagingQuery.getPagingPropertyFunc(),
                logicPagingQuery.getSortOrder(),
                logicPagingQuery.getLastStartPageId(),
                logicPagingQuery.getLastEndPageId(),
                logicPagingQuery.getUpDown());
        if (Objects.nonNull(logicPagingQuery.getQueryBuilder())) {
            boolQueryBuilder.must(logicPagingQuery.getQueryBuilder());
        }
        if (Objects.nonNull(mapEntry.getValue())) {
            boolQueryBuilder.must(mapEntry.getValue());
        }
        NativeSearchQueryBuilder esQuery = new NativeSearchQueryBuilder();
        if (logicPagingQuery.getQueryMode() == QueryMode.QUERY) {
            esQuery.withQuery(boolQueryBuilder);
        } else {
            esQuery.withFilter(boolQueryBuilder);
        }
        esQuery.withPageable(PageRequest.of(0, queryPageSize));

        final PropertyInfo propertyInfo = CommonsHelper.getPropertyInfo(logicPagingQuery.getPagingPropertyFunc());
        final ColumnInfo columnInfo = EntityCache.getInstance().getColumnInfo(propertyInfo.getOwnerClass(), propertyInfo.getPropertyName());
        esQuery.withSort(SortBuilders.fieldSort(columnInfo.getColumnName())
                .order(mapEntry.getKey().getSortOrder()));
        esQuery.withHighlightBuilder(logicPagingQuery.getHighlightBuilder());
        Page<T> ts;
        final HighlightResultMapper highlightResultMapper = logicPagingQuery.getHighlightResultMapper();
        if (Objects.nonNull(highlightResultMapper)
                && !CollectionUtils.isEmpty(highlightResultMapper.getPropertyMapping(logicPagingQuery.getClazz()))) {
            ts = elasticsearchOperations.queryForPage(
                    esQuery.build(), logicPagingQuery.getClazz(), logicPagingQuery.getHighlightResultMapper());
        } else {
            ts = elasticsearchOperations.queryForPage(
                    esQuery.build(), logicPagingQuery.getClazz());
        }
        List<T> dataList = new ArrayList<>(ts.getContent());
        if (!logicPagingQuery.getSortOrder().equals(mapEntry.getKey().getSortOrder())) {
            Collections.reverse(dataList);
        }
        Optional<LogicPagingResult<T>> logicPagingResultOptional = LogicPagingHelper.getPagingResult(
                logicPagingQuery.getPagingPropertyFunc(),
                dataList, logicPagingQuery.getPageSize(), logicPagingQuery.getUpDown());
        if (logicPagingResultOptional.isPresent()) {
            return logicPagingResultOptional.get();
        }
        LogicPagingQuery<T> resetPagingQuery = LogicPagingQuery.createQuery(
                logicPagingQuery.getClazz(),
                logicPagingQuery.getPagingPropertyFunc(),
                logicPagingQuery.getSortOrder(),
                UpDown.NONE);
        resetPagingQuery.setPageSize(logicPagingQuery.getPageSize());
        resetPagingQuery.setQueryBuilder(logicPagingQuery.getQueryBuilder());
        resetPagingQuery.setHighlightBuilder(logicPagingQuery.getHighlightBuilder());
        resetPagingQuery.setHighlightResultMapper(logicPagingQuery.getHighlightResultMapper());
        return selectByLogicPaging(resetPagingQuery);
    }
}
