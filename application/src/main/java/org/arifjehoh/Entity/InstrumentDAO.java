package org.arifjehoh.Entity;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDAO {
    private static final String ATTR_PK_INSTRUMENT_ID = "instrument_id";
    private static final String ATTR_INSTRUMENT = "instrument";
    private static final String ATTR_TYPE = "instrument_type";
    private static final String ATTR_COST = "instrument_cost";
    private static final String ATTR_FK_STUDENT_ID = "student_id";
    private static final String TABLE_NAME = "instrument_rental";
    private static final String ATTR_FK_RENTAL_ID = "rental_id";
    private Connection connection;
    private PreparedStatement findAvailableInstrumentsStmt;

    public InstrumentDAO() throws DBException {
        try {
            createConnection();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DBException("Could not connect to datasource.", exception);
        }
    }

    private void prepareStatements() throws SQLException {
        findAvailableInstrumentsStmt = connection.prepareStatement("SELECT * FROM " +
                TABLE_NAME + " WHERE " + ATTR_FK_RENTAL_ID + " IS NULL");
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school","arif","1234");
        connection.setAutoCommit(false);
    }

    public List<Instrument> findAvailableInstruments() throws DBException {
        String failureMsg = "Could not list available instruments.";
        List<Instrument> instruments = new ArrayList<>();
        try (ResultSet result = findAvailableInstrumentsStmt.executeQuery()) {
            while (result.next()) {
                Instrument instrument = new Instrument
                        .Builder(result.getString(ATTR_PK_INSTRUMENT_ID), result.getString(ATTR_INSTRUMENT),result.getString(ATTR_TYPE),result.getString(ATTR_COST))
                        .build();
                instruments.add(instrument);
            }
            connection.commit();

        } catch (SQLException exception) {
            handleException(failureMsg, exception);
        }
        return instruments;
    }

    private void handleException(String message, Exception cause) throws DBException {
        StringBuilder completeFailureMsg = new StringBuilder().append(message);
        try {
            connection.rollback();
        } catch (SQLException rollbakExc) {
            completeFailureMsg
                    .append(". Also failed to rollback transaction because of: ")
                    .append(rollbakExc.getMessage());
        }
        if (cause != null) {
            throw new DBException(completeFailureMsg.toString(), cause);
        } else {
            throw new DBException(completeFailureMsg.toString());
        }

    }
}
