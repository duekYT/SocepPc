/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.sql.Connection;
import java.sql.DriverManager;


public class Conexion {
    Connection con;
    String url="jdbc:mysql://localhost:3306/socep";
    String user="root";
    String pass="";
    public Connection Conectar(){
        try {
             Class.forName("com.mysql.jdbc.Driver");
             con=DriverManager.getConnection(url,user,pass);
             System.out.println("exito al conectar " + url);
        } catch (Exception e) {
            System.out.println("error "+e);
        }
        return con;
        }
    }

