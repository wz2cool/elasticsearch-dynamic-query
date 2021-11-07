package com.github.wz2cool.elasticsearch.model;

import java.lang.reflect.Field;

/**
 * @author Frank
 */
public class ColumnInfo {

    private String columnName;
    private String propertyName;
    private Field field;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }
}
