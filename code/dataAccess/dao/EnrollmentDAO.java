package dataAccess.dao;

import dataAccess.entity.Course;
import dataAccess.entity.Enrollment;
import dataAccess.entity.StudentAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("ALL")
public class EnrollmentDAO implements EntityDAO<Enrollment> {

    private static final String TABLE_NAME = "enrollments";

    @Override
    public Enrollment find(int id) throws DataAccessException {
        Enrollment enrollment = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                StudentAccount student = studentDAO.find(resultSet.getInt(2));
                Course course = courseDAO.find(resultSet.getInt(3));
                enrollment = new Enrollment(resultSet.getInt(1),
                        student,
                        course,
                        resultSet.getDouble(4));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return enrollment;
    }

    @Override
    public List<Enrollment> findAll() throws DataAccessException {
        List<Enrollment> enrollments = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                StudentAccount student = studentDAO.find(resultSet.getInt(2));
                Course course = courseDAO.find(resultSet.getInt(3));
                enrollments.add(new Enrollment(resultSet.getInt(1),
                        student,
                        course,
                        resultSet.getDouble(4)));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return enrollments;
    }

    @Override
    public int insert(Enrollment obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId = 0;

        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(student_id, course_id, grade) VALUES(?,?,?)");
            statement.setInt(1, obj.getStudent().getId());
            statement.setInt(2, obj.getCourse().getId());
            statement.setDouble(3, obj.getGrade());
            statement.executeUpdate();
            resultSet = statement.getResultSet();
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

    @Override
    public void update(Enrollment obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("UPDATE " + TABLE_NAME + " SET grade=? WHERE id = ?");
            statement.setDouble(1, obj.getGrade());
            statement.setInt(2, obj.getId());
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
