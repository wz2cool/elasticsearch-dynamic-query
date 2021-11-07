package com.github.wz2cool.elasticsearch.mapper;

import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.query.DeleteQuery;

/**
 * https://www.elastic.co/guide/en/elasticsearch/client/java-rest/master/java-rest-high-document-delete-by-query.html
 *
 * @author Frank
 **/
public interface DeleteByDynamicQueryMapper<T> {


    /**
     * delete by dynamic query
     *
     * @param dynamicQuery dynamic query
     */
    void deleteByDynamicQuery(DynamicQuery<T> dynamicQuery);
}
