/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import Objetos.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Acer
 */
public class ConsultasSql extends Conexion{
    public boolean RegistrarUsuario(Usuario usr){
        PreparedStatement ps = null;
        Connection con = Conectar();
        
        String sql = "INSERT INTO usuario(Nombre, Correo_electronico, lada, Telefono, Contraseña, Id_roles) VALUES (?,?,?,?,?,?)";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr.getNombre());
            ps.setString(2, usr.getCorreo());
            ps.setString(3, usr.getLada());
            ps.setString(4, usr.getTelefono());
            ps.setString(5, usr.getContrasenia());
            ps.setInt(6, usr.getRol());
            ps.execute();
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSql.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
    }
    
    public int ExiteUsuario(String usr){
        PreparedStatement ps = null;
        ResultSet resultado = null;
        Connection con = Conectar();
        
        String sql = "SELECT count(Id) FROM usuario WHERE Nombre = ?";
        
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, usr);
            resultado = ps.executeQuery();
            if(resultado.next()){
                return resultado.getInt(1);
            }
            
            return 1;
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSql.class.getName()).log(Level.SEVERE, null, ex);
            return 1;
        }
    }
    
    public boolean esEmail(String correo) {

        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correo);

        return mather.find();

    }
}
