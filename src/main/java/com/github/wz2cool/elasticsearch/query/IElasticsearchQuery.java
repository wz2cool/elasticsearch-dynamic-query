package com.github.wz2cool.elasticsearch.query;


import org.elasticsearch.search.sort.SortBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.util.CollectionUtils;

import java.util.List;
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
        final String sortItems = String.join(",", sortStrings);
        stringBuilder.append("{")
                .append("\"query\":")
                .append(nativeSearchQuery.getQuery().toString()).append(",");
        if (!CollectionUtils.isEmpty(nativeSearchQuery.getElasticsearchSorts())) {
            stringBuilder.append("\"sort\":[")
                    .append(sortItems).append("],");
        }
        stringBuilder.append("\"highlight\":")
                .append(nativeSearchQuery.getHighlightBuilder().toString())
                .append("}");
        return stringBuilder.toString();
    }
}
