package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/underwriting_engine";
    private static final String user = "root";
    private static final String pass = "Hemasri@7107";
    public static Connection getConnection(){
        try{
            return DriverManager.getConnection(url,user,pass);
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
