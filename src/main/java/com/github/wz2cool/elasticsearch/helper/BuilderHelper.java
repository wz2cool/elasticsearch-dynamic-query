package com.github.wz2cool.elasticsearch.helper;

import com.github.wz2cool.elasticsearch.model.FilterMode;
import org.elasticsearch.search.sort.SortOrder;

public final class BuilderHelper {

    private BuilderHelper() {

    }

    public static FilterMode filter() {
        return FilterMode.FILTER;
    }

    public static FilterMode must() {
        return FilterMode.MUST;
    }

    public static FilterMode mustNot() {
        return FilterMode.MUST_NOT;
    }

    public static SortOrder asc() {
        return SortOrder.ASC;
    }

    public static SortOrder desc() {
        return SortOrder.DESC;
    }
}
