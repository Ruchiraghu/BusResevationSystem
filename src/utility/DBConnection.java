package utility;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/bus_reservation_system";
    private static final String USER = "root";
    private static final String PASSWORD = "Ruchi@12345";
    private static Connection connection = null;

    private DBConnection(){}
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Database connected successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Unable to connect to the database.", e);
            }
        }
        return connection;
    }
    public static void closeConnection(){
    if (connection==null){
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Unable to close database connection");
        }
        System.out.println("Connection is closed");
    }
    }
}