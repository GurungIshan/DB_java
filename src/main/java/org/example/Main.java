package org.example;

import javax.xml.transform.Result;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static final String DATABASE_URL = "jdbc:mysql://localhost/javalab";

    public static void main(String[] args) throws ClassNotFoundException {
        Connection connection = null; // manages connection

        Statement statement = null; // query statement
        ResultSet resultSet = null; // manages results
// connect to database books and query database
        try {
// establish connection to database
            connection = DriverManager.getConnection(
                    DATABASE_URL, "root", "");
// create Statement for querying database
            statement = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_UPDATABLE);
// query database

            // in tabular form, row data
            resultSet = statement.executeQuery(
                    "SELECT author_id, name, email, bio FROM authors"
            );

// process query results
            // column data
            ResultSetMetaData metaData = resultSet.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            System.out.println("Authors Table of Books Database:\n");
            for (int i = 1; i <= numberOfColumns; i++)
                System.out.printf("%-8s\t", metaData.getColumnName(i));
            System.out.println();

            ResultSet uprs = statement.executeQuery(
                    "Select * from authors"
            );

            uprs.moveToInsertRow();
            uprs.updateInt("author_id", 12);
            uprs.updateString("name", "Udghosh");
            uprs.updateString("email","hello23@gmail.com");
            uprs.updateString("bio","Shakespeare");
            uprs.insertRow();
            uprs.beforeFirst();

            resultSet = statement.executeQuery(
                    "SELECT author_id, name, email, bio FROM authors"
            );

//            while (resultSet.next()) {
//                resultSet.updateString("name","ishan@gmail.com");
//                resultSet.updateRow();
//            }
//            resultSet.beforeFirst();

            while (resultSet.next()) {
                for (int i = 1; i <= numberOfColumns; i++)
                    System.out.printf("%-8s\t", resultSet.getObject(i));
                System.out.println();
            } // end while
        } // end try
        catch (SQLException sqlException) {
            sqlException.printStackTrace();
        } // end catch
        finally // ensure resultSet, statement and connection are closed
        {
            try {
                resultSet.close();
                statement.close();
                connection.close();
            } // end try
            catch (Exception exception) {
                exception.printStackTrace();
            } // end catch
        }
    }
}

