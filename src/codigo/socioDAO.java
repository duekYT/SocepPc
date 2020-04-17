/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author Alcantara Trejo
 */
public class socioDAO {
    PreparedStatement ps;
    ResultSet rs;
    usuario ev = new usuario();
    Conexion con=new Conexion();
    Connection acceso;

    public usuario Validarusuario(String correo,String contrasenia){
          String sql="select socios.Correo, socios.Contraseña from socios WHERE socios.Contraseña=? and socios.Correo=?";
        try {
            acceso=con.Conectar();
            ps=acceso.prepareStatement(sql);
            ps.setString(1, correo);
            ps.setString(2, contrasenia);
            rs=ps.executeQuery();
            while(rs.next()){
                ev.setCorreo(rs.getString(1));
                ev.setContrasenia(rs.getString(2));
            }
        } catch (Exception e) {
        }
        return ev;
    }
    
    
}
