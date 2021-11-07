package com.github.wz2cool.elasticsearch.test.dao;

import com.github.wz2cool.elasticsearch.test.mapper.TestExampleEsMapper;
import com.github.wz2cool.elasticsearch.test.model.TestExampleES;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TestExampleEsDAO {

    @Resource
    private TestExampleEsMapper testExampleEsMapper;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    public List<TestExampleES> selectByDynamicQuery(DynamicQuery<TestExampleES> query) {
        return testExampleEsMapper.selectByDynamicQuery(elasticsearchTemplate, query);
    }

    public void save(List<TestExampleES> testExampleESList) {
        testExampleEsMapper.saveAll(testExampleESList);
    }
}
