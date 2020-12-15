package org.arifjehoh;

import org.arifjehoh.Controller.Controller;
import org.arifjehoh.Entity.DBException;
import org.arifjehoh.View.Interpreter;

public class Main {
    public static void main(String[] args) {
        try {
            new Interpreter(new Controller()).handleCommands();
        } catch (DBException cause) {
            System.out.println("Could not connect to School Database.");
            cause.printStackTrace();
        }
    }
}
