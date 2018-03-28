package presentation;

import dataAccess.entity.Account;
import dataAccess.entity.AdminProfile;
import dataAccess.entity.StudentProfile;

class SessionData {

    private Account account;
    private StudentProfile studentProfile;
    private AdminProfile adminProfile;
    private boolean admin;

    public SessionData() {
        account = null;
    }

    public Account getCurrentAccount() {
        return account;
    }

    public void setCurrentAccount(Account acc) {
        this.account = acc;
    }

    public StudentProfile getCurrentStudentProfile() {return studentProfile;}

    public void setCurrentStudentProfile(StudentProfile profile) {this.studentProfile = profile;}

    public AdminProfile getCurrentAdminProfile() {return adminProfile;}

    public void setCurrentAdminProfile(AdminProfile profile) {this.adminProfile = profile;}

    public boolean isAdmin() { return admin;}

    public void setAdmin(boolean admin) {
        this.admin=admin;
    }
}
