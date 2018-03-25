package business;

import dataAccess.DataAccessException;
import dataAccess.ExamDAO;
import entity.Exam;
import entity.StudentProfile;

import java.util.List;

public class ExamService {

    public List<Exam> getExams(StudentProfile student) throws ExamException {
        ExamDAO examDAO = new ExamDAO();
        List<Exam> exams = null;
        try {
            exams = examDAO.findAllForStudent(student);
        } catch (DataAccessException e) {
            throw new ExamException("Error accessing exams");
        }
        return exams;
    }

    public Object[][] toObjectArray(List<Exam> exams) {
        Object res[][] = null;
        if (exams != null) {
            res = new Object[exams.size()][];
            for (int i = 0; i < exams.size(); i++) {
                res[i] = convertToArray(exams.get(i));
            }
        }
        return res;
    }

    private static Object[] convertToArray(Exam e) {
        Object res[] = { e.getDate(), e.getTime(), e.getWritten() };
        return res;
    }
}
