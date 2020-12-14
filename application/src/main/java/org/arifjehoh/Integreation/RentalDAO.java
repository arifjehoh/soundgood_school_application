package org.arifjehoh.Integreation;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Rental;
import org.arifjehoh.Model.RentalDTO;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;

public class RentalDAO {

    private static final String TABLE_NAME = "rental";
    private Connection connection;
    private PreparedStatement findInvoiceStmt;
    private PreparedStatement createInvoiceStmt;
    private PreparedStatement updateInvoiceStmt;

    public RentalDAO() throws DBException {
        try {
            createConnection();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DBException("Could not connect to datasource.", exception);
        }
    }

    private void prepareStatements() throws SQLException {
        findInvoiceStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE student_id = ?");
        createInvoiceStmt = connection.prepareStatement("INSERT INTO " + TABLE_NAME + "(student_id,city," +
                "zip_code,street_name,country,rental_due_date) VALUES (?,?,?,?,?,?)");
        updateInvoiceStmt = connection.prepareStatement("UPDATE "+TABLE_NAME+" SET total_cost = (SELECT SUM" +
                "(instrument_cost) FROM instrument_rental WHERE rental_id = ?) WHERE rental_id = ?");
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "arif", "1234");
        connection.setAutoCommit(false);
    }

    public Rental findInvoice(int id, String timePeriod) throws DBException {
        String failureMsg = "Could not find invoice.";
        Rental rental = null;
        try {
            findInvoiceStmt.setInt(1, id);
            ResultSet set = findInvoiceStmt.executeQuery();
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

    public void createInvoice(StudentDTO student) throws DBException {
        String message = "Could create a invoice for: " + student;
        int updatedRows = 0;
        try {
            createInvoiceStmt.execute("SET FOREIGN_KEY_CHECKS=0");
            createInvoiceStmt.setInt(1, student.getId());
            createInvoiceStmt.setString(2, student.getCity());
            createInvoiceStmt.setString(3, student.getZipCode());
            createInvoiceStmt.setString(4, student.getStreetName());
            createInvoiceStmt.setString(5, student.getCountry());
            createInvoiceStmt.setString(6,
                    String.valueOf(LocalDateTime
                            .of(LocalDateTime.now().with(TemporalAdjusters.lastDayOfMonth()).toLocalDate(),
                                    LocalTime.MIDNIGHT.minusSeconds(1))));
            updatedRows = createInvoiceStmt.executeUpdate();
            if (updatedRows != 1) {
                new DBException().handle(connection, message, null);
            }
            createInvoiceStmt.execute("SET FOREIGN_KEY_CHECKS=1");
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection, message, cause);
        }
    }

    public void updateInvoice(RentalDTO rental) throws DBException {
        String message = "Could not find invoice.";
        int updatedRows = 0;
        try {
            updateInvoiceStmt.setInt(1, rental.getId());
            updateInvoiceStmt.setInt(2, rental.getId());
            updatedRows = updateInvoiceStmt.executeUpdate();
            if (updatedRows != 1) {
                new DBException().handle(connection,message,null);
            }
            connection.commit();
        } catch (SQLException cause) {
            new DBException().handle(connection,message,cause);
        }
    }
}
