package com.github.wz2cool.elasticsearch.test.dao;

import com.github.wz2cool.elasticsearch.test.mapper.BondInfoEsMapper;
import com.github.wz2cool.elasticsearch.test.model.BondInfoES;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository
public class BondInfoEsDAO {
    @Resource
    private BondInfoEsMapper bondInfoEsMapper;

    public void save(List<BondInfoES> bondInfoESList) {
        bondInfoEsMapper.saveAll(bondInfoESList);
    }
}
