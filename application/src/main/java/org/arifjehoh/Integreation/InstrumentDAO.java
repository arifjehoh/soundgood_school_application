package org.arifjehoh.Integreation;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Instrument;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class InstrumentDAO {

    private static final String TABLE_NAME = "instrument_rental";
    private static final String ATTR_INSTRUMENT_ID = "instrument_id";
    private static final String ATTR_INSTRUMENT = "instrument";
    private static final String ATTR_TYPE = "instrument_type";
    private static final String ATTR_COST = "instrument_cost";
    private static final String ATTR_RENTAL_ID = "rental_id";
    private static final String ATTR_STUDENT_ID = "student_id";
    private static final String ATTR_RENTAL_DUE_DATE = "rental_due_date";

    private Connection connection;

    private PreparedStatement findAvailableInstrumentsStmt;
    private PreparedStatement updateInstrumentStmt;
    private PreparedStatement findFirstAvailableInstrumentStmt;
    private PreparedStatement findInstrumentByRentalIdStmt;
    private PreparedStatement removeInstrumentRent;

    public InstrumentDAO() throws DBException {
        try {
            createConnection();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DBException("Could not connect to datasource.", exception);
        }
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "arif", "1234");
        connection.setAutoCommit(false);
    }

    private void prepareStatements() throws SQLException {
        findAvailableInstrumentsStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME +
                " WHERE " + ATTR_RENTAL_ID + " IS NULL");
        findFirstAvailableInstrumentStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME +
                " WHERE " + ATTR_INSTRUMENT + " = ? AND " + ATTR_RENTAL_ID + " IS NULL LIMIT 1");
        updateInstrumentStmt = connection.prepareStatement("UPDATE " + TABLE_NAME +
                " SET " + ATTR_RENTAL_ID + " = ?, " + ATTR_STUDENT_ID + " = ?, " + ATTR_RENTAL_DUE_DATE + " = ? " +
                "WHERE " + ATTR_INSTRUMENT_ID + " = ?");
        findInstrumentByRentalIdStmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM " + TABLE_NAME +
                " WHERE " + ATTR_RENTAL_ID + " = ? AND " + ATTR_RENTAL_DUE_DATE + " BETWEEN ? AND ?");
        removeInstrumentRent = connection.prepareStatement("UPDATE " + TABLE_NAME +
                " SET " + ATTR_RENTAL_ID + " = NULL, " + ATTR_STUDENT_ID + " = NULL, " + ATTR_RENTAL_DUE_DATE + " = NULL " +
                "WHERE " + ATTR_RENTAL_ID + "= ? AND " + ATTR_INSTRUMENT_ID + " = ?");
    }

    public List<Instrument> findAvailableInstruments() throws DBException {
        List<Instrument> instruments = new ArrayList<>();
        try (ResultSet result = findAvailableInstrumentsStmt.executeQuery()) {
            while (result.next()) {
                Instrument instrument = new Instrument
                        .Builder(result.getString(ATTR_INSTRUMENT_ID), result.getString(ATTR_INSTRUMENT)
                        , result.getString(ATTR_TYPE), result.getString(ATTR_COST))
                        .build();
                instruments.add(instrument);
            }
            connection.commit();

        } catch (SQLException exception) {
            new DBException().handle(connection, "Could not list available instruments.", exception);
        }

        return instruments;
    }

    public void rentInstrument(int rentalId, int studentId, String instrument, String dueDate) throws DBException {
        String message = "Could not find available instrument of : " + instrument;
        try {
            int instrumentId = findInstrumentId(instrument);
            int updatedRows = executeUpdateInstrument(rentalId, studentId, dueDate, instrumentId);
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
    }

    private int executeUpdateInstrument(int rentalId, int studentId, String dueDate, int instrumentId) throws SQLException {
        updateInstrumentStmt.setInt(1, rentalId);
        updateInstrumentStmt.setInt(2, studentId);
        updateInstrumentStmt.setString(3, dueDate);
        updateInstrumentStmt.setInt(4, instrumentId);
        return updateInstrumentStmt.executeUpdate();
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
        String firstDayOfMonth = LocalDate.parse(dueDate.split(" ")[0])
                .with(TemporalAdjusters.firstDayOfMonth()).toString();
        boolean canRent = false;
        try {
            try (ResultSet result = executeFindInstrumentBy(id, dueDate, firstDayOfMonth)) {
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

    private ResultSet executeFindInstrumentBy(int id, String dueDate, String firstDayOfMonth) throws SQLException {
        findInstrumentByRentalIdStmt.setInt(1, id);
        findInstrumentByRentalIdStmt.setString(2, firstDayOfMonth);
        findInstrumentByRentalIdStmt.setString(3, dueDate);
        return findInstrumentByRentalIdStmt.executeQuery();
    }

    public String terminateRental(String rentalId, String instrumentId) throws DBException {
        String message = "Could not find instrument.";
        try {
            int updatedRows = executeRemoveRent(rentalId, instrumentId);
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
        return "You have successfully terminate your rental of instrument.";
    }

    private int executeRemoveRent(String rentalId, String instrumentId) throws SQLException {
        removeInstrumentRent.setInt(1, Integer.parseInt(rentalId));
        removeInstrumentRent.setInt(2, Integer.parseInt(instrumentId));
        return removeInstrumentRent.executeUpdate();
    }
}
