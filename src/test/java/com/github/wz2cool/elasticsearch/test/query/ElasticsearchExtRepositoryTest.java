package com.github.wz2cool.elasticsearch.test.query;

import com.github.wz2cool.elasticsearch.helper.JSON;
import com.github.wz2cool.elasticsearch.model.LogicPagingResult;
import com.github.wz2cool.elasticsearch.model.UpDown;
import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.query.LogicPagingQuery;
import com.github.wz2cool.elasticsearch.test.TestApplication;
import com.github.wz2cool.elasticsearch.test.dao.StudentEsDAO;
import com.github.wz2cool.elasticsearch.test.model.StudentDTO;
import com.github.wz2cool.elasticsearch.test.model.StudentES;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
@SuppressWarnings("all")
public class ElasticsearchExtRepositoryTest {

    @Resource
    private StudentEsDAO studentEsDAO;
    @Resource
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void init() {

        // saveTestData();
    }

    private void saveTestData() {
        List<StudentES> studentESList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            StudentES studentES = new StudentES();
            studentES.setId((long) i);
            studentES.setName("student" + i);
            studentES.setAge(20);
            studentESList.add(studentES);
        }
        studentEsDAO.save(studentESList.toArray(new StudentES[0]));
    }


    @Test
    public void testLogicPaging() {
        QueryBuilder idQuery = QueryBuilders.rangeQuery("id").lt(77);
        LogicPagingQuery<StudentES> query =
                LogicPagingQuery.createQuery(StudentES.class, StudentES::getId, SortOrder.ASC, UpDown.NONE);

        query.setQueryBuilder(idQuery);

        final LogicPagingResult<StudentES> studentESLogicPagingResult = studentEsDAO.selectByLogicPaging(query);
        System.out.println(JSON.toJSONString(studentESLogicPagingResult));
    }

    @Test
    public void testLogicPaging2() {
        LogicPagingQuery<StudentES> query =
                LogicPagingQuery.createQuery(StudentES.class, StudentES::getId, SortOrder.ASC, UpDown.NONE)
                        .or(b -> b.range(StudentES::getId).gt(3L).lt(6L))
                        .or(b -> b.range(StudentES::getId).gt(10L).lt(15L))
                        .scoreMapping(StudentES::setScore)
                        .highlightMapping(StudentES::getName, StudentES::setNameHit);
        final LogicPagingResult<StudentES> studentESLogicPagingResult = studentEsDAO.selectByLogicPaging(query);
        System.out.println(JSON.toJSONString(studentESLogicPagingResult));
    }

    @Test
    public void testLogicPaging3() {
        LogicPagingQuery<StudentES> query =
                LogicPagingQuery.createQuery(StudentES.class, StudentES::getId, SortOrder.ASC, UpDown.NONE)
                        .and(false, b -> b.multiMatch("aaa", StudentES::getName, StudentES::getNameHit)
                                .minimumShouldMatch("100%"))
                        .andGroup(g -> g
                                .and(b -> b.range(StudentES::getId).gt(3L))
                                .and(b -> b.range(StudentES::getId).lt(6L)))
                        .scoreMapping(StudentES::setScore)
                        .highlightMapping(StudentES::getName, StudentES::setNameHit);
        final LogicPagingResult<StudentDTO> studentESLogicPagingResult = studentEsDAO.selectByLogicPaging(query)
                .convert(x -> {
                    StudentDTO dto = new StudentDTO();
                    dto.setAge(x.getAge());
                    dto.setId(x.getId());
                    dto.setName(x.getName());
                    dto.setNameHit(x.getNameHit());
                    dto.setScore(x.getScore());
                    return dto;
                });
        System.out.println(JSON.toJSONString(studentESLogicPagingResult));
    }

    @Test
    public void testDelete() {
        Long id = 999999L;
        StudentES studentES = new StudentES();
        studentES.setId(id);
        studentES.setName("student");
        studentES.setAge(20);
        studentEsDAO.save(studentES);

        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .and(x -> x.term(StudentES::getId, id));
        final List<StudentES> studentES1 = studentEsDAO.selectByDynamicQuery(query);
        assertEquals(id, studentES1.get(0).getId());
        // call method
        studentEsDAO.deleteByDynamicQuery(query);

        final List<StudentES> studentES2 = studentEsDAO.selectByDynamicQuery(query);
        assertTrue(studentES2.isEmpty());
    }

    @Test
    public void testMultiDeleteById() {
        Long id = 999999L;
        StudentES studentES = new StudentES();
        studentES.setId(id);
        studentES.setName("student");
        studentES.setAge(20);

        Long id2 = 888888L;
        StudentES studentES2 = new StudentES();
        studentES2.setId(id2);
        studentES2.setName("student");
        studentES2.setAge(20);
        studentEsDAO.save(studentES, studentES2);

        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .and(x -> x.terms(StudentES::getId, id, id2));
        final List<StudentES> studentESList1 = studentEsDAO.selectByDynamicQuery(query);
        assertEquals(2, studentESList1.size());
        // call method
        studentEsDAO.deleteByDynamicQuery(query);

        final List<StudentES> studentESList2 = studentEsDAO.selectByDynamicQuery(query);
        assertTrue(studentESList2.isEmpty());
    }

    @Test
    public void testLargeDeleteById() {
        List<StudentES> studentList = new ArrayList<>();
        List<Long> ids = new ArrayList<>();
        for (long i = 100000000; i < 100000100; i++) {
            ids.add(i);
            StudentES studentES = new StudentES();
            studentES.setId(i);
            studentES.setName("testDeleteStudent" + i);
            studentES.setAge(30);
            studentList.add(studentES);
        }
        studentEsDAO.save(studentList.toArray(new StudentES[0]));
        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .and(x -> x.terms(StudentES::getId, ids.toArray(new Long[0])));

        final List<StudentES> studentESList1 = studentEsDAO.selectByDynamicQuery(query);
        assertEquals(100, studentESList1.size());
        // call method
        studentEsDAO.deleteByDynamicQuery(query);
        final List<StudentES> studentESList2 = studentEsDAO.selectByDynamicQuery(query);
        assertTrue(studentESList2.isEmpty());
    }

}
