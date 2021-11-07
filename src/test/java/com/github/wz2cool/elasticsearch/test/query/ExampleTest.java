package com.github.wz2cool.elasticsearch.test.query;

import com.github.wz2cool.elasticsearch.test.TestApplication;
import com.github.wz2cool.elasticsearch.test.model.TestExampleES;
import com.github.wz2cool.elasticsearch.test.dao.TestExampleEsDAO;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import org.apache.commons.lang3.ArrayUtils;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class ExampleTest {

    @Resource
    private TestExampleEsDAO testExampleEsDAO;

    @Before
    public void init() {

        mockData();
    }

    private void mockData() {
        List<TestExampleES> data = new ArrayList<>();
        data.add(new TestExampleES(1L, "fhurler0@miibeian.gov.cn", 1, 10L, 1.1f, 1.1d, Date.valueOf("2020-11-23"), "Heywood", BigDecimal.valueOf(1.1), new Integer[]{1, 2, 3}));
        data.add(new TestExampleES(2L, "aachurch1@wix.com", 2, 20L, 2.1f, 1.2d, Date.valueOf("2021-03-25"), "Dawn", BigDecimal.valueOf(1.2), new Integer[]{4, 5, 6}));
        data.add(new TestExampleES(3L, "flerer2@free.fr", 3, 30L, 3.1f, 1.3d, Date.valueOf("2020-10-05"), "Danni", BigDecimal.valueOf(1.3), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(4L, "jmulroy3@wiley.com", 4, 40L, 4.1f, 1.4d, Date.valueOf("2020-12-23"), "Inez", BigDecimal.valueOf(1.4), new Integer[]{10, 11, 12}));
        data.add(new TestExampleES(5L, "ostathor4@51.la", 5, 50L, 5.1f, 1.5d, Date.valueOf("2021-03-30"), "Charil", BigDecimal.valueOf(1.5), new Integer[]{13, 14, 15}));
        data.add(new TestExampleES(6L, "jcommuzzo5@arizona.edu", 6, 60L, 6.1f, 1.6d, Date.valueOf("2020-11-10"), "Derk", BigDecimal.valueOf(1.6), new Integer[]{16, 17, 18}));
        data.add(new TestExampleES(7L, "tdabbes6@wisc.edu", 7, 70L, 7.1f, 1.7d, Date.valueOf("2020-07-03"), "Stacey", BigDecimal.valueOf(1.7), new Integer[]{19, 20, 21}));
        data.add(new TestExampleES(8L, "sopfer7@google.nl", 8, 80L, 8.1f, 1.8d, Date.valueOf("2020-09-06"), "Petronilla", BigDecimal.valueOf(1.8), new Integer[]{22, 23, 24}));
        data.add(new TestExampleES(9L, "llamburn8@hibu.com", 9, 90L, 9.1f, 1.9d, Date.valueOf("2021-02-20"), "Abigael", BigDecimal.valueOf(1.9), new Integer[]{25, 26, 27}));
        data.add(new TestExampleES(10L, "eleaman9@seesaa.net", 10, 100L, 10.1f, 2.1d, Date.valueOf("2021-01-14"), "Doloritas", BigDecimal.valueOf(2.0), new Integer[]{28, 29, 30}));
        data.add(new TestExampleES(11L, "nstclaira@gmpg.org", 11, 110L, 11.1f, 2.2d, Date.valueOf("2020-09-12"), "Chelsae", BigDecimal.valueOf(2.1), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(12L, "poshavlanb@goodreads.com", 12, 120L, 12.1f, 2.3d, Date.valueOf("2021-03-20"), "Lauree", BigDecimal.valueOf(2.2), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(13L, "gskupinskic@ucoz.ru", 13, 130L, 13.1f, 2.4d, Date.valueOf("2020-07-08"), "Myrta", BigDecimal.valueOf(2.3), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(14L, "sstodartd@unblog.fr", 14, 140L, 14.1f, 2.5d, Date.valueOf("2020-12-10"), "Bert", BigDecimal.valueOf(2.4), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(15L, "cfrankcome@virginia.edu", 15, 150L, 15.1f, 2.6d, Date.valueOf("2020-10-16"), "Zorina", BigDecimal.valueOf(2.5), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(16L, "gokeyg@who.int", 16, 160L, 1.1f, 16.1d, Date.valueOf("2021-04-26"), "Dore", BigDecimal.valueOf(2.6), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(17L, "nabryh@privacy.gov.au", 17, 170L, 17.1f, 17.1d, Date.valueOf("2020-09-10"), "Abbott", BigDecimal.valueOf(2.7), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(19L, "wsimoesi@so-net.ne.jp", 18, 180L, 18.1f, 18.1d, Date.valueOf("2020-08-16"), "Eugenius", BigDecimal.valueOf(2.8), new Integer[]{7, 8, 9}));
        data.add(new TestExampleES(20L, "maltamiranoj@blogs.com", 19, 190L, 19.1f, 19.1d, Date.valueOf("2020-07-09"), "Benetta", BigDecimal.valueOf(2.9), new Integer[]{7, 8, 9}));
        testExampleEsDAO.save(data);
    }

    @Test
    public void testTermInteger() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(TestExampleES::getId, o -> o.term(3L))
                .orderBy(TestExampleES::getId, SortOrder.ASC);
        final QueryBuilder queryBuilder = query.buildQuery();
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(3), testExampleES.get(0).getP2());
    }

    @Test
    public void testArrayTermInteger() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(TestExampleES::getP9, o -> o.term(8))
                .orderBy(TestExampleES::getId, SortOrder.ASC);
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertTrue(testExampleES.size() > 0);
        for (TestExampleES testExample : testExampleES) {
            final int i = ArrayUtils.indexOf(testExample.getP9(), 8);
            assertTrue(i >= 0);
        }
    }

    @Test
    public void testTermsInteger() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(FilterMode.FILTER, TestExampleES::getId, o -> o.terms(3L, 6L, 9L))
                .orderBy(TestExampleES::getId, SortOrder.ASC);
        final QueryBuilder queryBuilder = query.buildQuery();
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(3), testExampleES.get(0).getP2());
        assertEquals(Integer.valueOf(6), testExampleES.get(1).getP2());
        assertEquals(Integer.valueOf(9), testExampleES.get(2).getP2());
    }

    @Test
    public void testRangeInteger() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(TestExampleES::getId, o -> o.gt(1L).lt(3L));
        final QueryBuilder queryBuilder = query.buildQuery();
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(2), testExampleES.get(0).getP2());
    }

    @Test
    public void testGroup() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(FilterMode.FILTER, g -> g
                        .and(FilterMode.FILTER, TestExampleES::getId, o -> o.gt(1L))
                        .and(FilterMode.FILTER, TestExampleES::getId, o -> o.lt(3L)));
        final QueryBuilder queryBuilder = query.buildQuery();
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(2), testExampleES.get(0).getP2());
    }

    @Test
    public void testMultiMatch() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and("jcommuzzo5", o -> o
                        .multiMatch(TestExampleES::getP1, TestExampleES::getP7)
                        .type(MultiMatchQueryBuilder.Type.MOST_FIELDS));
        final QueryBuilder queryBuilder = query.buildQuery();
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(6), testExampleES.get(0).getP2());
    }

    @Test
    public void testOr() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .or(TestExampleES::getId, o -> o.term(1L))
                .or(TestExampleES::getId, o -> o.term(3L))
                .orderBy(TestExampleES::getId, SortOrder.DESC);
        final QueryBuilder queryBuilder = query.buildQuery();
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(3), testExampleES.get(0).getP2());
        assertEquals(Integer.valueOf(1), testExampleES.get(1).getP2());
    }
}
