package com.github.wz2cool.elasticsearch.test.query;

import com.github.wz2cool.elasticsearch.helper.JSON;
import com.github.wz2cool.elasticsearch.model.FilterMode;
import com.github.wz2cool.elasticsearch.model.QueryMode;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.test.TestApplication;
import com.github.wz2cool.elasticsearch.test.dao.TestExampleEsDAO;
import com.github.wz2cool.elasticsearch.test.model.TestExampleES;
import org.apache.commons.lang3.ArrayUtils;
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

import static org.junit.Assert.*;

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
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getP2, 3));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(Integer.valueOf(3), testExampleES.get(0).getP2());
    }

    @Test
    public void testTermLong() {
        Long target = 80L;
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getP3, target));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(target, testExampleES.get(0).getP3());
    }

    @Test
    public void testTermFloat() {
        Float target = 10.1f;
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getP4, target));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(target, testExampleES.get(0).getP4());
    }

    @Test
    public void testTermDouble() {
        Double target = 17.1d;
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getP5, target));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(target, testExampleES.get(0).getP5());
    }

    @Test
    public void testTermDate() {
        Date target = Date.valueOf("2020-09-10");
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getAliasP6, target));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(target.getTime(), testExampleES.get(0).getAliasP6().getTime());
    }

    @Test
    public void testTermString() {
        String target = "Zorina";
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getP7, target));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(target, testExampleES.get(0).getP7());
    }

    @Test
    public void testTermBigDecimal() {
        BigDecimal target = BigDecimal.valueOf(1.2);
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.term(TestExampleES::getP8, target));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertEquals(target, testExampleES.get(0).getP8());
    }

    @Test
    public void testTermArray() {
        Integer target = 26;
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .highlightMapping(TestExampleES::getP1, TestExampleES::setP1Hit)
                .and(x -> x.term(TestExampleES::getP9, 1));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertTrue(ArrayUtils.contains(testExampleES.get(0).getP9(), target));
    }

    @Test
    public void testRangeInteger() {
        Integer from = 5;
        Integer to = 10;
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.range(TestExampleES::getP2).gt(from).lt(to));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertFalse(testExampleES.isEmpty());
        for (TestExampleES testExample : testExampleES) {
            boolean valid = testExample.getP2() > from && testExample.getP2() < to;
            assertTrue(valid);
        }
    }

    @Test
    public void testRangeBigDecimal() {
        BigDecimal from = BigDecimal.valueOf(1.5);
        BigDecimal to = BigDecimal.valueOf(2.1);
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.range(TestExampleES::getP8).gt(from).lt(to));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertFalse(testExampleES.isEmpty());
        for (TestExampleES testExample : testExampleES) {
            boolean valid = testExample.getP8().compareTo(from) > 0 && testExample.getP8().compareTo(to) < 0;
            assertTrue(valid);
        }
    }

    @Test
    public void testRangeDate() {
        Date from = Date.valueOf("2020-12-22");
        Date to = Date.valueOf("2021-02-21");
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.range(TestExampleES::getAliasP6).gt(from).lt(to));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertFalse(testExampleES.isEmpty());
        for (TestExampleES testExample : testExampleES) {
            boolean valid = testExample.getAliasP6().compareTo(from) > 0 && testExample.getAliasP6().compareTo(to) < 0;
            assertTrue(valid);
        }
    }

    @Test
    public void testMatch() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .highlightMapping(TestExampleES::getP1, TestExampleES::setP1Hit)
                .and(x -> x.match(TestExampleES::getP1, "aachurch1"));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        System.out.println(JSON.toJSONString(testExampleES));
        assertEquals("aachurch1@wix.com", testExampleES.get(0).getP1());
    }

    @Test
    public void testMutliMatch() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .highlightMapping(TestExampleES::getP1, TestExampleES::setP1Hit)
                .and(x -> x.multiMatch("aachurch1").field(TestExampleES::getP1));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        System.out.println(JSON.toJSONString(testExampleES));
        assertEquals("aachurch1@wix.com", testExampleES.get(0).getP1());
    }

    @Test
    public void testAnd() {
        BigDecimal from = BigDecimal.valueOf(1.5);
        BigDecimal to = BigDecimal.valueOf(2.1);
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .and(x -> x.range(TestExampleES::getP8).gt(from))
                .and(x -> x.range(TestExampleES::getP8).lt(to));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertFalse(testExampleES.isEmpty());
        for (TestExampleES testExample : testExampleES) {
            boolean valid = testExample.getP8().compareTo(from) > 0 && testExample.getP8().compareTo(to) < 0;
            assertTrue(valid);
        }
    }

    @Test
    public void testOr() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.QUERY)
                .highlightMapping(TestExampleES::getP1, TestExampleES::setP1Hit)
                .or(x -> x.match(TestExampleES::getP1, "aachurch1"))
                .or(x -> x.match(TestExampleES::getP1, "fhurler0"))
                .or(x -> x.wildcard(TestExampleES::getP1, "*a*"))
                .orderBy(TestExampleES::getId, SortOrder.DESC);
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        System.out.println(JSON.toJSONString(testExampleES));
        assertEquals("aachurch1@wix.com", testExampleES.get(0).getP1());
        assertEquals("fhurler0@miibeian.gov.cn", testExampleES.get(1).getP1());
    }

    @Test
    public void testGroup() {
        BigDecimal from = BigDecimal.valueOf(1.5);
        BigDecimal to = BigDecimal.valueOf(2.1);
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class, QueryMode.FILTER)
                .andGroup(g -> g
                        .and(x -> x.range(TestExampleES::getP8).gt(from))
                        .and(x -> x.range(TestExampleES::getP8).lt(to)));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        assertFalse(testExampleES.isEmpty());
        for (TestExampleES testExample : testExampleES) {
            boolean valid = testExample.getP8().compareTo(from) > 0 && testExample.getP8().compareTo(to) < 0;
            assertTrue(valid);
        }
    }

    @Test
    public void testTermsInteger() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(x -> x.terms(TestExampleES::getP9, 1, 10));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        System.out.println(JSON.toJSONString(testExampleES));
        assertFalse(testExampleES.isEmpty());

        for (TestExampleES testExample : testExampleES) {
            boolean valid = ArrayUtils.contains(testExample.getP9(), 1) || ArrayUtils.contains(testExample.getP9(), 10);
            assertTrue(valid);
        }
    }

    @Test
    public void testFilterMode() {
        DynamicQuery<TestExampleES> query = DynamicQuery.createQuery(TestExampleES.class)
                .and(FilterMode.FILTER, x -> x.terms(TestExampleES::getP9, 1, 10))
                .and(x -> x.terms(TestExampleES::getP9, 1, 2, 3, 4));
        final List<TestExampleES> testExampleES = testExampleEsDAO.selectByDynamicQuery(query);
        System.out.println(JSON.toJSONString(testExampleES));
        assertFalse(testExampleES.isEmpty());

        for (TestExampleES testExample : testExampleES) {
            boolean valid = ArrayUtils.contains(testExample.getP9(), 1) || ArrayUtils.contains(testExample.getP9(), 10);
            assertTrue(valid);
        }
    }
}
