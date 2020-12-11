package org.arifjehoh;

import java.sql.*;

/**
 * Hello world!
 */
public class App {
    private PreparedStatement findAllInstruments;
    public static void main(String[] args) {
        System.out.println("Hello World!");
        new App().accessDB();
    }

    private void accessDB() {
        try (Connection connection = createConnection()) {
            prepareStatement(connection);
            getInstruments();

        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void getInstruments() {
        try (ResultSet result = findAllInstruments.executeQuery()) {
            while (result.next()) {
                System.out.println(result.getString(4));
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

    private void prepareStatement(Connection connection) throws SQLException {
       findAllInstruments = connection.prepareStatement("SELECT * FROM instrument_rental");
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "arif", "1234");
    }
}
