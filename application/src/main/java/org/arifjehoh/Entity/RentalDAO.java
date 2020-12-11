package org.arifjehoh.Entity;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RentalDAO {

    private Connection connection;

    public RentalDAO() throws DBException {
        try {
            createConnection();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DBException("Could not connecto to datasource.", exception);
        }
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "arif" , "1234");
        connection.setAutoCommit(false);
    }
}
