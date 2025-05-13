package com.github.wz2cool.elasticsearch.query;

import com.github.wz2cool.elasticsearch.model.QueryMode;

@SuppressWarnings("java:S3740")
public class DynamicQuery<T> extends BaseDynamicQuery<T, DynamicQuery<T>> {

    public DynamicQuery(Class<T> clazz, QueryMode queryMode, String route) {
        this.clazz = clazz;
        this.queryMode = queryMode;
        this.route = route;
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz) {
        return new DynamicQuery<>(clazz, QueryMode.QUERY, null);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode) {
        return new DynamicQuery<>(clazz, queryMode, null);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, String route) {
        return new DynamicQuery<>(clazz, QueryMode.QUERY, route);
    }

    public static <T> DynamicQuery<T> createQuery(Class<T> clazz, QueryMode queryMode, String route) {
        return new DynamicQuery<>(clazz, queryMode, route);
    }
}
