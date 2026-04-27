package util;
import java.sql.*;
public class DBConnection {
    private static final String url = "jdbc:mysql://localhost:3306/employee_management_system";
    private static final String user = "root";
    private static final String pass = "Hemasri@7107";
    public static Connection getConnectionObj(){
        try{
            Connection con = DriverManager.getConnection(url,user,pass);
            return con;
        }
        catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
