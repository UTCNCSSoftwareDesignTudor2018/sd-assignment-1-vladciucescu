package testing.dataAccess;

import dataAccess.CourseDAO;
import dataAccess.DataAccessException;
import dataAccess.ExamDAO;
import dataAccess.entity.Course;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class CourseDAOTest {

    @Test
    void findTest() throws DataAccessException {

        ExamDAO examDao = new ExamDAO();
        Course course = new Course(4, "Software Engineering", LocalDate.of(2018, 9, 1), LocalDate.of(2019, 1, 1), examDao.findById(1));
        CourseDAO courseDao = new CourseDAO();
        Course res = courseDao.findById(4);

        assert course.equals(res);
    }

    @Test
    void insertTest() throws DataAccessException {

        ExamDAO examDao = new ExamDAO();
        Course course = new Course(4, "Fundamental Algorithms", LocalDate.of(2025, 1, 1), LocalDate.of(2026, 1, 1), examDao.findById(1));
        CourseDAO courseDao = new CourseDAO();
        int id = courseDao.insert(course);
        Course check = courseDao.findById(id);
        courseDao.delete(id);

        assert course.getName().equals(check.getName()) &&
                course.getStartDate().equals(check.getStartDate()) &&
                course.getEndDate().equals(check.getEndDate()) &&
                course.getExam().equals(check.getExam());
    }

    @Test
    void updateTest() throws DataAccessException {
        ExamDAO examDao = new ExamDAO();
        Course orig = new Course(4, "Software Engineering", LocalDate.of(2018, 9, 1), LocalDate.of(2019, 1, 1),  examDao.findById(1));
        Course course = new Course(4, "Software Reverse Engineering", LocalDate.of(2020, 10, 2), LocalDate.of(2020, 11, 3),  examDao.findById(1));
        CourseDAO courseDao = new CourseDAO();
        courseDao.update(course);
        Course check = courseDao.findById(4);
        courseDao.update(orig);

        assert course.equals(check);
    }

    @Test
    void delete() throws DataAccessException {
        ExamDAO examDao = new ExamDAO();
        Course course = new Course(4, "Software Reverse Engineering", LocalDate.of(2020, 10, 2), LocalDate.of(2020, 11, 3),  examDao.findById(1));
        CourseDAO courseDao = new CourseDAO();
        int id = courseDao.insert(course);
        courseDao.delete(id);

        assert courseDao.findById(id) == null;
    }
}
