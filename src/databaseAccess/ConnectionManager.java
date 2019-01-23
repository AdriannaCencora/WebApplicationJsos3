/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseAccess;
import java.sql.*;

/**
 *
 * @author adrianna
 */
public class ConnectionManager {
    static String url = "jdbc:mysql://localhost:3306/jsos3";
    static String driverName = "com.mysql.jdbc.Driver";   
    static String username = "root";
    static String password = "BD2018";
    
    private static Connection con;

      public static Connection getConnection() {
        try {   
            Class.forName(driverName);
            
            try {
                con = DriverManager.getConnection(url, username, password);
            } catch (SQLException ex) {
                throw new RuntimeException("Error connecting to the database", ex);
            }
            
        } catch (ClassNotFoundException ex) {
            System.out.println("Driver not found.");
        }
        return con;
    }


}
