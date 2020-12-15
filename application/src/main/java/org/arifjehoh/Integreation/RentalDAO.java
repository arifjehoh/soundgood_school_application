package org.arifjehoh.Integreation;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Rental;
import org.arifjehoh.Model.RentalDTO;
import org.arifjehoh.Model.StudentDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

public class RentalDAO {

    private static final String TABLE_RENTAL = "rental";
    private static final String TABLE_INSTRUMENT = "instrument_rental";
    private static final String ATTR_STUDENT_ID = "student_id";
    private static final String ATTR_CITY = "city";
    private static final String ATTR_ZIP_CODE = "zip_code";
    private static final String ATTR_STREET_NAME = "street_name";
    private static final String ATTR_COUNTRY = "country";
    private static final String ATTR_RENTAL_DUE_DATE = "rental_due_date";
    private static final String ATTR_TOTAL_COST = "total_cost";
    private static final String ATTR_INSTRUMENT_COST = "instrument_cost";
    private static final String ATTR_RENTAL_ID = "rental_id";

    private Connection connection;
    private PreparedStatement findInvoiceStmt;
    private PreparedStatement createInvoiceStmt;
    private PreparedStatement updateInvoiceStmt;
    private PreparedStatement findInvoicesStmt;

    public RentalDAO() throws DBException {
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
        findInvoiceStmt = connection.prepareStatement("SELECT * FROM " + TABLE_RENTAL +
                " WHERE " + ATTR_STUDENT_ID + " = ? AND " + ATTR_RENTAL_DUE_DATE + " LIKE ?");
        findInvoicesStmt = connection.prepareStatement("SELECT * FROM " + TABLE_RENTAL +
                " WHERE " + ATTR_STUDENT_ID + " = ?");
        createInvoiceStmt = connection.prepareStatement("INSERT INTO " + TABLE_RENTAL + "(" + ATTR_STUDENT_ID +
                "," + ATTR_CITY + "," + ATTR_ZIP_CODE + "," + ATTR_STREET_NAME + "," + ATTR_COUNTRY +
                "," + ATTR_RENTAL_DUE_DATE + "," + ATTR_TOTAL_COST + ")" +
                " VALUES (?,?,?,?,?,?,0)");
        updateInvoiceStmt = connection.prepareStatement("UPDATE " + TABLE_RENTAL +
                " SET " + ATTR_TOTAL_COST + " = ? + " + ATTR_TOTAL_COST +
                " WHERE " + ATTR_RENTAL_ID + " = ?");
    }


    /**
     * Find invoice of student.
     *
     * @param id   student id.
     * @param date "YYYY-MM"
     * @return invoice of student by date.
     * @throws DBException
     */
    public Rental findInvoice(String id, String date) throws DBException {
        String failureMsg = "Could not find invoice.";
        String period = date + "%";
        Rental rental = null;
        try {
            ResultSet set = executeFindInvoice(id, period);
            while (set.next()) {
                rental = new Rental.Builder(set.getString("rental_id"), set.getString("student_id"),
                        set.getString("city"), set.getString("zip_code"), set.getString("street_name"),
                        set.getString("country"), set.getString("rental_due_date"), set.getString("total_cost"))
                        .build();
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, failureMsg, cause);
        }
        return rental;
    }

    /**
     * Execute query for finding invoice.
     *
     * @param id     student id
     * @param period "YYYY-MM%"
     * @return query values.
     * @throws SQLException
     */
    private ResultSet executeFindInvoice(String id, String period) throws SQLException {
        findInvoiceStmt.setInt(1, Integer.parseInt(id));
        findInvoiceStmt.setString(2, period);
        return findInvoiceStmt.executeQuery();
    }

    /**
     * Create new invoice.
     *
     * @param student
     * @throws DBException
     */
    public void createInvoice(StudentDTO student) throws DBException {
        String message = "Could create a invoice for: " + student;
        String dueDate = String.valueOf(LocalDateTime.of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth())
                .toLocalDate(), LocalTime.MIDNIGHT.minusSeconds(1)));
        dueDate = dueDate.replace("T", " ");
        int updatedRows;
        try {
            createInvoiceStmt.execute("SET FOREIGN_KEY_CHECKS=0");
            updatedRows = executeCreateInvoice(student, dueDate);
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            createInvoiceStmt.execute("SET FOREIGN_KEY_CHECKS=1");
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
    }

    /**
     * Execute query for creating new invoice.
     * Mock data will added for only mandatory requirement.
     *
     * @param student
     * @param dueDate
     * @return query values.
     * @throws SQLException
     */
    private int executeCreateInvoice(StudentDTO student, String dueDate) throws SQLException {
        createInvoiceStmt.setInt(1, student.getId());
        createInvoiceStmt.setString(2, "stockholm"); // CITY
        createInvoiceStmt.setString(3, "12345"); // ZIP CODE
        createInvoiceStmt.setString(4, "streetname"); // STREET NAME
        createInvoiceStmt.setString(5, "sweden"); // COUNTRY
        createInvoiceStmt.setString(6, dueDate);
        return createInvoiceStmt.executeUpdate();
    }

    /**
     * Update invoice values.
     *
     * @param rentalId
     * @param cost     of instrument.
     * @throws DBException
     */
    public void updateInvoice(int rentalId, double cost) throws DBException {
        String message = "Could not find invoice.";
        int updatedRows;
        try {
            updatedRows = executeUpdate(cost, rentalId);
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
    }

    /**
     * Execute query for updating invoice.
     *
     * @param cost
     * @param rentalId
     * @return query status.
     * @throws SQLException
     */
    private int executeUpdate(double cost, int rentalId) throws SQLException {
        updateInvoiceStmt.setDouble(1, cost);
        updateInvoiceStmt.setInt(2, rentalId);
        return updateInvoiceStmt.executeUpdate();
    }

    /**
     * Find invoices of student.
     *
     * @param studentId
     * @return list of invoices by student.
     * @throws DBException
     */
    public List<? extends RentalDTO> findInvoices(int studentId) throws DBException {
        String failureMsg = "Could not find invoice.";
        List<Rental> rentals = new ArrayList<>();
        try {
            ResultSet set = executeFindInvoices(studentId);
            while (set.next()) {
                if (set != null) {
                    Rental rental = new Rental.Builder(set.getString(ATTR_RENTAL_ID), set.getString(ATTR_STUDENT_ID),
                            set.getString(ATTR_CITY), set.getString(ATTR_ZIP_CODE), set.getString(ATTR_STREET_NAME),
                            set.getString(ATTR_COUNTRY), set.getString(ATTR_RENTAL_DUE_DATE),
                            set.getString(ATTR_TOTAL_COST))
                            .build();
                    rentals.add(rental);
                }

            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, failureMsg, cause);
        }
        return rentals;
    }

    /**
     * Execute query for finding invoices.
     *
     * @param studentId
     * @return query values.
     * @throws SQLException
     */
    private ResultSet executeFindInvoices(int studentId) throws SQLException {
        findInvoicesStmt.setInt(1, studentId);
        return findInvoicesStmt.executeQuery();
    }
}
