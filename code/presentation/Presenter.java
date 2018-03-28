package presentation;

import business.*;
import dataAccess.entity.*;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Vector;

class Presenter {

    private final SessionData sessionData;

    private final AccountService accountService;
    private final ProfileService profileService;
    private final StudentService studentService;
    private final AdminService adminService;
    private final EnrollmentService enrollmentService;
    private final CourseService courseService;

    private LoginGUI loginGUI;
    private AccountGUI accountGUI;
    private ProfileGUI profileGUI;
    private final SelectStudentGUI ssGUI;
    private EnrollmentGUI enrollmentGUI;

    private Presenter() {
        sessionData = new SessionData();

        accountService = new AccountService();
        profileService = new ProfileService();
        studentService = new StudentService();
        adminService = new AdminService();
        enrollmentService = new EnrollmentService();
        courseService = new CourseService();

        loginGUI = new LoginGUI(this);
        loginGUI.setVisible(true);
        ssGUI = new SelectStudentGUI(this);
    }

    public static void main(String[] args) {
        Presenter presenter = new Presenter();
    }

    public void attemptLogin(String username, char[] password) {
        Account account;
        try {
            account = accountService.validateAccount(username, String.valueOf(password));
        } catch (AccountException e) {
            loginGUI.setError(true, e.getMessage());
            return;
        }
        sessionData.setCurrentAccount(account);
        loginGUI.setError(false, "");
        loginGUI.dispose();
        accountGUI = new AccountGUI(this);
        accountGUI.setVisible(true);
    }

    public void logOut() {
        sessionData.setCurrentAccount(null);
        accountGUI.dispose();
        loginGUI = new LoginGUI(this);
        loginGUI.setVisible(true);
    }

    public String getUsername() {
        if (sessionData.getCurrentAccount() == null) return "";
        return sessionData.getCurrentAccount().getUsername();
    }

    public boolean validatePassword(char[] password) {
        return sessionData.getCurrentAccount().getPassword().equals(String.valueOf(password));
    }

    public boolean updatePassword(char[] newPass) {
        Account acc;
        try {
            acc = accountService.updatePassword(sessionData.getCurrentAccount(), String.valueOf(newPass));
        } catch (AccountException e) {
            accountGUI.setPassError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentAccount(acc);
        accountGUI.setPassError(false, "");
        return true;
    }

    public boolean updateEmail(String newM) {
        Account acc;
        try {
            acc = accountService.updateEmail(sessionData.getCurrentAccount(), newM);
        } catch (AccountException e) {
            accountGUI.setMailError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentAccount(acc);
        accountGUI.setMailError(false, "");
        return true;
    }

    public boolean updateName(String newN, boolean admin) {
        Profile profile;
        try {
            if (admin)
                profile = adminService.updateName(sessionData.getCurrentAdminProfile(), newN);
            else
                profile = studentService.updateName(sessionData.getCurrentStudentProfile(), newN);
        } catch (ProfileException e) {
            profileGUI.setError(true, e.getMessage());
            return false;
        }
        if (admin) sessionData.setCurrentAdminProfile((AdminProfile) profile);
        else sessionData.setCurrentStudentProfile((StudentProfile) profile);
        profileGUI.setError(false, "");
        return true;
    }

    public boolean updateAddress(String addr, boolean admin) {
        Profile profile;
        try {
            if (admin)
                profile = adminService.updateAddress(sessionData.getCurrentAdminProfile(), addr);
            else
                profile = studentService.updateAddress(sessionData.getCurrentStudentProfile(), addr);
        } catch (ProfileException e) {
            profileGUI.setError(true, e.getMessage());
            return false;
        }
        if (admin) sessionData.setCurrentAdminProfile((AdminProfile) profile);
        else sessionData.setCurrentStudentProfile((StudentProfile) profile);
        profileGUI.setError(false, "");
        return true;
    }

    public boolean updateYear(int year) {
        StudentProfile profile;
        try {
            profile = studentService.updateStudentYear(sessionData.getCurrentStudentProfile(), year);
        } catch (ProfileException e) {
            profileGUI.setError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentStudentProfile(profile);
        profileGUI.setError(false, "");
        return true;
    }

    public boolean updateGroup(int group) {
        StudentProfile profile;
        try {
            profile = studentService.updateStudentGroup(sessionData.getCurrentStudentProfile(), group);
        } catch (ProfileException e) {
            profileGUI.setError(true, e.getMessage());
            return false;
        }
        sessionData.setCurrentStudentProfile(profile);
        profileGUI.setError(false, "");
        return true;
    }

    public void showProfile() {
        accountGUI.setVisible(false);
        profileGUI = new ProfileGUI(this);
        profileGUI.setVisible(true);
        String title = "Administrator ";
        Profile profile;
        try {
            sessionData.setAdmin(true);
            profile = profileService.getAdmin(sessionData.getCurrentAccount().getId());
            sessionData.setCurrentAdminProfile((AdminProfile) profile);
            if (profile == null) {
                title = "Student ";
                sessionData.setAdmin(false);
                profile = profileService.getStudent(sessionData.getCurrentAccount().getId());
                sessionData.setCurrentStudentProfile((StudentProfile) profile);
            }
        } catch (ProfileException e) {
            profileGUI.setTitleL(e.getMessage());
            return;
        }
        profileGUI.setTitleL(title + profile.getName());
        if (sessionData.isAdmin()) {
            profileGUI.displayAdminButtons();
        } else {
            profileGUI.displayStudentButtons();
        }
        profileGUI.displayProfileData(profile.toString());
    }

    public void showAccount() {
        profileGUI.dispose();
        sessionData.setCurrentAdminProfile(null);
        sessionData.setCurrentStudentProfile(null);
        accountGUI.setVisible(true);
    }

    public void openSSWindow(boolean reqE) {
        profileGUI.setVisible(false);
        ssGUI.forEnrollments(reqE);
        ssGUI.setVisible(true);
    }

    public void backToProfileWindow() {
        profileGUI.setVisible(true);
        ssGUI.setVisible(false);
    }

    public Vector<StudentProfile> getStudentsVector() {
        try {
            return studentService.getStudentVector();
        } catch (ProfileException e) {
            return null;
        }
    }

    public List<Enrollment> getStudentEnrollments() {
        try {
            return enrollmentService.getEnrollmentsForStudent(sessionData.getCurrentStudentProfile());
        } catch (EnrollmentException e) {
            return null;
        }
    }

    public List<Course> getAvailableCourses() {
        try {
            return courseService.getAvailableCoursesForStudent(sessionData.getCurrentStudentProfile());
        } catch (CourseException e) {
            System.out.println(e);
            return null;
        }
    }

    public void studentSelectedForChange(StudentProfile student) {
        profileGUI.setVisible(true);
        profileGUI.showStudentEditPnl();
        sessionData.setCurrentStudentProfile(student);
    }

    public void studentSelectedForEnroll(StudentProfile student) {
        enrollmentGUI = new EnrollmentGUI(this);
        enrollmentGUI.setVisible(true);
        sessionData.setCurrentStudentProfile(student);
        profileGUI.setVisible(false);
        enrollmentGUI.setTitleL("Enrollments of " + sessionData.getCurrentStudentProfile().getName());
        enrollmentGUI.updateTables();
        enrollmentGUI.studentView(false);
    }

    public void showEnrollments() {
        if (sessionData.isAdmin()) {
            openSSWindow(true);
        } else {
            profileGUI.setVisible(false);
            enrollmentGUI = new EnrollmentGUI(this);
            enrollmentGUI.setVisible(true);
            enrollmentGUI.studentView(true);
            System.out.println("Enrollments of " + sessionData.getCurrentStudentProfile().getName());
            enrollmentGUI.setTitleL("Enrollments of " + sessionData.getCurrentStudentProfile().getName());
            enrollmentGUI.updateTables();
        }
    }

    public boolean addEnrollment(Course course) {
        try {
            enrollmentService.createEnrollment(sessionData.getCurrentStudentProfile(), course);
        } catch (EnrollmentException e) {
            enrollmentGUI.setError(true, e.getMessage());
            return false;
        }
        enrollmentGUI.setError(false, "");
        return true;
    }

    public boolean deleteEnrollment(Enrollment en) {
        try {
            enrollmentService.deleteEnrollment(en);
        } catch (EnrollmentException e) {
            enrollmentGUI.setError(true, e.getMessage());
            return false;
        }
        enrollmentGUI.setError(false, "");
        return true;
    }

    public boolean updateGrade(Enrollment en, double grade) {
        try {
            enrollmentService.updateGrade(en, grade);
        } catch (EnrollmentException e) {
            enrollmentGUI.setError(true, e.getMessage());
            return false;
        }
        enrollmentGUI.setError(false, "");
        return true;
    }

    public File getReport(LocalDate d1, LocalDate d2) {
        File report;
        if (d1.isAfter(d2)) {
            enrollmentGUI.setError(true, "First date must be before second date");
            return null;
        }
        try {
            report = studentService.createReport(sessionData.getCurrentStudentProfile(), d1, d2);
        } catch (ProfileException e) {
            enrollmentGUI.setError(true, e.getMessage());
            return null;
        }
        return report;
    }

}
