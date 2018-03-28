package business;

import dataAccess.CourseDAO;
import dataAccess.DataAccessException;
import dataAccess.EnrollmentDAO;
import dataAccess.StudentDAO;
import dataAccess.entity.Course;
import dataAccess.entity.Enrollment;
import dataAccess.entity.StudentProfile;

import java.util.List;

public class EnrollmentService {

    public void createEnrollment(StudentProfile student, Course course) throws EnrollmentException {
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        Enrollment enrollment;
        try {
            StudentProfile s = studentDAO.findById(student.getId());
            if (s == null) {
                throw new EnrollmentException("Invalid student");
            }
            Course c = courseDAO.findById(course.getId());
            if (c == null) {
                throw new EnrollmentException("Invalid course");
            }
            int newId = enrollmentDAO.insert(new Enrollment(0, s, c, 0.0));
            enrollment = enrollmentDAO.findById(newId);
        } catch (DataAccessException e) {
            throw new EnrollmentException("Error creating enrollment");
        }

    }

    public List<Enrollment> getEnrollments() throws EnrollmentException {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<Enrollment> enrollments;
        try {
            enrollments = enrollmentDAO.findAll();
        } catch (DataAccessException e) {
            throw new EnrollmentException("Error accessing enrollments");
        }
        return enrollments;
    }

    public List<Enrollment> getEnrollmentsForStudent(StudentProfile student) throws EnrollmentException {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        List<Enrollment> enrollments;
        try {
            enrollments = enrollmentDAO.findAllForStudent(student);
        } catch (DataAccessException e) {
            throw new EnrollmentException("Error accessing enrollments");
        }
        return enrollments;
    }

    public void updateGrade(Enrollment enrollment, double newGrade) throws EnrollmentException {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        if (newGrade < 1.0 || newGrade > 10.0) {
            throw new EnrollmentException("Invalid grade");
        }
        Enrollment updatedEnrollment = new Enrollment(enrollment.getId(), enrollment.getStudentProfile(), enrollment.getCourse(), newGrade);
        try {
            enrollmentDAO.update(updatedEnrollment);
        } catch (DataAccessException e) {
            throw new EnrollmentException("Error updating enrollment");
        }
    }

    public void deleteEnrollment(Enrollment enr) throws EnrollmentException {
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        try {
            enrollmentDAO.delete(enr.getId());
        } catch (DataAccessException e) {
            throw new EnrollmentException("Error deleting enrollment");
        }
    }


}
