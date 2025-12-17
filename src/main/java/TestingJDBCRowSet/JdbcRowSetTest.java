package TestingJDBCRowSet;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.JdbcRowSet;
import javax.sql.rowset.RowSetProvider;
import javax.sql.rowset.RowSetFactory;

public class JdbcRowSetTest {

    static final String DATABASE_URL = "jdbc:mysql://localhost/javalab";
    static final String USERNAME = "root";
    static final String PASSWORD = "";

    public JdbcRowSetTest() {
        try {
            RowSetFactory factory = RowSetProvider.newFactory();
//            JdbcRowSet rowSet = factory.createJdbcRowSet();

            CachedRowSet rowSet = factory.createCachedRowSet();
            rowSet.setUrl(DATABASE_URL);
            rowSet.setUsername(USERNAME);
            rowSet.setPassword(PASSWORD);
//            rowSet.setAutoCommit(true);
            rowSet.setCommand("SELECT * FROM authors");
            rowSet.execute();


//          insert
//            rowSet.moveToInsertRow();
//            rowSet.updateInt("author_id", 1);
//            rowSet.updateString("name","Ishan");
//            rowSet.updateString("email","rabineba@gmail.com");
//            rowSet.updateString("bio","Rabi");
//            rowSet.insertRow();

            // update
//            if (rowSet.absolute(4)) {
//                rowSet.updateString("name", "Ishan");
//                rowSet.updateRow();
//                System.out.println("Row Updated");
//            }else {
//                System.out.println("Row Not Updated");
//            }
            if (rowSet.absolute(4)) {
                rowSet.updateString("name", "Suhile");
                rowSet.updateRow();
                rowSet.acceptChanges();
                System.out.println("rowSet updated");
            }else{
                System.out.println("rowSet NOT updated");
            }
            rowSet.beforeFirst();


            ResultSetMetaData metaData = rowSet.getMetaData();
            int columnCount = metaData.getColumnCount();

            System.out.println("Authors Table of Books Database\n");

            // Print column headers
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("%-15s", metaData.getColumnName(i));
            }
            System.out.println();


            while (rowSet.next()) {
                for (int col = 1; col <= columnCount; col++) {
                    System.out.printf("%-15s", rowSet.getObject(col));
                }
                System.out.println();
            }

            rowSet.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new JdbcRowSetTest();
    }
}
