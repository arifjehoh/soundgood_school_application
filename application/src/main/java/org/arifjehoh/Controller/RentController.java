package org.arifjehoh.Controller;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.InstrumentDAO;
import org.arifjehoh.Entity.InstrumentDTO;
import org.arifjehoh.Entity.RentalDAO;

import java.util.List;

public class RentController {
    private final InstrumentDAO instrumentDAO;
    private final RentalDAO rentalDAO;

    public RentController() throws DBException {
        instrumentDAO = new InstrumentDAO();
        rentalDAO = new RentalDAO();
    }

    public List<? extends InstrumentDTO> getAvailableInstruments() throws DBException {
        try {
            return instrumentDAO.findAvailableInstruments();
        } catch (Exception exception) {
            throw new DBException("Unable to list available instruments.", exception);
        }
    }

    public void rentInstrument() {

    }

    public void terminateRental() {
    }
}
