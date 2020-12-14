package org.arifjehoh.Controller;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Integreation.InstrumentDAO;
import org.arifjehoh.Integreation.RentalDAO;
import org.arifjehoh.Model.InstrumentDTO;
import org.arifjehoh.Model.RentalDTO;
import org.arifjehoh.Model.StudentDTO;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RentController {
    private final InstrumentDAO instrumentDB;
    private final RentalDAO rentalDB;

    public RentController() throws DBException {
        instrumentDB = new InstrumentDAO();
        rentalDB = new RentalDAO();
    }

    public List<? extends InstrumentDTO> getAvailableInstruments() throws DBException {
        try {
            return instrumentDB.findAvailableInstruments();
        } catch (Exception exception) {
            throw new DBException("Unable to list available instruments.", exception);
        }
    }

    public String rentInstrument(StudentDTO student, String instrument) throws DBException {
        String timePeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM"));
        RentalDTO rental = getRental(student, timePeriod);
        boolean canRent = instrumentDB.canRentInstrument(rental.getId(), rental.getDueDate());
        if (canRent) {
            instrumentDB.rentInstrument(rental.getId(), student.getId(), instrument, rental.getDueDate());
            rentalDB.updateInvoice(rental.getId());
            return "You have successfully rented your instrument.";
        }
        return "You have failed to rent your instrument, due to multiple rented instrument or instrument not found.";
    }

    private RentalDTO getRental(StudentDTO student, String timePeriod) throws DBException {
        RentalDTO rental = rentalDB.findInvoice(student.getId(), timePeriod);
        if (rental == null) {
            rentalDB.createInvoice(student);
            rental = rentalDB.findInvoice(student.getId(), timePeriod);
        }
        return rental;
    }

    public String terminateRental(String rentalId, String instrumentId) throws DBException {
        String status = instrumentDB.terminateRental(rentalId, instrumentId);
        return status;
    }
}
