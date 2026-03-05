package com.github.wz2cool.elasticsearch.query;

import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import com.github.wz2cool.elasticsearch.core.HighlightResultMapper;
import org.springframework.data.elasticsearch.core.query.RescorerQuery;

import java.util.ArrayList;
import java.util.List;

/**
 * Custom NativeSearchQuery that supports rescorer functionality
 */
public class CustomNativeSearchQuery extends NativeSearchQuery {

    private final List<RescorerQuery> rescorerQueries = new ArrayList<>();
    private HighlightResultMapper highlightResultMapper;

    private final HighlightBuilder highlightBuilder;

    public CustomNativeSearchQuery(NativeSearchQuery nativeSearchQuery) {
        super(nativeSearchQuery.getQuery(), nativeSearchQuery.getFilter(), 
              nativeSearchQuery.getElasticsearchSorts(), 
              nativeSearchQuery.getHighlightFields());
        this.highlightBuilder = nativeSearchQuery.getHighlightBuilder();
    }

    @Override
    public HighlightBuilder getHighlightBuilder() {
        return highlightBuilder;
    }

    public void addRescorer(RescorerQuery rescorerQuery) {
        rescorerQueries.add(rescorerQuery);
    }

    public List<RescorerQuery> getRescorerQueries() {
        return rescorerQueries;
    }

    public HighlightResultMapper getHighlightResultMapper() {
        return highlightResultMapper;
    }

    public void setHighlightResultMapper(HighlightResultMapper highlightResultMapper) {
        this.highlightResultMapper = highlightResultMapper;
    }
}