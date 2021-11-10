package com.github.wz2cool.elasticsearch.repository.support;

import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.LogicPagingHelper;
import com.github.wz2cool.elasticsearch.model.LogicPagingResult;
import com.github.wz2cool.elasticsearch.model.UpDown;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.LogicPagingQuery;
import com.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @author Frank
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
        NativeSearchQuery esQuery = dynamicQuery.buildNativeSearch();
        final PageRequest pageRequest = PageRequest.of(page, pageSize);
        esQuery.setPageable(pageRequest);
        Page<T> ts = elasticsearchOperations.queryForPage(
                esQuery, dynamicQuery.getClazz(), dynamicQuery.getHighlightResultMapper());
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
        final QueryBuilder queryBuilder = dynamicQuery.getFilterQuery();
        DeleteQuery deleteQuery = new DeleteQuery();
        deleteQuery.setQuery(queryBuilder);
        elasticsearchOperations.delete(deleteQuery, dynamicQuery.getClazz());
    }

    @Override
    public LogicPagingResult<T> selectByLogicPaging(LogicPagingQuery<T> logicPagingQuery) {
        NativeSearchQuery esQuery = logicPagingQuery.buildNativeSearch();
        final SortOrder sortOrder = esQuery.getElasticsearchSorts().get(0).order();
        Page<T> ts;
        final HighlightResultMapper highlightResultMapper = logicPagingQuery.getHighlightResultMapper();
        if (Objects.nonNull(highlightResultMapper)
                && !CollectionUtils.isEmpty(highlightResultMapper.getPropertyMapping(logicPagingQuery.getClazz()))) {
            ts = elasticsearchOperations.queryForPage(
                    esQuery, logicPagingQuery.getClazz(), logicPagingQuery.getHighlightResultMapper());
        } else {
            ts = elasticsearchOperations.queryForPage(
                    esQuery, logicPagingQuery.getClazz());
        }
        List<T> dataList = new ArrayList<>(ts.getContent());
        if (!logicPagingQuery.getSortOrder().equals(sortOrder)) {
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
