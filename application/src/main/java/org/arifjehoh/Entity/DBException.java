package org.arifjehoh.Entity;

import java.sql.Connection;
import java.sql.SQLException;

public class DBException extends Exception {
    public DBException(String message) {
        super(message);
    }

    public DBException(String message, Throwable cause) {
        super(message, cause);
    }

    public DBException() {

    }

    /**
     * Handling causes from queries.
     *
     * @param connection to database.
     * @param message
     * @param cause
     * @throws DBException
     */
    public void handle(Connection connection, String message, Exception cause) throws DBException {
        StringBuilder completeMessage = new StringBuilder().append(message);
        try {
            connection.rollback();
        } catch (SQLException rollback) {
            completeMessage.append(". Also failed to rollback transaction because of: ").append(rollback.getMessage());
        }
        if (cause != null) {
            throw new DBException(completeMessage.toString(), cause);
        } else {
            throw new DBException(completeMessage.toString());
        }
    }
}
