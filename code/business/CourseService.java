package business;

import dataAccess.CourseDAO;
import dataAccess.DataAccessException;
import dataAccess.entity.Course;
import dataAccess.entity.StudentProfile;

import java.util.List;

public class CourseService {

    public List<Course> getCourses() throws CourseException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses;
        try {
            courses = courseDAO.findAll();
        } catch (DataAccessException e) {
            throw new CourseException("Error accessing courses");
        }
        return courses;
    }

    public List<Course> getAvailableCoursesForStudent(StudentProfile student) throws CourseException {
        CourseDAO courseDAO = new CourseDAO();
        List<Course> courses;
        try {
            courses = courseDAO.findNotEnrolledForStudent(student);
        } catch (DataAccessException e) {
            System.out.println(e);
            throw new CourseException("Error accessing courses");
        }
        return courses;
    }

}
