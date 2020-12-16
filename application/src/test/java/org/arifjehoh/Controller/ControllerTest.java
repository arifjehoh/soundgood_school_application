package org.arifjehoh.Controller;

import junit.framework.TestCase;
import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Student;
import org.arifjehoh.Model.StudentDTO;

public class ControllerTest extends TestCase {

    private Controller controller;

    public void setUp() throws Exception {
        super.setUp();
        controller = new Controller();
    }

    private StudentDTO createStudent() {
        int studentId = 57;
        String city = "stockholm";
        String streetName = "danmarkvagen 33";
        String firstName = "Arif";
        String lastName = "Jehda-Oh";
        int age = 23;
        String ssn = "199410231234";
        Student student = new Student.Builder(studentId, firstName, lastName, age, city, streetName, ssn).build();
        return student;
    }

    public void testAvailableInstruments() throws DBException {
        assertNotNull(controller.getAvailableInstruments(""));
        assertNotNull(controller.getAvailableInstruments("guitar"));
        assertNotNull(controller.getAvailableInstruments("viola"));
        assertNotNull(controller.getAvailableInstruments("piano"));
        assertNotNull(controller.getAvailableInstruments("keyboard"));
        assertEquals(0, controller.getAvailableInstruments("empty").size());
    }

    public void testTerminateRental() throws DBException {
        String actual = controller.terminateRental("27", "10");
        assertEquals("You have successfully terminate your rental of instrument.", actual);
        actual = controller.terminateRental("27", "11");
        assertEquals("You have successfully terminate your rental of instrument.", actual);
    }

    public void testRentInstrument() throws DBException {
        String actual = controller.rentInstrument("5", "guitar");
        assertEquals("You have successfully rented your instrument.", actual);
        actual = controller.rentInstrument("5", "guitar");
        assertEquals("You have successfully rented your instrument.", actual);
        actual = controller.rentInstrument("5", "guitar");
        assertEquals("You have failed to rent your instrument, due to multiple rented instrument or instrument not " +
                "found.", actual);
    }

}