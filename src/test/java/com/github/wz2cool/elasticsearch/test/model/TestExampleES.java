package com.github.wz2cool.elasticsearch.test.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.util.Date;

@Document(indexName = "test_example", type = "testExample")
public class TestExampleES {

    @Id
    private Long id;
    @Field(type = FieldType.Text, analyzer = "hanlp_index")
    private String p1;
    private Integer p2;
    private Long p3;
    private Float p4;
    private Double p5;
    @Field(name = "p6", type = FieldType.Date)
    private Date aliasP6;
    @Field(type = FieldType.Keyword)
    private String p7;
    private BigDecimal p8;

    private Integer[] p9;

    @Transient
    private String p1Hit;

    public TestExampleES() {

    }

    public TestExampleES(Long id, String p1, Integer p2, Long p3, Float p4, Double p5, Date aliasP6, String p7, BigDecimal p8, Integer[] p9) {
        this.id = id;
        this.p1 = p1;
        this.p2 = p2;
        this.p3 = p3;
        this.p4 = p4;
        this.p5 = p5;
        this.aliasP6 = aliasP6;
        this.p7 = p7;
        this.p8 = p8;
        this.p9 = p9;
    }

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public Integer getP2() {
        return p2;
    }

    public void setP2(Integer p2) {
        this.p2 = p2;
    }

    public Long getP3() {
        return p3;
    }

    public void setP3(Long p3) {
        this.p3 = p3;
    }

    public Float getP4() {
        return p4;
    }

    public void setP4(Float p4) {
        this.p4 = p4;
    }

    public Double getP5() {
        return p5;
    }

    public void setP5(Double p5) {
        this.p5 = p5;
    }

    public Date getAliasP6() {
        return aliasP6;
    }

    public void setAliasP6(Date aliasP6) {
        this.aliasP6 = aliasP6;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    public BigDecimal getP8() {
        return p8;
    }

    public void setP8(BigDecimal p8) {
        this.p8 = p8;
    }

    public String getP1Hit() {
        return p1Hit;
    }

    public void setP1Hit(String p1Hit) {
        this.p1Hit = p1Hit;
    }

    public Integer[] getP9() {
        return p9;
    }

    public void setP9(Integer[] p9) {
        this.p9 = p9;
    }
}
