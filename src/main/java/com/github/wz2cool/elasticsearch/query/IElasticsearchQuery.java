package com.github.wz2cool.elasticsearch.query;

import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;

/**
 * @author Frank
 **/
public interface IElasticsearchQuery {

    NativeSearchQueryBuilder buildNativeSearch();
}
