package Conexiones;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.JOptionPane;

public class Conexion {
    private static Connection conexion = null;
    private static boolean status = false;
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static boolean login(String username, String password) {
        status = false;

        try {
            boolean is_valid = Procedimientos.validatePassword(username, password);
            status = is_valid;
        }
        catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de validación", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        
        return status;
    }
    
    public static Connection getConexion() {
        return conexion;
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void closeConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    @SuppressWarnings("CallToPrintStackTrace")
    public static void startConexion() {
        status = false;
        
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } 
        catch (ClassNotFoundException e) {
            JOptionPane.showMessageDialog(null, "No se pudo establece la conexion... revisar Driver. " + e.getMessage(),
            "Error de Conexion",JOptionPane.ERROR_MESSAGE);
        }
        
        try {
            Properties props = new Properties();
            FileInputStream config_file = new FileInputStream("config.properties");
            props.load(config_file);
            
            String url = props.getProperty("db_path").replaceAll("\"", "") + props.getProperty("db_name").replaceAll("\"", "");
            String username = props.getProperty("db_username").replaceAll("\"", "");
            String password = props.getProperty("db_password").replaceAll("\"", "");
            
            conexion = DriverManager.getConnection(url, username, password);
            status = true;
        } 
        catch (SQLException e) {
             JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de conexión", JOptionPane.ERROR_MESSAGE);
             e.printStackTrace();
        }
        catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de configuración", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } 
        catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error de credenciales", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    public static boolean getStatus(){
        return status;
    }
}