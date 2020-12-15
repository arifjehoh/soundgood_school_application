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
        assertNotNull(controller.getAvailableInstruments());
    }

    public void testRentInstrument() throws DBException {
        StudentDTO student = createStudent();
        String studentId = String.valueOf(student.getId());
        controller.rentInstrument(studentId, "guitar");
    }

    public void testTerminateRental() throws DBException {
        String rentalId = "1";
        String instrumentId = "100";
        controller.terminateRental(rentalId, instrumentId);
    }

}