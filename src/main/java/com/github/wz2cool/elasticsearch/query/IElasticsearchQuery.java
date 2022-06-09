package com.github.wz2cool.elasticsearch.query;


import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Frank
 **/
public interface IElasticsearchQuery {

    NativeSearchQuery buildNativeSearch();

    default String buildQueryJson(NativeSearchQuery nativeSearchQuery) {
        StringBuilder stringBuilder = new StringBuilder();
        final List<String> sortStrings = nativeSearchQuery.getElasticsearchSorts()
                .stream().map(SortBuilder::toString).collect(Collectors.toList());
        QueryBuilder queryBuilder;
        if (Objects.nonNull(nativeSearchQuery.getQuery())) {
            queryBuilder = nativeSearchQuery.getQuery();
        } else {
            queryBuilder = nativeSearchQuery.getFilter();
        }
        final String sortItems = String.join(",", sortStrings);
        stringBuilder.append("{")
                .append("\"query\":")
                .append(queryBuilder.toString()).append(",");
        if (!CollectionUtils.isEmpty(nativeSearchQuery.getElasticsearchSorts())) {
            stringBuilder.append("\"sort\":[")
                    .append(sortItems).append("],");
        }
        stringBuilder.append("\"highlight\":")
                .append(nativeSearchQuery.getHighlightBuilder().toString())
                .append(",");

        final Pageable pageable = nativeSearchQuery.getPageable();

        if (pageable != Pageable.unpaged()) {
            stringBuilder.append("\"from\":").append(pageable.getOffset()).append(",")
                    .append("\"size\":").append(pageable.getPageSize());
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }
}
