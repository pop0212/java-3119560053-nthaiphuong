package DAL;
import DTO.Customer;
import java.sql.*;

public class Database {
    public Connection conn = null;
    
    public Database(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/java";
            String username = "root";
            String password = "";
            conn = DriverManager.getConnection(url, username, password);
            System.out.println("Connect sucessfully !");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
