package com.github.wz2cool.elasticsearch.repository.support;

import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import com.github.wz2cool.elasticsearch.helper.LogicPagingHelper;
import com.github.wz2cool.elasticsearch.model.LogicPagingResult;
import com.github.wz2cool.elasticsearch.model.RowBounds;
import com.github.wz2cool.elasticsearch.model.UpDown;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.LogicPagingQuery;
import com.github.wz2cool.elasticsearch.repository.ElasticsearchExtRepository;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.repository.support.ElasticsearchEntityInformation;
import org.springframework.data.elasticsearch.repository.support.SimpleElasticsearchRepository;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Frank
 **/
public class SimpleElasticsearchExtRepository<T, I> extends SimpleElasticsearchRepository<T, I> implements ElasticsearchExtRepository<T, I> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public SimpleElasticsearchExtRepository(ElasticsearchEntityInformation<T, I> metadata,
                                            ElasticsearchOperations elasticsearchOperations) {
        super(metadata, elasticsearchOperations);
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

        if (logger.isDebugEnabled()) {
            String json = dynamicQuery.buildQueryJson(esQuery);
            logger.debug("selectByDynamicQuery: {}{}", System.lineSeparator(), json);
        }
        final SearchHits<T> searchHits = this.operations.search(esQuery, dynamicQuery.getClazz());
        return searchHits.stream()
                .map(x -> dynamicQuery.getHighlightResultMapper()
                        .mapResult(x, dynamicQuery.getClazz())).collect(Collectors.toList());
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
        if (logger.isDebugEnabled()) {
            final NativeSearchQuery nativeSearchQuery = dynamicQuery.buildNativeSearch();
            String json = dynamicQuery.buildQueryJson(nativeSearchQuery);
            logger.debug("deleteByDynamicQuery: {}{}", System.lineSeparator(), json);
        }
        this.operations.delete(dynamicQuery.buildNativeSearch(), dynamicQuery.getClazz());
    }

    @Override
    public LogicPagingResult<T> selectByLogicPaging(LogicPagingQuery<T> logicPagingQuery) {
        NativeSearchQuery esQuery = logicPagingQuery.buildNativeSearch();
        final SortOrder sortOrder = esQuery.getElasticsearchSorts().get(0).order();
        if (logger.isDebugEnabled()) {
            String json = logicPagingQuery.buildQueryJson(esQuery);
            logger.debug("selectByLogicPaging: {}{}", System.lineSeparator(), json);
        }
        List<T> dataList;
        final HighlightResultMapper highlightResultMapper = logicPagingQuery.getHighlightResultMapper();
        if (Objects.nonNull(highlightResultMapper)
                && !CollectionUtils.isEmpty(highlightResultMapper.getPropertyMapping(logicPagingQuery.getClazz()))) {
            final SearchHits<T> searchHits = this.operations.search(esQuery, logicPagingQuery.getClazz());
            dataList = searchHits.stream()
                    .map(x -> logicPagingQuery.getHighlightResultMapper()
                            .mapResult(x, logicPagingQuery.getClazz())).collect(Collectors.toList());
        } else {
            final SearchHits<T> searchHits = this.operations.search(esQuery, logicPagingQuery.getClazz());
            dataList = searchHits.stream()
                    .map(SearchHit::getContent).collect(Collectors.toList());
        }
        if (!logicPagingQuery.getSortOrder().equals(sortOrder)) {
            Collections.reverse(dataList);
        }
        Optional<LogicPagingResult<T>> logicPagingResultOptional = LogicPagingHelper.getPagingResult(
                logicPagingQuery.getPagingPropertyFunc(), dataList, logicPagingQuery.getPageSize(),
                logicPagingQuery.getUpDown(), logicPagingQuery.isUpAutomaticSupplement());
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

    /**
     * 左开右闭 例如(0,20]查询的是1-20
     *
     * @param dynamicQuery 查询sql
     * @param rowBounds    分页rouBounds
     * @return java.util.List<T> 返回集合
     * @author dengmeiluan
     * @date 2022/6/8 18:00
     */
    @Override
    public List<T> selectRowBoundsByDynamicQuery(DynamicQuery<T> dynamicQuery, RowBounds rowBounds) {
        int offset = rowBounds.getOffset();
        int limit = rowBounds.getLimit();
        int pageSize = limit - offset;
        if (pageSize <= 0) {
            return Collections.emptyList();
        }
        int modularResidueValue = offset % pageSize;
        //如果模等于0则代表直接代入分页参数即可
        if (modularResidueValue == 0) {
            return selectByDynamicQuery(dynamicQuery, offset / pageSize, pageSize);
        } else {
            //如果分页长度大于偏移长度,直接从第一页查询,然后分页长度扩大等于偏移的长度,查询结果跳过偏移长度
            if (pageSize > offset) {
                return selectByDynamicQuery(dynamicQuery, 0, pageSize + offset).stream().skip(offset).collect(Collectors.toList());
                //如果分页长度小于偏移长度,分页长度扩大等于摸的长度,分页页码重新计算,查询结果只取入参的长度
            } else {
                int finalPageSize = pageSize + modularResidueValue;
                return selectByDynamicQuery(dynamicQuery, offset / finalPageSize, finalPageSize).stream().limit(pageSize).collect(Collectors.toList());
            }
        }
    }
}
