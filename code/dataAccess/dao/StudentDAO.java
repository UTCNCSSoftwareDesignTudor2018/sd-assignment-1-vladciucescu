package dataAccess.dao;

import dataAccess.entity.StudentAccount;
import dataAccess.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO implements EntityDAO<StudentAccount> {

    private static final String TABLE_NAME = "students";

    @Override
    public StudentAccount find(int id) throws DataAccessException {
        StudentAccount student = null;
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
                student = new StudentAccount(user, resultSet.getInt(2), resultSet.getInt(3));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return student;
    }

    @Override
    public List<StudentAccount> findAll() throws DataAccessException {
        List<StudentAccount> students = new ArrayList<>();
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
                students.add(new StudentAccount(user, resultSet.getInt(2), resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return students;
    }

    @Override
    public int insert(StudentAccount obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        UserDAO userDAO = new UserDAO();

        int insertedId = userDAO.insert(obj);
        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(id, year, group) VALUES(?,?,?)");
            statement.setInt(1, insertedId);
            statement.setInt(2, obj.getYear());
            statement.setInt(3, obj.getGroup());
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
    public void update(StudentAccount obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        UserDAO userDAO = new UserDAO();

        userDAO.update(obj);
        try {
            statement = con.prepareStatement("UPDATE " + TABLE_NAME + " SET year=?, group=? WHERE id = ?");
            statement.setInt(1, obj.getYear());
            statement.setInt(2, obj.getGroup());
            statement.setInt(3, obj.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Update error ", e);
        } finally {
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }
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
