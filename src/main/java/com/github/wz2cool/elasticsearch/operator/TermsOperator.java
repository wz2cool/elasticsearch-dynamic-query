package com.github.wz2cool.elasticsearch.operator;

import java.util.*;

public class TermsOperator<T> implements IFilterOperator<T> {

    private List<T> value;

    public TermsOperator(T[] values) {
        if (Objects.isNull(values)) {
            this.value = new ArrayList<>();
        } else {
            this.value = Arrays.asList(values);
        }
    }

    public TermsOperator(Collection<T> values) {
        if (Objects.isNull(values)) {
            this.value = new ArrayList<>();
        } else {
            this.value = new ArrayList<>(values);
        }
    }

    public List<T> getValue() {
        return this.value;
    }
}
