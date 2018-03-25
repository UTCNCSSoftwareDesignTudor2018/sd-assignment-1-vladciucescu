package dataAccess;

import entity.StudentProfile;
import entity.Profile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    private static final String TABLE_NAME = "students";

    public StudentProfile findById(int id) throws DataAccessException {
        StudentProfile studentProfile = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                Profile profile = profileDAO.findById( resultSet.getInt(1));
                studentProfile = new StudentProfile(profile, resultSet.getInt(2), resultSet.getInt(3));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return studentProfile;
    }

    public List<StudentProfile> findAll() throws DataAccessException {
        List<StudentProfile> studentProfiles = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Profile profile = profileDAO.findById( resultSet.getInt(1));
                studentProfiles.add(new StudentProfile(profile, resultSet.getInt(2), resultSet.getInt(3)));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return studentProfiles;
    }

    public int insert(StudentProfile obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ProfileDAO profileDAO = new ProfileDAO();

        int insertedId = profileDAO.insert(obj);
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

    public void update(StudentProfile obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ProfileDAO profileDAO = new ProfileDAO();

        profileDAO.update(obj);
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
