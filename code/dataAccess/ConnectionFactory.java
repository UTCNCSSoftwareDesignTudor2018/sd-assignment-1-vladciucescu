package dataAccess;

import java.sql.*;

/**
 * Implements static methods used to create connections to the database
 */
class ConnectionFactory {
    private static final String DBURL = "jdbc:mysql://localhost:3306/school?useSSL=false";
    private static final String USER = "root";
    private static final String PASS = "123abd";

    private ConnectionFactory() {
    }

    /**
     * Tries to create a new Connection to the database.
     *
     * @return connection to the database, or null if a connection could not be created
     */
    public static Connection getConnection() throws DataAccessException {
        Connection connection;
        try {
            connection = DriverManager.getConnection(DBURL, USER, PASS);
        } catch (SQLException e) {
            throw new DataAccessException("Could not create connection ", e);
        }
        return connection;
    }

    /**
     * Tries to close the given Connection.
     *
     * @param connection - the Connection to close
     */
    public static void close(Connection connection) throws DataAccessException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw new DataAccessException("Could not close connection ", e);
            }
        }
    }

    /**
     * Tries to close the given Statement.
     *
     * @param statement - the Statement to close
     */
    public static void close(Statement statement) throws DataAccessException {
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                throw new DataAccessException("Could not close statement ", e);
            }
        }
    }

    /**
     * Tries to close the given ResultSet.
     *
     * @param resultSet - the ResultSet to close
     */
    public static void close(ResultSet resultSet) throws DataAccessException {
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                throw new DataAccessException("Could not close result set ", e);
            }
        }
    }
}
