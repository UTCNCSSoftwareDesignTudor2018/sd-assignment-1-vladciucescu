package dataAccess;

import dataAccess.entity.Profile;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfileDAO {

    private static final String TABLE_NAME = "users";

    public Profile findById(int id) throws DataAccessException {
        Profile profile = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                profile = new Profile(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return profile;
    }

    public Profile findByIdCardNumber(int idCardNumber) throws DataAccessException {
        Profile profile = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id_card_number = " + idCardNumber);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                profile = new Profile(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return profile;
    }

    public List<Profile> findAll() throws DataAccessException {
        List<Profile> profiles = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                profiles.add(new Profile(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getInt(3),
                        resultSet.getString(4)));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return profiles;
    }

    public int insert(Profile obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId = 0;

        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(name, id_card_number, address) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, obj.getName());
            statement.setInt(2, obj.getId_card_number());
            statement.setString(3, obj.getAddress());
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

    public void update(Profile obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("UPDATE " + TABLE_NAME + " SET name= ?, address=? WHERE id = ?");
            statement.setString(1, obj.getName());
            statement.setString(2, obj.getAddress());
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
