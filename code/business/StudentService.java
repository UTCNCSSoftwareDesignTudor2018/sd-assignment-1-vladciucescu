package business;

import dataAccess.DataAccessException;
import dataAccess.EnrollmentDAO;
import dataAccess.ProfileDAO;
import dataAccess.StudentDAO;
import dataAccess.entity.Enrollment;
import dataAccess.entity.Profile;
import dataAccess.entity.StudentProfile;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

public class StudentService {

    public StudentProfile createStudentProfile(String name, int id_card_number, String address, int year, int group) throws ProfileException {
        StudentDAO studentDAO = new StudentDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        StudentProfile newProfile;

        if (year < 0 || year > 6) {
            throw new ProfileException("Invalid year");
        }
        if (group < 0) {
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

    public StudentProfile updateStudentGroup(StudentProfile profile, int group) throws ProfileException {
        StudentDAO studentDAO = new StudentDAO();
        StudentProfile updatedProfile;
        if (profile.getGroup() == group) {
            throw new ProfileException("New group must be different from old group");
        }
        if (group < 0) {
            throw new ProfileException("Invalid Group");
        }

        Profile aux = new Profile(profile.getId(), profile.getName(), profile.getId_card_number(), profile.getAddress());
        updatedProfile = new StudentProfile(aux, profile.getYear(), group);
        try {
            studentDAO.update(updatedProfile);
        } catch (DataAccessException e) {
            throw new ProfileException("Error updating profile");
        }

        return updatedProfile;
    }

    public StudentProfile updateStudentYear(StudentProfile profile, int newYr) throws ProfileException {
        StudentDAO studentDAO = new StudentDAO();
        StudentProfile updatedProfile;
        if (profile.getYear() == newYr) {
            throw new ProfileException("New year must be different from old year");
        }

        Profile aux = new Profile(profile.getId(), profile.getName(), profile.getId_card_number(), profile.getAddress());
        updatedProfile = new StudentProfile(aux, newYr, profile.getGroup());
        try {
            studentDAO.update(updatedProfile);
        } catch (DataAccessException e) {
            throw new ProfileException("Error updating profile");
        }

        return updatedProfile;
    }

    public StudentProfile updateName(StudentProfile profile, String newName) throws ProfileException {
        ProfileService profileService = new ProfileService();
        return new StudentProfile(profileService.updateName(profile, newName), profile.getYear(), profile.getGroup());
    }

    public StudentProfile updateAddress(StudentProfile profile, String newAddress) throws ProfileException {
        ProfileService profileService = new ProfileService();
        return new StudentProfile(profileService.updateAddress(profile, newAddress), profile.getYear(), profile.getGroup());
    }

    public File createReport(StudentProfile student, LocalDate start, LocalDate end) throws ProfileException {
        File directory = new File("reports");
        if (!directory.exists()) {
            directory.mkdir();
        }
        File out = new File("reports/Report" + student.getNameNoSpace() + ".txt");
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        try (FileOutputStream foStream = new FileOutputStream(out);
             OutputStreamWriter osWriter = new OutputStreamWriter(foStream);
             BufferedWriter writer = new BufferedWriter(osWriter)) {
            writer.write("Report for " + student.getName());
            writer.newLine();
            writer.write(start.toString() + " - " + end.toString());
            writer.newLine();
            writer.newLine();
            writer.newLine();
            List<Enrollment> enrollments = enrollmentDAO.findAllForStudent(student);
            for (Enrollment e : enrollments) {
                if (e.getCourse().getStartDate().isAfter(start) && e.getCourse().getStartDate().isBefore(end)) {
                    writer.write("Started course " + e.getCourse().getName() + " on " + e.getCourse().getStartDate());
                    writer.newLine();
                }
                if (e.getCourse().getEndDate().isAfter(start) && e.getCourse().getEndDate().isBefore(end)) {
                    writer.write("Finished course " + e.getCourse().getName() + " on " + e.getCourse().getEndDate());
                    writer.newLine();
                }
                if (e.getCourse().getExam().getDate().isAfter(start) && e.getCourse().getExam().getDate().isBefore(end)) {
                    writer.write("Took exam on " + e.getCourse().getExam().getDate() + " at " + e.getCourse().getExam().getTime());
                    writer.newLine();
                }
                writer.newLine();
            }
        } catch (Exception e) {
            throw new ProfileException("Cannot create report");
        }
        return out;
    }

    public Vector<StudentProfile> getStudentVector() throws ProfileException {
        Vector<StudentProfile> students = new Vector<>();
        StudentDAO studentDAO = new StudentDAO();
        try {
            List<StudentProfile> aux = studentDAO.findAll();
            students.addAll(aux);
        } catch (DataAccessException e) {
            throw new ProfileException("Cannot retrieve student list");
        }
        return students;
    }
}
