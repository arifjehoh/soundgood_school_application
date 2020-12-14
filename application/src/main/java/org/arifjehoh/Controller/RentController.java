package org.arifjehoh.Controller;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Rental;
import org.arifjehoh.Integreation.InstrumentDAO;
import org.arifjehoh.Integreation.StudentDTO;
import org.arifjehoh.Model.InstrumentDTO;
import org.arifjehoh.Integreation.RentalDAO;
import org.arifjehoh.Model.RentalDTO;

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

    public void rentInstrument(StudentDTO student, String timePeriod, String instrument) throws DBException {
        RentalDTO rental = rentalDB.findInvoice(student.getId(),timePeriod);
        if (rental == null) {
            rentalDB.createInvoice(student);
            rental = rentalDB.findInvoice(student.getId(),timePeriod);
            System.out.println(rental);
        }
        boolean canRent = instrumentDB.canRentInstrument(rental.getId(), rental.getDueDate());
        if (canRent) {
            instrumentDB.rentInstrument(rental.getId(), student.getId(), instrument, rental.getDueDate());
            rentalDB.updateInvoice(rental);
        }
    }

    public void terminateRental() {
    }
}
