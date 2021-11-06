package com.github.wz2cool.elasticsearch.mapper;

import com.github.wz2cool.elasticsearch.model.QueryMode;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S3740")
public interface SelectByDynamicQueryMapper<T> {

    /**
     * select by dynamic query
     *
     * @param elasticsearchOperations elasticsearch operations
     * @param dynamicQuery            dynamic query
     * @return query list
     */
    default List<T> selectByDynamicQuery(ElasticsearchOperations elasticsearchOperations, DynamicQuery<T> dynamicQuery) {
        return selectByDynamicQuery(elasticsearchOperations, dynamicQuery, 0, 100);
    }

    default List<T> selectByDynamicQuery(
            ElasticsearchOperations elasticsearchOperations, DynamicQuery<T> dynamicQuery, int page, int pageSize) {
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

    default Optional<T> selectFirstByDynamicQuery(ElasticsearchOperations elasticsearchOperations, DynamicQuery<T> dynamicQuery) {
        final List<T> ts = selectByDynamicQuery(elasticsearchOperations, dynamicQuery, 0, 1);
        if (ts == null || ts.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(ts.get(0));
    }
}
