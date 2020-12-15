package org.arifjehoh.View;

import org.arifjehoh.Controller.Controller;
import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Student;
import org.arifjehoh.Model.InstrumentDTO;
import org.arifjehoh.Model.RentalDTO;
import org.arifjehoh.Model.StudentDTO;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
    private static final String PROMPT = "> ";
    private final Controller controller;
    private final Scanner console = new Scanner(System.in);

    public Interpreter(Controller controller) {
        this.controller = controller;
    }

    public void handleCommands() {
        boolean keepReceivingCommands = true;
        while (keepReceivingCommands) {
            try {
                CommandLine cmd = new CommandLine(readNextLine());
                String status;
                switch (cmd.getCommand()) {
                    case HELP:
                        printCommands();
                        break;
                    case QUIT:
                        keepReceivingCommands = false;
                        break;
                    case LIST:
                        printAvailableInstruments(cmd.getParameter(0));
                        break;
                    case RENT:
                        status = controller.rentInstrument(createStudent(), cmd.getParameter(0));
                        System.out.println(status);
                        break;
                    case TERMINATE:
                        status = controller.terminateRental(cmd.getParameter(0), cmd.getParameter(1));
                        System.out.println(status);
                        break;
                    case ABOUT_ME:
                        printAbout(cmd);
                        break;
                    default:
                        System.out.println("illegal command.");
                }

            } catch (Exception exception) {
                System.out.println("Operation failed.");
                System.out.println(exception.getMessage());
                exception.printStackTrace();
            }
        }

    }

    private void printAbout(CommandLine cmd) throws DBException {
        if (!cmd.getParameter(0).equals("")) {
            int studentId = Integer.parseInt(cmd.getParameter(0));

            List<? extends RentalDTO> rentals = controller.getRentalInvoices(studentId);
            rentals.stream().map(rental -> "Rental ID: " + rental.getId() +
                    "\t| Student ID: " + rental.getStudentId() +
                    "\t| Due Date: " + rental.getDueDate() +
                    "\t\t| Total cost: " + rental.getTotalCost())
                    .forEach(System.out::println);

            System.out.println("+++++++++++++++++++++++++++++");

            List<? extends InstrumentDTO> instruments = controller.getInstrumentsBy(studentId);
            instruments.stream().map(instrument -> "Instrument ID: " + instrument.getId() +
                    "\t| Rental Id: " + instrument.getRentalId() +
                    "\t| Instrument: " + instrument.getType() +
                    "\t\t| Cost: " + instrument.getCost() +
                    "\t\t| Due Date: " + instrument.getDueDate())
                    .forEach(System.out::println);
        } else {
            System.out.println("Could not find information from your student id.");
        }
    }

    private void printAvailableInstruments(String cmd) throws DBException {
        if (cmd.equals("")) {
            List<? extends InstrumentDTO> instruments = controller.getAvailableInstruments();
            if (instruments != null) {
                instruments.stream().map(instrument -> "Instrument ID: " + instrument.getId() +
                        "\t| Instrument: " + instrument.getType() +
                        "\t\t| Cost: " + instrument.getCost()).forEach(System.out::println);
            }
        } else {
            System.out.println("Could not understand the command.");
        }
    }

    private void printCommands() {
        Arrays.stream(Command.values()).forEachOrdered(command -> {
            StringBuilder text = new StringBuilder().append(command.toString().toLowerCase());
            if (command != Command.ILLEGAL_COMMAND) {
                if (command == Command.RENT) {
                    text.append(" [INSTRUMENT_TYPE]");
                }
                if (command == Command.TERMINATE) {
                    text.append(" [RENTAL_ID] [INSTRUMENT_ID]");
                }
                if (command == Command.ABOUT_ME) {
                    text.append(" [STUDENT_ID]");
                }
                System.out.println(text);
            }
        });
    }

    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }

    /**
     * This is for mocking a student for testing.
     *
     * @return A instance of a student object.
     */
    private StudentDTO createStudent() {
        int studentId = 57;
        int age = 23;
        String city = "stockholm";
        String streetName = "danmarkvagen 33";
        String firstName = "Arif";
        String lastName = "Jehda-Oh";
        String ssn = "199410231234";
        return new Student.Builder(studentId, firstName, lastName, age, city, streetName, ssn).build();
    }
}
