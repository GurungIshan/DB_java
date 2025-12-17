package org.example;

import java.sql.*;

public class batch {
    static final String DATABASE_URL = "jdbc:mysql://localhost/javalab";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            connection.setAutoCommit(false);

            statement = connection.createStatement(
                    ResultSet.TYPE_SCROLL_SENSITIVE,
                    ResultSet.CONCUR_UPDATABLE
            );

//            statement.addBatch(
//                    "INSERT INTO authors (author_id, name, email, bio) " +
//                            "VALUES (15, 'Sushil', 'sushil@gmail.com', 'bho')"
//            );
//            statement.addBatch(
//                    "INSERT INTO authors (author_id, name, email, bio) " +
//                            "VALUES (16, 'Udghosh', 'udghosh@gmail.com', 'bho')"
//            );
//            statement.addBatch(
//                    "INSERT INTO authors (author_id, name, email, bio) " +
//                            "VALUES (17, 'Ishan', 'ishan345@gmail.com', 'bho')"
//            );

            statement.addBatch(
                    "UPDATE authors SET bio = 'updated bio here' WHERE author_id = 17"
            );

            statement.addBatch(
                    "DELETE FROM authors WHERE author_id = 15"
            );
            statement.executeBatch();
            connection.commit();

            resultSet = statement.executeQuery(
                    "SELECT author_id, name, email, bio FROM authors"
            );

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Authors Table of Books Database:\n");

            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-12s\t", metaData.getColumnName(i));
            System.out.println();

            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-12s\t", resultSet.getObject(i));
                System.out.println();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
