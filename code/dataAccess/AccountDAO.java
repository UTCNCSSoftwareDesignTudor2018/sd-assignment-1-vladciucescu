package dataAccess;

import dataAccess.entity.Account;
import dataAccess.entity.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {

    private static final String TABLE_NAME = "accounts";

    public Account findById(int id) throws DataAccessException {
        Account account = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Profile profile = profileDAO.findById(resultSet.getInt(5));
                account = (new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        profile));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find by id error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return account;
    }

    public Account findByUsername(String username) throws DataAccessException {
        Account account = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE username = '" + username + "'");
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Profile profile = profileDAO.findById(resultSet.getInt(5));
                account = (new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        profile));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find by username error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return account;
    }

    public List<Account> findAll() throws DataAccessException {
        List<Account> accounts = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Profile profile = profileDAO.findById(resultSet.getInt(5));
                accounts.add(new Account(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getString(4),
                        profile));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return accounts;
    }

    public int insert(Account obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId = 0;

        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(username, password, email, userid) VALUES(?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getUsername());
            statement.setString(2, obj.getPassword());
            statement.setString(3, obj.getEmail());
            statement.setInt(4, obj.getProfile().getId());
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                insertedId = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Insert error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return insertedId;
    }

    public void update(Account obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement("UPDATE " + TABLE_NAME + " SET password=?, email=? WHERE id = ?");
            statement.setString(1, obj.getPassword());
            statement.setString(2, obj.getEmail());
            statement.setInt(3, obj.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Update error ", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
    }

    public void delete(int id) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;

        try {
            statement = con.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Delete error ", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
    }
}
