package dataAccess.dao;

public class DAOFactory {

    private DAOFactory() {
    }

    public static EntityDAO getDAO(Type type) {
        EntityDAO dao = null;
        switch (type) {
            case ACCOUNT:
                dao = new AccountDAO();
                break;
            case ADMIN:
                dao = new AdminDAO();
                break;
            case COURSE:
                dao = new CourseDAO();
                break;
            case ENROLLMENT:
                dao = new EnrollmentDAO();
                break;
            case EXAM:
                dao = new ExamDAO();
                break;
            case STUDENT:
                dao = new StudentDAO();
                break;
            case USER:
                dao = new UserDAO();
        }
        return dao;
    }

    public enum Type {
        ACCOUNT, ADMIN, COURSE, ENROLLMENT, EXAM, STUDENT, USER
    }

}
