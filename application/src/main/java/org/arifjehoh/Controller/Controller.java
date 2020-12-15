package org.arifjehoh.Controller;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Integreation.InstrumentDAO;
import org.arifjehoh.Integreation.RentalDAO;
import org.arifjehoh.Integreation.StudentDAO;
import org.arifjehoh.Model.InstrumentDTO;
import org.arifjehoh.Model.RentalDTO;
import org.arifjehoh.Model.StudentDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Controller {
    private final InstrumentDAO instrumentDB;
    private final RentalDAO rentalDB;
    private final StudentDAO studentDB;

    public Controller() throws DBException {
        instrumentDB = new InstrumentDAO();
        rentalDB = new RentalDAO();
        studentDB = new StudentDAO();
    }

    /**
     * Find all available instruments.
     *
     * @param instrument type or all instruments if param is empty.
     * @return list of instruments.
     * @throws DBException
     */
    public List<? extends InstrumentDTO> getAvailableInstruments(String instrument) throws DBException {
        try {
            return instrumentDB.findAvailableInstruments(instrument);
        } catch (Exception exception) {
            throw new DBException("Unable to list available instruments.", exception);
        }
    }

    /**
     * Rent instrument for student.
     *
     * @param studentId
     * @param type
     * @return status.
     * @throws DBException
     */
    public String rentInstrument(String studentId, String type) throws DBException {
        String timePeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        RentalDTO rental = getRental(studentId, timePeriod);
        boolean canRent = instrumentDB.canRentInstrument(rental.getId(), rental.getDueDate());
        if (canRent) {
            InstrumentDTO instrument = instrumentDB.rentInstrument(rental.getId(), studentId, type,
                    rental.getDueDate());
            rentalDB.updateInvoice(rental.getId(), instrument.getCost());
            return "You have successfully rented your instrument.";
        }
        return "You have failed to rent your instrument, due to multiple rented instrument or instrument not found.";
    }

    /**
     * Find invoice for student.
     * Or create a new invoice for student if not found.
     *
     * @param studentId
     * @param timePeriod
     * @return
     * @throws DBException
     */
    private RentalDTO getRental(String studentId, String timePeriod) throws DBException {
        RentalDTO rental = rentalDB.findInvoice(studentId, timePeriod);
        if (rental == null) {
            createInvoice(studentId);
            rental = rentalDB.findInvoice(studentId, timePeriod);
        }
        return rental;
    }

    /**
     * Create invoice for student.
     *
     * @param studentId
     * @throws DBException
     */
    private void createInvoice(String studentId) throws DBException {
        StudentDTO student = studentDB.findStudent(studentId);
        rentalDB.createInvoice(student);
    }

    /**
     * Terminate a rented instrument.
     *
     * @param rentalId
     * @param instrumentId
     * @return status.
     * @throws DBException
     */
    public String terminateRental(String rentalId, String instrumentId) throws DBException {
        String status = instrumentDB.terminateRental(rentalId, instrumentId);
        return status;
    }

    /**
     * Find all invoices of student.
     *
     * @param studentId
     * @return list of invoices.
     * @throws DBException
     */
    public List<? extends RentalDTO> getRentalInvoices(int studentId) throws DBException {
        return rentalDB.findInvoices(studentId);
    }

    /**
     * Fiind all rented instrument by student.
     *
     * @param studentId
     * @return list of rented instrument.
     * @throws DBException
     */
    public List<? extends InstrumentDTO> getInstrumentsBy(int studentId) throws DBException {
        return instrumentDB.findInstrumentsBy(studentId);
    }

    /**
     * Find all students.
     *
     * @return list of students.
     * @throws DBException
     */
    public List<? extends StudentDTO> getStudents() throws DBException {
        return studentDB.findStudents();
    }
}
