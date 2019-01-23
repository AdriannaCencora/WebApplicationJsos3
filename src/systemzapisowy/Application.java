/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package systemzapisowy;

import databaseAccess.ConnectionManager;
import java.sql.*;

/**
 *
 * @author adrianna
 */
public class Application {
private static Connection con = null;
private static Statement stmt = null;
private static ResultSet rs = null;

 
    public static void main(String[] args) {
        
    con = ConnectionManager.getConnection();
    System.out.println("connected");
    
}
}