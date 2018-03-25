package business;

import dataAccess.CourseDAO;
import dataAccess.DataAccessException;
import dataAccess.ExamDAO;
import entity.Course;
import entity.Exam;
import entity.StudentProfile;

import java.util.List;

public class CourseService {

    public List<Course> getCourses() throws CourseException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses = null;
        try {
            courses = courseDAO.findAll();
        } catch (DataAccessException e) {
            throw new CourseException("Error accessing courses");
        }
        return courses;
    }

    public Object[][] toObjectArray(List<Course> courses) {
        Object res[][] = null;
        if (courses != null) {
            res = new Object[courses.size()][];
            for (int i = 0; i < courses.size(); i++) {
                res[i] = convertToArray(courses.get(i));
            }
        }
        return res;
    }

    private static Object[] convertToArray(Course c) {
        Object res[] = { c.getName(), c.getStartDate(), c.getEndDate(), c.getExam().getDate() };
        return res;
    }
}
