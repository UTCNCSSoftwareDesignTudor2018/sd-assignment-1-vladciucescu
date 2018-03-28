package testing.dataAccess;

import dataAccess.DataAccessException;
import dataAccess.ExamDAO;
import dataAccess.entity.Exam;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

class ExamDAOTest {

    @Test
    void findTest() throws DataAccessException {

        Exam exam = new Exam(1, LocalDate.of(2018, 10, 3), LocalTime.of(0, 0), true);
        ExamDAO dao = new ExamDAO();
        Exam res = dao.findById(1);
        assert exam.equals(res);
    }

    @Test
    void insertTest() throws DataAccessException {
        Exam exam = new Exam(0, LocalDate.of(2020, 3, 3), LocalTime.of(0, 0), true);
        ExamDAO dao = new ExamDAO();
        int id = dao.insert(exam);
        Exam check = dao.findById(id);
        dao.delete(id);
        assert exam.getDate().equals(check.getDate()) &&
                exam.getTime().equals(check.getTime()) &&
                exam.getWritten().equals(check.getWritten());
    }

    @Test
    void updateTest() throws DataAccessException {
        ExamDAO dao = new ExamDAO();
        Exam orig = new Exam(1, LocalDate.of(2018, 10, 3), LocalTime.of(0, 0), true);
        Exam exam = new Exam(1, LocalDate.of(2020, 11, 4), LocalTime.of(1, 2), false);
        dao.update(exam);
        Exam check = dao.findById(1);
        dao.update(orig);
        assert exam.equals(check);
    }

    @Test
    void delete() throws DataAccessException {
        ExamDAO dao = new ExamDAO();
        Exam exam = new Exam(1, LocalDate.of(2018, 10, 3), LocalTime.of(0, 0), true);
        int id = dao.insert(exam);
        dao.delete(id);
        assert dao.findById(id) == null;
    }
}
