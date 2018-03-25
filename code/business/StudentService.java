package business;

import dataAccess.*;
import entity.Enrollment;
import entity.Profile;
import entity.StudentProfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.List;

public class StudentService {

    public StudentProfile createStudentProfile(String name, int id_card_number, String address, int year, int group) throws ProfileException {
        StudentDAO studentDAO = new StudentDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        StudentProfile newProfile = null;

        if (year<0 || year>6) {
            throw new ProfileException("Invalid year");
        }
        if (group<0) {
            throw new ProfileException("Invalid group");
        }

        try {
            Profile aux = profileDAO.findByIdCardNumber(id_card_number);
            if (aux != null) {
                throw new ProfileException("Profile with given id card number already exists");
            }
            aux = new Profile(0, name, id_card_number, address);
            int newId = studentDAO.insert(new StudentProfile(aux, year, group));
            newProfile = studentDAO.findById(newId);
        } catch (DataAccessException e) {
            throw new ProfileException("Error creating user");
        }

        return newProfile;
    }

    public void deleteStudentProfile(StudentProfile profile) throws ProfileException {
        StudentDAO dao = new StudentDAO();
        try {
            dao.delete(profile.getId());
        } catch (DataAccessException e) {
            throw new ProfileException("Error deleting profile");
        }
    }

    public File createReport(StudentProfile student, LocalDate start, LocalDate end) throws ProfileException {
        File out = new File("report/Report" + student.getName() + ".txt");
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        try (FileOutputStream foStream = new FileOutputStream(out);
             OutputStreamWriter osWriter = new OutputStreamWriter(foStream);
             BufferedWriter writer = new BufferedWriter(osWriter)) {
            writer.write("Report for " + student.getName());
            writer.newLine();
            writer.write(start.toString() + end.toString());
            writer.newLine();
            writer.newLine();
            writer.newLine();
            List<Enrollment> enrollments = enrollmentDAO.findAllForStudent(student);
            for (Enrollment e : enrollments) {
                if (e.getCourse().getStartDate().isAfter(start) && e.getCourse().getStartDate().isBefore(end)) {
                    writer.write("Started course " + e.getCourse().getName() + " at " + e.getCourse().getStartDate());
                    writer.newLine();
                }
                if (e.getCourse().getEndDate().isAfter(start) && e.getCourse().getEndDate().isBefore(end)) {
                    writer.write("Finished course " + e.getCourse().getName() + " at " + e.getCourse().getStartDate());
                    writer.newLine();
                }
                if (e.getCourse().getExam().getDate().isAfter(start) && e.getCourse().getExam().getDate().isBefore(end)) {
                    writer.write("Took exam at " + e.getCourse().getExam().getDate());
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (Exception e) {
            throw new ProfileException("Cannot create report");
        }
        return out;
    }
}
