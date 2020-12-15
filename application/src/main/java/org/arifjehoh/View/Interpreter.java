package org.arifjehoh.View;

import org.arifjehoh.Controller.Controller;
import org.arifjehoh.Entity.DBException;
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

    public void handleCommands() throws DBException {
        boolean keepReceivingCommands = true;
        printStudentList();
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
                        if (!cmd.getParameter(0).equals("")) {
                            status = controller.rentInstrument(cmd.getParameter(0), cmd.getParameter(1));
                            System.out.println(status);
                        } else {
                            System.out.println("Could not understand the command.");
                        }
                        break;
                    case TERMINATE:
                        if (!cmd.getParameter(0).equals("")) {
                            status = controller.terminateRental(cmd.getParameter(0), cmd.getParameter(1));
                            System.out.println(status);
                        } else {
                            System.out.println("Could not understand the command.");
                        }
                        break;
                    case ABOUT_ME:
                        printAbout(cmd.getParameter(0));
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

    /**
     * Print out all students.
     *
     * @throws DBException
     */
    private void printStudentList() throws DBException {
        List<? extends StudentDTO> students = controller.getStudents();
        students.stream().map(student -> "Student Id: " + student.getId() + "\t| Full name: " + student.getFullName())
                .forEach(System.out::println);
    }

    /**
     * Print out all commands.
     */
    private void printCommands() {
        Arrays.stream(Command.values()).forEachOrdered(command -> {
            StringBuilder text = new StringBuilder().append(command.toString().toLowerCase());
            if (command != Command.ILLEGAL_COMMAND) {
                if (command == Command.LIST) {
                    text.append(" [INSTRUMENT_TYPE]");
                }
                if (command == Command.RENT) {
                    text.append(" [STUDENT_ID] [INSTRUMENT_TYPE]");
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

    /**
     * Print out all available instruments.
     * param contains what instrument type user is searching for.
     *
     * @param param if empty then then find all available instruments.
     * @throws DBException
     */
    private void printAvailableInstruments(String param) throws DBException {
        List<? extends InstrumentDTO> instruments;
        if (!param.equals("")) {
            instruments = controller.getAvailableInstruments(param);
        } else {
            instruments = controller.getAvailableInstruments("");
        }
        if (instruments != null) {
            instruments.stream().map(instrument -> "Instrument ID: " + instrument.getId() +
                    "\t| Instrument: " + instrument.getType() + "\t\t| Cost: " + instrument.getCost())
                    .forEach(System.out::println);
        } else {
            System.out.println("Could not understand the command.");
        }
    }

    /**
     * Print out rentals and rented instruments of student.
     *
     * @param id, student id.
     * @throws DBException
     */
    private void printAbout(String id) throws DBException {
        if (!id.equals("")) {
            int studentId = Integer.parseInt(id);
            printRentals(studentId);
            System.out.println("+++++++++++++++++++++++++++++");
            printInstruments(studentId);
        } else {
            System.out.println("Could not find information from your student id.");
        }
    }

    /**
     * Print out rental invoice of student.
     *
     * @param studentId, Id of student.
     * @throws DBException
     */
    private void printRentals(int studentId) throws DBException {
        List<? extends RentalDTO> rentals = controller.getRentalInvoices(studentId);
        rentals.stream().map(RentalDTO::toString).forEach(System.out::println);
    }

    /**
     * Print out rented instruments of student.
     *
     * @param studentId, Id of student.
     * @throws DBException
     */
    private void printInstruments(int studentId) throws DBException {
        List<? extends InstrumentDTO> instruments = controller.getInstrumentsBy(studentId);
        instruments.stream().map(InstrumentDTO::toString).forEach(System.out::println);
    }

    /**
     * Reads user input.
     *
     * @return user input.
     */
    private String readNextLine() {
        System.out.print(PROMPT);
        return console.nextLine();
    }
}
