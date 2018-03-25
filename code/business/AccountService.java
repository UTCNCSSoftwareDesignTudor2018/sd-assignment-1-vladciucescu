package business;

import dataAccess.AccountDAO;
import dataAccess.DataAccessException;
import entity.Account;
import entity.StudentProfile;
public class AccountService {

    public Account validateAccount(String username, String password) throws AccountException {
        AccountDAO accountDAO = new AccountDAO();
        Account account = null;
        try {
            account = accountDAO.findByUsername(username);
            if (account == null) {
                throw new AccountException("Wrong username");
            }
            if (!account.getPassword().equals(password)) {
                throw new AccountException("Wrong password");
            }
        } catch (DataAccessException e) {
            throw new AccountException("Error finding account");
        }
        return account;
    }

    public Account createAccount(String username, String password, String email, StudentProfile studentProfile) throws AccountException {
        AccountDAO accountDAO = new AccountDAO();
        Account account = null;

        if (password.length()<6) {
            throw new AccountException("New password must have at least 6 characters.");
        }
        try {
            account = accountDAO.findByUsername(username);
            if (account != null) {
                throw new AccountException("Username already in use");
            }
            int newId = accountDAO.insert(new Account(0, username, password, email, studentProfile));
            account = new Account(newId, username, password, email, studentProfile);
        } catch (DataAccessException e) {
            throw new AccountException("Error creating account");
        }

        return account;
    }


    public Account updatePassword(Account account, String newPass) throws AccountException {
        AccountDAO accountDAO = new AccountDAO();
        Account updatedAccount = null;
        if (account.getPassword().equals(newPass)) {
            throw new AccountException("New password must be different from old password.");
        }

        if (newPass.length()<6) {
            throw new AccountException("New password must have at least 6 characters.");
        }
        updatedAccount = new Account(account.getId(), account.getUsername(), newPass, account.getEmail(), account.getProfile());
        try {
            accountDAO.insert(updatedAccount);
        } catch (DataAccessException e) {
            throw new AccountException("Error updating account");
        }

        return updatedAccount;
    }

    public Account updateEmail(Account account, String newMail) throws AccountException {
        AccountDAO accountDAO = new AccountDAO();
        Account updatedAccount = null;
        if (account.getPassword().equals(newMail)) {
            throw new AccountException("New email address must be different from old address.");
        }

        updatedAccount = new Account(account.getId(), account.getUsername(), account.getPassword(), newMail, account.getProfile());
        try {
            accountDAO.insert(updatedAccount);
        } catch (DataAccessException e) {
            throw new AccountException("Error updating account");
        }

        return updatedAccount;
    }

    public void deleteAccount(Account account) throws AccountException {
        AccountDAO accountDAO = new AccountDAO();
        try {
            accountDAO.delete(account.getId());
        } catch (DataAccessException e) {
            throw new AccountException("Error deleting account");
        }
    }
}
