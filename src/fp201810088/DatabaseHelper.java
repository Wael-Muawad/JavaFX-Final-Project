package fp201810088;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DatabaseHelper {

    public static Connection getConnection() {
        Connection con = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/course_db";
            String User = "root";
            String password = "";
            con = DriverManager.getConnection(url, User, password);
                      
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
        }

        return con;
    }
    
    public static Statement getStatement(Connection con){
        Statement statment;
        try {
            statment = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(DatabaseHelper.class.getName()).log(Level.SEVERE, null, ex);
            new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.CLOSE).show();
            return null;
        }
        return statment;
    }
}
