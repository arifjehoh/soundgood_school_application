package org.arifjehoh.Model;

import org.arifjehoh.Entity.DBException;
import org.arifjehoh.Entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {
    private static final String TABLE_NAME = "student";
    private static final String ATTR_STUDENT_ID = "student_id";
    private static final String ATTR_FIRST_NAME = "first_name";
    private static final String ATTR_LAST_NAME = "last_name";
    private static final String ATTR_AGE = "age";
    private static final String ATTR_CITY = "city";
    private static final String ATTR_STREET_NAME = "street_name";
    private static final String ATTR_SSN = "social_security_number";
    private Connection connection;
    private PreparedStatement findStudentsStmt;

    public StudentDAO() throws DBException {
        try {
            createConnection();
            prepareStatements();
        } catch (ClassNotFoundException | SQLException exception) {
            throw new DBException("Could not connect to datasource.", exception);
        }
    }

    private void createConnection() throws ClassNotFoundException, SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/school", "arif", "1234");
        connection.setAutoCommit(false);
    }

    private void prepareStatements() throws SQLException {
        findStudentsStmt = connection.prepareStatement("SELECT * FROM " + TABLE_NAME);
    }

    public List<? extends StudentDTO> getStudents() throws DBException {
        List<Student> students = new ArrayList<>();
        try (ResultSet set = findStudentsStmt.executeQuery()) {
            while (set.next()) {
                Student student = new Student.Builder(set.getInt(ATTR_STUDENT_ID),set.getString(ATTR_FIRST_NAME),
                        set.getString(ATTR_LAST_NAME),set.getInt(ATTR_AGE),set.getString(ATTR_CITY),
                        set.getString(ATTR_STREET_NAME), set.getString(ATTR_SSN)).build();
                students.add(student);
            }
        } catch (SQLException cause) {
            new DBException().handle(connection, "Could not list students.", cause);
        }
        return students;
    }
}
