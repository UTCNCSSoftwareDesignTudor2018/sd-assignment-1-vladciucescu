package business;

import dataAccess.DataAccessException;
import dataAccess.ExamDAO;
import dataAccess.entity.Exam;
import dataAccess.entity.StudentProfile;

import java.util.List;

class ExamService {

    private static Object[] convertToArray(Exam e) {
        return new Object[]{e.getDate(), e.getTime(), e.getWritten()};
    }

    public List<Exam> getExams(StudentProfile student) throws ExamException {
        ExamDAO examDAO = new ExamDAO();
        List<Exam> exams;
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
}
