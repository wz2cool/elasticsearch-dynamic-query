package com.github.wz2cool.elasticsearch.test.dao;

import com.github.wz2cool.elasticsearch.model.NormPagingResult;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.NormPagingQuery;
import com.github.wz2cool.elasticsearch.test.mapper.TestExampleEsMapper;
import com.github.wz2cool.elasticsearch.test.model.TestExampleES;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class TestExampleEsDAO {

    @Resource
    private TestExampleEsMapper testExampleEsMapper;

    public List<TestExampleES> selectByDynamicQuery(DynamicQuery<TestExampleES> query) {
        return testExampleEsMapper.selectByDynamicQuery(query);
    }

    public void save(List<TestExampleES> testExampleESList) {
        testExampleEsMapper.saveAll(testExampleESList);
    }

    public NormPagingResult<TestExampleES> selectByNormPaging(NormPagingQuery<TestExampleES> query) {
        return testExampleEsMapper.selectByNormalPaging(query);
    }
}
