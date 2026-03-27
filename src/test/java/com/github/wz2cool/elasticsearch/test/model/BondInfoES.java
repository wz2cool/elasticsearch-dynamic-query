package com.github.wz2cool.elasticsearch.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;

@Document(indexName = "test_bond_info456")
public class BondInfoES {
    @Transient
    private Float score;
    @Id
    private Long bondUniCode;
    private Integer remainingTenorSort;
    private String bondCode;
    private String bondFullName;

    public Long getBondUniCode() {
        return bondUniCode;
    }

    public void setBondUniCode(Long bondUniCode) {
        this.bondUniCode = bondUniCode;
    }

    public String getBondCode() {
        return bondCode;
    }

    public void setBondCode(String bondCode) {
        this.bondCode = bondCode;
    }

    public String getBondFullName() {
        return bondFullName;
    }

    public void setBondFullName(String bondFullName) {
        this.bondFullName = bondFullName;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getRemainingTenorSort() {
        return remainingTenorSort;
    }

    public void setRemainingTenorSort(Integer remainingTenorSort) {
        this.remainingTenorSort = remainingTenorSort;
    }
}
