package org.arifjehoh.Controller;

import junit.framework.TestCase;
import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Student;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RentControllerTest extends TestCase {

    private RentController controller;
    public void setUp() throws Exception {
        super.setUp();
        controller = new RentController();
    }

    public void testAvailableInstruments() throws DBException {
        assertNotNull(controller.getAvailableInstruments());
    }

    public void testRentInstrument() throws DBException {
        int studentId = 57;
        String city = "stockholm";
        String zipCode = "12345";
        String streetName = "danmarkvagen 33";
        String country = "sweden";
        String dueDate = LocalDateTime.now().toString();

        String firstName = "Arif";
        String lastName = "Jehda-Oh";
        int age = 23;
        String ssn = "199410231234";
        Student student = new Student.Builder(studentId, firstName, lastName, age, city, streetName, ssn).build();

        String timePeriod = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM"));
        controller.rentInstrument(student, timePeriod, "guitar");
    }

}