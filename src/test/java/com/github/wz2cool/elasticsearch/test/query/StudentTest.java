package com.github.wz2cool.elasticsearch.test.query;

import com.github.wz2cool.elasticsearch.query.DynamicQuery;
import com.github.wz2cool.elasticsearch.test.TestApplication;
import com.github.wz2cool.elasticsearch.test.dao.StudentEsDAO;
import com.github.wz2cool.elasticsearch.test.model.ClassroomES;
import com.github.wz2cool.elasticsearch.test.model.StudentES;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

import static com.github.wz2cool.elasticsearch.helper.BuilderHelper.asc;
import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestApplication.class)
public class StudentTest {

    @Resource
    private StudentEsDAO studentEsDAO;

    @Before
    public void init() {

        mockData();
    }

    private void mockData() {
        List<StudentES> data = new ArrayList<>();

        ClassroomES classroomES1 = new ClassroomES();
        classroomES1.setId(1L);
        classroomES1.setName("classroom1");

        ClassroomES classroomES2 = new ClassroomES();
        classroomES2.setId(2L);
        classroomES2.setName("classroom2");

        for (int i = 1; i < 100; i++) {
            StudentES studentES = new StudentES();
            studentES.setId((long) i);
            studentES.setName("student" + i);
            studentES.setAge(10);
            if (i % 2 == 0) {
                studentES.setClassroom(classroomES1);
            } else {
                studentES.setClassroom(classroomES2);
            }
            data.add(studentES);
        }
        studentEsDAO.save(data.toArray(new StudentES[0]));
    }

    @Test
    public void testObject() {
        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .andNot("", o-> o.multiMatch(StudentES::getName))
                .and(StudentES::getClassroom, ClassroomES::getId, o -> o.term(1L));
        final List<StudentES> studentESList = studentEsDAO.selectByDynamicQuery(query);
        assertTrue(studentESList.size() > 0);
        for (StudentES studentES : studentESList) {
            assertEquals(Long.valueOf(1), studentES.getClassroom().getId());
        }
    }

    @Test
    public void testNested() {
        DynamicQuery<StudentES> query = DynamicQuery.createQuery(StudentES.class)
                .highlightMapping(StudentES::getName, StudentES::setNameHit)
                .highlightMapping(StudentES::getNameWide, StudentES::setNameWideHit)
                .and(StudentES::getName, o -> o.term("student1"))
                .and("student1", o -> o.multiMatch(StudentES::getName, StudentES::getNameWide))
                .orderBy(StudentES::getId, asc());
        final List<StudentES> studentESList = studentEsDAO.selectByDynamicQuery(query);
        assertEquals(1, studentESList.size());
        assertEquals(Long.valueOf(1), studentESList.get(0).getId());
    }
}
