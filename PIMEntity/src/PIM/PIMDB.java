package PIM;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PIMDB {
	Connection conn = null;
    static String username = new String("root");
    static String password = new String("ycs199924");

    public static Connection conDB()
    {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/pim?useSSL=false&serverTimezone=UTC",username,password);
            System.out.println("Connect Mysql successfully!");
            return con;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println("ConnectionUtil : " + ex.getMessage());
           return null;
        }
    }
    //make sure you add the lib
}