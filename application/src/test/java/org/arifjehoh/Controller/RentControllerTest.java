package org.arifjehoh.Controller;

import junit.framework.TestCase;
import org.arifjehoh.Entity.DBException;

public class RentControllerTest extends TestCase {

    private RentController controller;
    public void setUp() throws Exception {
        super.setUp();
        controller = new RentController();
    }

    public void testAvailableInstruments() throws DBException {
        assertNotNull(controller.getAvailableInstruments());
    }
}