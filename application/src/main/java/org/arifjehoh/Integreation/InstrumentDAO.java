package org.arifjehoh.Integreation;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Instrument;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDAO {
    private static final String ATTR_PK_INSTRUMENT_ID = "instrument_id";
    private static final String ATTR_INSTRUMENT = "instrument";
    private static final String ATTR_TYPE = "instrument_type";
    private static final String ATTR_COST = "instrument_cost";
    private static final String TABLE_NAME = "instrument_rental";
    private static final String ATTR_FK_RENTAL_ID = "rental_id";
    private Connection connection;
    private PreparedStatement findAvailableInstrumentsStmt;
    private PreparedStatement updateInstrumentStmt;
    private PreparedStatement findFirstAvailableInstrumentStmt;
    private PreparedStatement findInstrumentByRentalIdStmt;

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
        findFirstAvailableInstrumentStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " +
                "instrument = ? AND rental_id IS NULL LIMIT 1");
        updateInstrumentStmt = connection.prepareStatement("UPDATE " + TABLE_NAME + " SET rental_id = ?, student_id = " +
                "?, rental_due_date = ? " + "WHERE instrument_id = ?");
        findInstrumentByRentalIdStmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM instrument_rental " +
                "WHERE " +
                "rental_id = ? AND rental_due_date BETWEEN ? AND ?");
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "arif", "1234");
        connection.setAutoCommit(false);
    }

    public List<Instrument> findAvailableInstruments() throws DBException {
        String failureMsg = "Could not list available instruments.";
        List<Instrument> instruments = new ArrayList<>();
        try (ResultSet result = findAvailableInstrumentsStmt.executeQuery()) {
            while (result.next()) {
                Instrument instrument = new Instrument
                        .Builder(result.getString(ATTR_PK_INSTRUMENT_ID), result.getString(ATTR_INSTRUMENT), result.getString(ATTR_TYPE), result.getString(ATTR_COST))
                        .build();
                instruments.add(instrument);
            }
            connection.commit();

        } catch (SQLException exception) {
            new DBException().handle(connection, failureMsg, exception);
        }

        return instruments;
    }

    public void rentInstrument(int rentalId, int studentId, String instrument, String dueDate) throws DBException {
        String message = "Could not find available instrument of : " + instrument;
        int instrumentId = 0;
        int updatedRows = 0;
        try {
            instrumentId = findInstrumentId(instrument);
            updateInstrumentStmt.setInt(1, rentalId);
            updateInstrumentStmt.setInt(2, studentId);
            updateInstrumentStmt.setString(3, dueDate);
            updateInstrumentStmt.setInt(4, instrumentId);
            updatedRows = updateInstrumentStmt.executeUpdate();
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
    }

    private int findInstrumentId(String instrument) throws SQLException {
        findFirstAvailableInstrumentStmt.setString(1, instrument);
        int id = 0;
        try (ResultSet set = findFirstAvailableInstrumentStmt.executeQuery()) {
            while (set.next()) {
                id = set.getInt("instrument_id");
            }
            connection.commit();
        }
        return id;
    }

    public boolean canRentInstrument(int id, String dueDate) throws DBException {
        String message = "Could not find rented instrument.";
        boolean canRent = false;
        String firstDayOfMonth = LocalDate.parse(dueDate.split(" ")[0])
                .with(TemporalAdjusters.firstDayOfMonth()).toString();

        try {
            findInstrumentByRentalIdStmt.setInt(1, id);
            findInstrumentByRentalIdStmt.setString(2, firstDayOfMonth);
            findInstrumentByRentalIdStmt.setString(3, dueDate);
            try (ResultSet result = findInstrumentByRentalIdStmt.executeQuery()) {
                while (result.next()) {
                    canRent = result.getInt("total") <= 2;
                }

                connection.commit();

            }
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
        return canRent;
    }
}
