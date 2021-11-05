package com.github.wz2cool.elasticsearch.operator;

public class FuzzyOperator {

    private final String value;

    public FuzzyOperator(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
