package com.github.wz2cool.elasticsearch.query;

import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;

/**
 * @author Frank
 **/
public interface IElasticsearchQuery {

    NativeSearchQuery buildNativeSearch();
}
