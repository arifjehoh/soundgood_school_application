package org.arifjehoh.View;

import org.arifjehoh.Controller.RentController;
import org.arifjehoh.Entity.Student;
import org.arifjehoh.Model.InstrumentDTO;
import org.arifjehoh.Model.StudentDTO;

import java.util.List;
import java.util.Scanner;

public class Interpreter {
    private static final String PROMPT = "> ";
    private final RentController rentController;
    private final Scanner console = new Scanner(System.in);
    private boolean keepRecevingCommands = false;

    public Interpreter(RentController rentController) {
        this.rentController = rentController;
    }

    public void handleCommands() {
        keepRecevingCommands = true;
        String status = "";
        while (keepRecevingCommands) {
            try {
                CommandLine cmd = new CommandLine(readNextLine());
                switch (cmd.getCommand()) {
                    case HELP:
                        for (Command command : Command.values()) {
                            StringBuilder text = new StringBuilder().append(command.toString().toLowerCase());
                            if (command == Command.ILLEGAL_COMMAND) {
                                continue;
                            }
                            if (command == Command.RENT) {
                                text.append(" [INSTRUMENT_TYPE]");
                            }
                            if (command == Command.TERMINATE) {
                                text.append(" [RENTAL_ID] [INSTRUMENT_ID]");
                            }

                            System.out.println(text);
                        }
                        break;
                    case QUIT:
                        keepRecevingCommands = false;
                        break;
                    case LIST:
                        List<? extends InstrumentDTO> instruments = null;
                        if (cmd.getParameter(0).equals("")) {
                            instruments = rentController.getAvailableInstruments();
                        }
                        for (InstrumentDTO instrument : instruments) {
                            System.out.println("Instrument ID: " + instrument.getId() +
                                    "\t| Instrument: " + instrument.getType() +
                                    "\t\t| Cost: " + instrument.getCost());
                        }
                        break;
                    case RENT:
                        status = rentController.rentInstrument(createStudent(), cmd.getParameter(0));
                        System.out.println(status);
                        break;
                    case TERMINATE:
                        status = rentController.terminateRental(cmd.getParameter(0), cmd.getParameter(1));
                        System.out.println(status);
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
        String city = "stockholm";
        String streetName = "danmarkvagen 33";
        String firstName = "Arif";
        String lastName = "Jehda-Oh";
        int age = 23;
        String ssn = "199410231234";
        return new Student.Builder(studentId, firstName, lastName, age, city, streetName, ssn).build();
    }
}
