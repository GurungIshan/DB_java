package org.example;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.DriverManager;
import  java.sql.PreparedStatement;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class preparedStatement {
    static final String DATABASE_URL = "jdbc:mysql://localhost/javalab";

    public static void main(String[] args) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(DATABASE_URL, "root", "");
            connection.setAutoCommit(false);

            PreparedStatement pstmt = connection.prepareStatement(
                    "INSERT INTO authors (author_id, name, email, bio) VALUES (?,?,?,?)"
            );

            pstmt.setInt(1, 19);
            pstmt.setString(2, "Ishan");
            pstmt.setString(3, "Ishangrg2@gmail.com");
            pstmt.setString(4, "bio 1");
            pstmt.addBatch();

            pstmt.setInt(1, 20);
            pstmt.setString(2, "Babu");
            pstmt.setString(3, "Babu3452@gmail.com");
            pstmt.setString(4, "bio 2");
            pstmt.addBatch();

            int[] updateCounts = pstmt.executeBatch();
            connection.commit();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(
                    "SELECT author_id, name, email, bio FROM authors"
            );

            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Authors Table:\n");

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
