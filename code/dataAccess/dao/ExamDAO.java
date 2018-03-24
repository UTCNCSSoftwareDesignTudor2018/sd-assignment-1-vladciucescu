package dataAccess.dao;

import dataAccess.entity.Exam;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class ExamDAO implements EntityDAO<Exam> {

    private static final String TABLE_NAME = "exams";

    @Override
    public Exam find(int id) throws DataAccessException {
        Exam exam = null;
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = " + id);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            if (resultSet.next()) {
                exam = new Exam(resultSet.getInt(1),
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getTime(3).toLocalTime(),
                        resultSet.getBoolean(4));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return exam;
    }

    @Override
    public List<Exam> findAll() throws DataAccessException {
        List<Exam> exams = new ArrayList<>();
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("SELECT * FROM " + TABLE_NAME);
            statement.executeQuery();
            resultSet = statement.getResultSet();
            while (resultSet.next()) {
                exams.add(new Exam(resultSet.getInt(1),
                        resultSet.getDate(2).toLocalDate(),
                        resultSet.getTime(3).toLocalTime(),
                        resultSet.getBoolean(4)));
            }
        } catch (SQLException e) {
            throw new DataAccessException("Find all error ", e);
        } finally {
            ConnectionFactory.close(resultSet);
            ConnectionFactory.close(statement);
            ConnectionFactory.close(con);
        }

        return exams;
    }

    @Override
    public int insert(Exam obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        int insertedId = 0;

        try {
            statement = con.prepareStatement("INSERT INTO " + TABLE_NAME + "(date, time, written) VALUES(?,?,?)", Statement.RETURN_GENERATED_KEYS);
            statement.setDate(1, Date.valueOf(obj.getDate()));
            statement.setTime(2, Time.valueOf(obj.getTime()));
            statement.setBoolean(3, obj.getWritten());
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

    @Override
    public void update(Exam obj) throws DataAccessException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            statement = con.prepareStatement("UPDATE " + TABLE_NAME + " SET date = ?, time=?, written=? WHERE id = ?");
            statement.setDate(1, Date.valueOf(obj.getDate()));
            statement.setTime(2, Time.valueOf(obj.getTime()));
            statement.setBoolean(3, obj.getWritten());
            statement.setInt(4, obj.getId());
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
