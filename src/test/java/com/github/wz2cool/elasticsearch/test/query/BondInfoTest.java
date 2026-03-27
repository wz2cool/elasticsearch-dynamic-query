package com.github.wz2cool.elasticsearch.test.query;


import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.function.FilterOperators;
import com.github.wz2cool.elasticsearch.query.function.FunctionBoostMode;
import com.github.wz2cool.elasticsearch.query.function.FunctionScoreMode;
import com.github.wz2cool.elasticsearch.test.TestApplication;
import com.github.wz2cool.elasticsearch.test.dao.BondInfoEsDAO;
import com.github.wz2cool.elasticsearch.test.mapper.BondInfoEsMapper;
import com.github.wz2cool.elasticsearch.test.model.BondInfoES;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class BondInfoTest {
    private static final int MIDDLE_MATCH_BOOST = 100;

    private static final int HIGH_MATCH_BOOST = 500;
    private static final String MINIMUM_SHOULD_MATCH = "100%";

    @Resource
    private BondInfoEsDAO bondInfoEsDAO;

    @Resource
    private BondInfoEsMapper bondInfoEsMapper;
    @PostConstruct
    public void init() {
        mockData();
    }

    private void mockData() {
        List<BondInfoES> data = new ArrayList<>();

        BondInfoES bond1 = new BondInfoES();
        bond1.setBondCode("000001");
        bond1.setBondUniCode((Long.parseLong(bond1.getBondCode())));
        bond1.setBondFullName("万科企业股份有限公司2020年公司债券");
        bond1.setRemainingTenorSort(2);
        data.add(bond1);

        BondInfoES bond2 = new BondInfoES();
        bond2.setBondCode("000002");
        bond2.setBondUniCode((Long.parseLong(bond2.getBondCode())));
        bond2.setBondFullName("万科企业股份有限公司2021年绿色债券");
        bond2.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond2);

        BondInfoES bond3 = new BondInfoES();
        bond3.setBondCode("000003");
        bond3.setBondUniCode((Long.parseLong(bond3.getBondCode())));
        bond3.setBondFullName("万科企业集团股份有限公司2019年可交换债券");
        bond3.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond3);

        BondInfoES bond4 = new BondInfoES();
        bond4.setBondCode("000004");
        bond4.setBondFullName("万科企业股份有限公司2022年中期票据");
        bond4.setBondUniCode((Long.parseLong(bond4.getBondCode())));
        bond4.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond4);

        BondInfoES bond5 = new BondInfoES();
        bond5.setBondCode("000005");
        bond5.setBondFullName("万科企业集团股份有限公司2020年企业债");
        bond5.setBondUniCode((Long.parseLong(bond5.getBondCode())));
        bond5.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond5);

        BondInfoES bond6 = new BondInfoES();
        bond6.setBondCode("000006");
        bond6.setBondFullName("万科企业股份有限公司2021年公司债券");
        bond6.setBondUniCode((Long.parseLong(bond6.getBondCode())));
        bond6.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond6);

        BondInfoES bond7 = new BondInfoES();
        bond7.setBondCode("000007");
        bond7.setBondUniCode((Long.parseLong(bond7.getBondCode())));
        bond7.setBondFullName("万科企业集团股份有限公司2018年绿色债券");
        bond7.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond7);

        BondInfoES bond8 = new BondInfoES();
        bond8.setBondCode("000008");
        bond8.setBondUniCode((Long.parseLong(bond8.getBondCode())));
        bond8.setBondFullName("万科企业股份有限公司2022年可转债");
        bond8.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond8);

        BondInfoES bond9 = new BondInfoES();
        bond9.setBondCode("000009");
        bond9.setBondUniCode((Long.parseLong(bond9.getBondCode())));
        bond9.setBondFullName("万科企业集团股份有限公司2019年公司债券");
        bond9.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond9);

        BondInfoES bond10 = new BondInfoES();
        bond10.setBondCode("000010");
        bond10.setBondUniCode((Long.parseLong(bond10.getBondCode())));
        bond10.setBondFullName("万科企业股份有限公司2023年企业债");
        bond10.setRemainingTenorSort(Integer.MAX_VALUE);
        data.add(bond10);

        bondInfoEsDAO.save(data);

    }

    @Test
    public void testWeightFunction() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .weightFunction(2.0)
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        assertEquals(10, list.size());
    }

    @Test
    public void testWeightFunctionWithTrue() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .weightFunction(true,2.0)
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        assertEquals(10, list.size());
    }

    @Test
    public void testWeightFunctionWithFalse() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .weightFunction(false,2.0)
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        assertEquals(10, list.size());
    }

    @Test
    public void testFieldValueFactorFunction() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .fieldValueFactorFunction(BondInfoES::getRemainingTenorSort,1.0f) // 基于remainingTenorSort字段的值因子函数
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        // 验证查询返回结果
        assertEquals(10, list.size());
    }

    @Test
    public void testFieldValueFactorFunctionWithModifier() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .fieldValueFactorFunction(true,BondInfoES::getRemainingTenorSort, 1.2)
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        // 验证查询返回结果
        assertEquals(10, list.size());
    }

    @Test
    public void testRandomFunction() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .randomFunction(true)
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        // 验证查询返回结果
        assertEquals(10, list.size());
    }

    @Test
    public void testMultipleFunctionScores() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                // 组合多个函数分数
                .weightFunction(1.5)
                .fieldValueFactorFunction(BondInfoES::getRemainingTenorSort, 0.5)
                .boostMode(FunctionBoostMode.SUM)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC);

        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 10);

        // 验证查询返回结果
        assertEquals(10, list.size());
    }
    @Test
    public void testFunctionScore() {
        String searchKeyWord = "万科";
        DynamicQuery<BondInfoES> esDynamicQuery = DynamicQuery.createQuery(BondInfoES.class)
                .select(BondInfoES::getBondCode,
                        BondInfoES::getBondFullName,
                        BondInfoES::getRemainingTenorSort)
                .and(searchKeyWord, o -> o.multiMatch().minimumShouldMatch(MINIMUM_SHOULD_MATCH)
                        .field(BondInfoES::getBondCode, HIGH_MATCH_BOOST)
                        .field(BondInfoES::getBondFullName, MIDDLE_MATCH_BOOST))
                .weightFunction(BondInfoES::getRemainingTenorSort, FilterOperators.LESS_THAN, Integer.MAX_VALUE - 1, 1.5)
                .boostMode(FunctionBoostMode.MULTIPLY)
                .scoreMode(FunctionScoreMode.SUM)
                .orderByScore(SortOrder.DESC)
                .orderBy(BondInfoES::getRemainingTenorSort, SortOrder.ASC);
        esDynamicQuery.scoreMapping(BondInfoES::setScore);
        List<BondInfoES> list = bondInfoEsMapper.selectByDynamicQuery(esDynamicQuery, 0, 5);
        assertEquals(1, list.size());
    }
}
