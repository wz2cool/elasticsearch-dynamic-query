package com.github.wz2cool.elasticsearch.repository;

import com.github.wz2cool.elasticsearch.mapper.DeleteByDynamicQueryMapper;
import com.github.wz2cool.elasticsearch.mapper.SelectByDynamicQueryMapper;
import com.github.wz2cool.elasticsearch.mapper.SelectByLogicPagingQueryMapper;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Abstract Elasticsearch Repository
 *
 * @param <T> entity class
 * @author Frank
 */
public interface ElasticsearchExtRepository<T, I> extends
        ElasticsearchRepository<T, I>,
        SelectByLogicPagingQueryMapper<T>,
        SelectByDynamicQueryMapper<T>,
        DeleteByDynamicQueryMapper<T> {
}
