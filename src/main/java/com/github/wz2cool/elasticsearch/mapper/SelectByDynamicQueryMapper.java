package com.github.wz2cool.elasticsearch.mapper;

import com.github.wz2cool.elasticsearch.query.DynamicQuery;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("java:S3740")
public interface SelectByDynamicQueryMapper<T> {

    List<T> selectByDynamicQuery(DynamicQuery<T> dynamicQuery);

    Optional<T> selectFirstByDynamicQuery(DynamicQuery<T> dynamicQuery);

    List<T> selectByDynamicQuery(DynamicQuery<T> dynamicQuery, int page, int pageSize);
}
