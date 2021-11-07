package com.github.wz2cool.elasticsearch.model;

import org.elasticsearch.search.sort.SortOrder;

public class SortDescriptor {

    private String propertyName;
    private SortOrder sortOrder;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }
}
