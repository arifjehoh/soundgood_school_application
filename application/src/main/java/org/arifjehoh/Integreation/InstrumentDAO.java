package org.arifjehoh.Integreation;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Instrument;
import org.arifjehoh.Model.InstrumentDTO;

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
    private PreparedStatement findInstrumentsStmt;
    private PreparedStatement findAvailableInstrumentsByTypeStmt;

    public InstrumentDAO() throws DBException {
        try {
            createConnection();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DBException("Could not connect to datasource.", exception);
        }
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "soundgood", "soundgood");
        connection.setAutoCommit(false);
    }

    private void prepareStatements() throws SQLException {
        findAvailableInstrumentsStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME +
                " WHERE " + ATTR_RENTAL_ID + " IS NULL");
        findAvailableInstrumentsByTypeStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME +
                " WHERE " + ATTR_INSTRUMENT + " = ? AND " + ATTR_RENTAL_ID + " IS NULL");
        findFirstAvailableInstrumentStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME +
                " WHERE " + ATTR_INSTRUMENT + " = ? AND " + ATTR_RENTAL_ID + " IS NULL LIMIT 1");
        findInstrumentsStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME +
                " WHERE " + ATTR_STUDENT_ID + " = ?");
        updateInstrumentStmt = connection.prepareStatement("UPDATE " + TABLE_NAME +
                " SET " + ATTR_RENTAL_ID + " = ?, " + ATTR_STUDENT_ID + " = ?, " + ATTR_RENTAL_DUE_DATE + " = ? " +
                "WHERE " + ATTR_INSTRUMENT_ID + " = ?");
        findInstrumentByRentalIdStmt = connection.prepareStatement("SELECT COUNT(*) AS total FROM " + TABLE_NAME +
                " WHERE " + ATTR_RENTAL_ID + " = ? AND " + ATTR_RENTAL_DUE_DATE + " BETWEEN ? AND ?");
        removeInstrumentRent = connection.prepareStatement("UPDATE " + TABLE_NAME +
                " SET " + ATTR_RENTAL_ID + " = NULL, " + ATTR_STUDENT_ID + " = NULL, " + ATTR_RENTAL_DUE_DATE + " = NULL " +
                "WHERE " + ATTR_RENTAL_ID + "= ? AND " + ATTR_INSTRUMENT_ID + " = ?");
    }

    /**
     * Find all available instruments.
     *
     * @param type of instrument.
     * @return instruments.
     * @throws DBException
     */
    public List<Instrument> findAvailableInstruments(String type) throws DBException {
        List<Instrument> instruments = new ArrayList<>();
        try (ResultSet result = executeFindAvailableInstrument(type)) {
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

    /**
     * Execute query for find available instrument.
     *
     * @param type of instrument. If param is empty then find all available instrument.
     * @return query values.
     * @throws SQLException
     */
    private ResultSet executeFindAvailableInstrument(String type) throws SQLException {
        if (type.equals("")) {
            return findAvailableInstrumentsStmt.executeQuery();
        } else {
            findAvailableInstrumentsByTypeStmt.setString(1, type);
            return findAvailableInstrumentsByTypeStmt.executeQuery();
        }
    }

    /**
     * Rent a instrument to the student.
     *
     * @param rentalId  to relate the instrument to.
     * @param studentId to relate the instrument to.
     * @param type      of instrument.
     * @param dueDate   to return or renew rent.
     * @return return instrument data.
     * @throws DBException
     */
    public InstrumentDTO rentInstrument(int rentalId, String studentId, String type, String dueDate) throws DBException {
        String message = "Could not find available instrument of : " + type;
        InstrumentDTO instrument = null;
        try {
            instrument = findInstrumentId(type);
            int updatedRows = executeUpdateInstrument(rentalId, studentId, dueDate, instrument.getId());
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
        return instrument;
    }

    /**
     * Execute Query for updating values of instrument.
     *
     * @param rentalId     to relate the instrument to.
     * @param studentId    to relate the instrument to.
     * @param dueDate      to return or renew rent.
     * @param instrumentId for which instrument to update.
     * @return return status of updating instrument value.
     * @throws SQLException
     */
    private int executeUpdateInstrument(int rentalId, String studentId, String dueDate, int instrumentId) throws SQLException {
        updateInstrumentStmt.setInt(1, rentalId);
        updateInstrumentStmt.setInt(2, Integer.parseInt(studentId));
        updateInstrumentStmt.setString(3, dueDate);
        updateInstrumentStmt.setInt(4, instrumentId);
        return updateInstrumentStmt.executeUpdate();
    }

    /**
     * Find instrument id of target type.
     *
     * @param type target type.
     * @return first instrument by type.
     * @throws SQLException
     */
    private InstrumentDTO findInstrumentId(String type) throws SQLException {
        findFirstAvailableInstrumentStmt.setString(1, type);
        Instrument instrument = null;
        try (ResultSet set = findFirstAvailableInstrumentStmt.executeQuery()) {
            while (set.next()) {
                instrument = new Instrument
                        .Builder(set.getString(ATTR_INSTRUMENT_ID), set.getString(ATTR_INSTRUMENT)
                        , set.getString(ATTR_TYPE), set.getString(ATTR_COST))
                        .build();
            }
            connection.commit();
        }
        return instrument;
    }

    /**
     * Can student rent more instrument.
     *
     * @param id      rental id.
     * @param dueDate when needed to return.
     * @return if student can rent more instrument.
     * @throws DBException
     */
    public boolean canRentInstrument(int id, String dueDate) throws DBException {
        String message = "Could not find rented instrument.";
        String firstDayOfMonth = LocalDate.parse(dueDate.split(" ")[0])
                .with(TemporalAdjusters.firstDayOfMonth()).toString();
        boolean canRent = false;
        try {
            try (ResultSet result = executeFindInstrumentBy(id, dueDate, firstDayOfMonth)) {
                while (result.next()) {
                    canRent = result.getInt("total") < 2;
                }
                connection.commit();
            }
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
        return canRent;
    }

    /**
     * Execute query for finding instrument by rental id.
     *
     * @param id              rental id
     * @param dueDate
     * @param firstDayOfMonth
     * @return query value.
     * @throws SQLException
     */
    private ResultSet executeFindInstrumentBy(int id, String dueDate, String firstDayOfMonth) throws SQLException {
        findInstrumentByRentalIdStmt.setInt(1, id);
        findInstrumentByRentalIdStmt.setString(2, firstDayOfMonth);
        findInstrumentByRentalIdStmt.setString(3, dueDate);
        return findInstrumentByRentalIdStmt.executeQuery();
    }

    /**
     * Terminate rental.
     *
     * @param rentalId
     * @param instrumentId
     * @return status.
     * @throws DBException
     */
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

    /**
     * Execute query for removing rented instrument.
     *
     * @param rentalId
     * @param instrumentId
     * @return query value.
     * @throws SQLException
     */
    private int executeRemoveRent(String rentalId, String instrumentId) throws SQLException {
        removeInstrumentRent.setInt(1, Integer.parseInt(rentalId));
        removeInstrumentRent.setInt(2, Integer.parseInt(instrumentId));
        return removeInstrumentRent.executeUpdate();
    }

    /**
     * Find instruments by student id.
     *
     * @param studentId
     * @return list of instruments owned by student.
     * @throws DBException
     */
    public List<? extends InstrumentDTO> findInstrumentsBy(int studentId) throws DBException {
        List<Instrument> instruments = new ArrayList<>();
        try {
            findInstrumentsStmt.setInt(1, studentId);
            ResultSet result = findInstrumentsStmt.executeQuery();
            while (result.next()) {
                Instrument instrument = new Instrument
                        .Builder(result.getString(ATTR_INSTRUMENT_ID), result.getString(ATTR_INSTRUMENT)
                        , result.getString(ATTR_TYPE), result.getString(ATTR_COST))
                        .rentalId(result.getString(ATTR_RENTAL_ID))
                        .due(result.getString(ATTR_RENTAL_DUE_DATE))
                        .build();
                instruments.add(instrument);
            }
            connection.commit();

        } catch (SQLException exception) {
            new DBException().handle(connection, "Could not list available instruments.", exception);
        }

        return instruments;
    }
}
