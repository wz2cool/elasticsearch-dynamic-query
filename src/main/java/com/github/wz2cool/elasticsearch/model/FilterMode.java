package com.github.wz2cool.elasticsearch.model;

/**
 * @author Frank
 **/
public enum FilterMode {
    /**
     * 默认的 比如term 就会用Filter, match 用MUST
     */
    DEFAULT,
    MUST,
    MUST_NOT,
    FILTER
}
