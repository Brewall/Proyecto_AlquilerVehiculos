package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dbrent_a_car";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    // Método para obtener una nueva conexión cada vez que se invoque
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
