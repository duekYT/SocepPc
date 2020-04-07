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

    public usuario Validarusuario(String telefono,String contrasenia){
          String sql="select socios.Telefono, socios.Contraseña from socios WHERE socios.Contraseña=? and socios.Telefono=?";
        try {
            acceso=con.Conectar();
            ps=acceso.prepareStatement(sql);
            ps.setString(1, telefono);
            ps.setString(2, contrasenia);
            rs=ps.executeQuery();
            while(rs.next()){
                ev.setTelefono(rs.getString(1));
                ev.setContrasenia(rs.getString(2));
            }
        } catch (Exception e) {
        }
        return ev;
    }
    
    
}
