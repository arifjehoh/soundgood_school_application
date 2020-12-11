package org.arifjehoh;

import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        new App().accessDB();
    }

    private void accessDB() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school","arif","1234");
            System.out.println("Connected!");
        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }
}

