package testing.dataAccess;

import dataAccess.AccountDAO;
import dataAccess.DataAccessException;
import dataAccess.ProfileDAO;
import dataAccess.entity.Account;
import dataAccess.entity.Profile;
import org.junit.jupiter.api.Test;

class AccountDAOTest {

    @Test
    void findByUsernameTest() throws DataAccessException {
        AccountDAO accountDAO = new AccountDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        Profile profile = profileDAO.findById(2);
        Account account = new Account(2, "frumi", "123", "frumix@gmail.br", profile);
        Account res = accountDAO.findByUsername("frumi");
        assert account.equals(res);
    }

    @Test
    void updateTest() throws DataAccessException {
        AccountDAO accountDAO = new AccountDAO();
        ProfileDAO profileDAO = new ProfileDAO();
        Profile profile = profileDAO.findById(2);
        Account orig = new Account(2, "frumi", "123", "frumix@gmail.br", profile);
        Account updated = new Account(2, "frumi", "124", "frumix@gmail.com", profile);
        accountDAO.update(updated);
        Account check = accountDAO.findById(2);
        accountDAO.update(orig);

        assert check.equals(updated);
    }

}
