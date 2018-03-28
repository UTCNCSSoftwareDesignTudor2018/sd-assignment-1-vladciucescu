package dataAccess;

import dataAccess.entity.AdminProfile;
import dataAccess.entity.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDAO {

    private static final String TABLE_NAME = "admins";

    public AdminProfile findById(int id) throws DataAccessException {
        AdminProfile adminProfile = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Profile profile = profileDAO.findById(resultSet.getInt(1));
                adminProfile = new AdminProfile(profile);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return adminProfile;
    }

    public List<AdminProfile> findAll() throws DataAccessException {
        List<AdminProfile> adminProfiles = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Profile profile = profileDAO.findById(resultSet.getInt(1));
                adminProfiles.add(new AdminProfile(profile));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return adminProfiles;
    }

    public int insert(AdminProfile obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ProfileDAO profileDAO = new ProfileDAO();

        int insertedId = profileDAO.insert(obj);
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

    public void update(AdminProfile obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        profileDAO.update(obj);
        ConnectionFactory.close(con);
    }

    public void delete(int id) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ProfileDAO profileDAO = new ProfileDAO();

        profileDAO.delete(id);
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
