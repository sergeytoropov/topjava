package ru.javawebinar.topjava.repository.jdbc;


import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JDBCStatmentSelectExample {

    //private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_DRIVER = "org.postgresql.Driver";
    //private static final String DB_CONNECTION = "jdbc:oracle:thin:@localhost:1521:MKYONG";
    private static final String DB_CONNECTION = "jdbc:postgresql://127.0.0.1:5432/topjava";
    private static final String DB_USER = "postgres";
    private static final String DB_PASSWORD = "123456";

    public static void main(String[] argv) {

        try {

            selectRecordsFromDbUserTable();

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

    }

    private static void selectRecordsFromDbUserTable() throws SQLException {

        Connection dbConnection = null;
        Statement statement = null;

        String selectTableSQL = "SELECT ID, NAME from users";

        try {
            dbConnection = getDBConnection();
            statement = dbConnection.createStatement();

            System.out.println(selectTableSQL);

            // execute select SQL stetement
            ResultSet rs = statement.executeQuery(selectTableSQL);

            while (rs.next()) {

                String userid = rs.getString("ID");
                String username = rs.getString("NAME");

                System.out.println("id : " + userid);
                System.out.println("name : " + username);

            }

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        } finally {

            if (statement != null) {
                statement.close();
            }

            if (dbConnection != null) {
                dbConnection.close();
            }

        }

    }

    private static Connection getDBConnection() {

        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

}
