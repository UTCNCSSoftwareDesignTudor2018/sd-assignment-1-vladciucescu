package dataAccess.dao;

import dataAccess.entity.AdminAccount;
import dataAccess.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO implements EntityDAO<AdminAccount> {

    private static final String TABLE_NAME = "admins";

    @Override
    public AdminAccount find(int id) throws DataAccessException {
        AdminAccount admin = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserDAO userDAO = new UserDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                User user = userDAO.find(resultSet.getInt(1));
                admin = new AdminAccount(user);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return admin;
    }

    @Override
    public List<AdminAccount> findAll() throws DataAccessException {
        List<AdminAccount> admins = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserDAO userDAO = new UserDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                User user = userDAO.find(resultSet.getInt(1));
                admins.add(new AdminAccount(user));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return admins;
    }

    @Override
    public int insert(AdminAccount obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        UserDAO userDAO = new UserDAO();

        int insertedId = userDAO.insert(obj);
        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(id) VALUES(?)");
            statement.setInt(1, insertedId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Insert error ", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return insertedId;
    }

    @Override
    public void update(AdminAccount obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        ResultSet resultSet = null;
        UserDAO userDAO = new UserDAO();

        userDAO.update(obj);
        ConnectionFactory.close(con);
    }

    @Override
    public void delete(int id) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        UserDAO userDAO = new UserDAO();

        userDAO.delete(id);
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
