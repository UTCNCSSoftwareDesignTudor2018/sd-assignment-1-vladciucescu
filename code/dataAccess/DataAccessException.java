package dataAccess;

public class DataAccessException extends Exception {

    DataAccessException(String message, Exception cause) {
        super(message + cause.toString(), cause);
    }
}
