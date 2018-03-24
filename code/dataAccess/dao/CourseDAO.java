package dataAccess.dao;

import dataAccess.entity.Course;
import dataAccess.entity.Exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@SuppressWarnings("ALL")
class CourseDAO implements EntityDAO<Course> {

    private static final String TABLE_NAME = "courses";

    CourseDAO() {
    }

    @Override
    public Course find(int id) throws DataAccessException {
        Course course = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExamDAO examDAO = new ExamDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();

            if (resultSet.next()) {
                Exam exam = examDAO.find(resultSet.getInt(1));
                course = new Course(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getDate(4).toLocalDate(), exam);
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return course;
    }

    @Override
    public List<Course> findAll() throws DataAccessException {
        List<Course> courses = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        ExamDAO examDAO = new ExamDAO();

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                Exam exam = examDAO.find(resultSet.getInt(5));
                courses.add(new Course(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDate(3).toLocalDate(),
                        resultSet.getDate(4).toLocalDate(),
                        exam));
            }
        } catch (SQLException | NoSuchElementException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return courses;
    }

    @Override
    public int insert(Course obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId = 0;

        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(course_name, start_date, end_date, exam_id) VALUES(?,?,?,?)");
            statement.setString(1, obj.getName());
            statement.setDate(2, Date.valueOf(obj.getStartDate()));
            statement.setDate(3, Date.valueOf(obj.getEndDate()));
            statement.setInt(4, obj.getExam().getId());
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
    public void update(Course obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("UPDATE " + TABLE_NAME + " SET course_name = ?, start_date=?, end_date=?, exam_id WHERE id = ?");
            statement.setString(1, obj.getName());
            statement.setDate(2, Date.valueOf(obj.getStartDate()));
            statement.setDate(3, Date.valueOf(obj.getEndDate()));
            statement.setInt(4, obj.getExam().getId());
            statement.setInt(5, obj.getId());
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
