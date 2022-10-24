/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller_pkg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
/**
 *
 * @author JEAN SAID
 */
public class Conexion {
  Connection connection;
public Conexion() {
try {
Class.forName("com.mysql.jdbc.Driver");
connection =DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_db?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC","root", "00875044500025");
if (connection != null) {
System.out.println("Conexión exitosa");
}
} catch (ClassNotFoundException | SQLException e) {
System.out.println("Conexión fallida");
    System.out.println(">>>> " + e);
}
}
public Connection getConnection(){
return connection;
}
  
}