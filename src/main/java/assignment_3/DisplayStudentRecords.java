package assignment_3;

import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import java.sql.SQLException;

public class DisplayStudentRecords {

    public static void main(String[] args) {

        try {
            // Create JdbcRowSet object
            JdbcRowSet rowSet = RowSetProvider.newFactory().createJdbcRowSet();

            // Set database connection details
            rowSet.setUrl("jdbc:mysql://localhost:3306/collegeMIS");
            rowSet.setUsername("root");        // change if needed
            rowSet.setPassword("");    // change if needed

            // Set SQL query
            rowSet.setCommand(
                    "SELECT student_ID, student_firstname, student_lastname FROM student"
            );

            // Execute query
            rowSet.execute();

            // Display records
            System.out.println("Student Records:");
            System.out.println("----------------------------");

            while (rowSet.next()) {
                int id = rowSet.getInt("student_ID");
                String firstName = rowSet.getString("student_firstname");
                String lastName = rowSet.getString("student_lastname");

                System.out.println(
                        "ID: " + id +
                                ", First Name: " + firstName +
                                ", Last Name: " + lastName
                );
            }

            // Close RowSet
            rowSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

