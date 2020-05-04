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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class ConsultasSql extends conexionBaseDatos{
    
    public ConsultasSql(Connection _conexion) {
        conexion = _conexion;
    }

    public int ExiteUsuario(String usr){
        PreparedStatement ps = null;
        ResultSet resultado = null;
        
        String sql = "SELECT count(Id) FROM usuario WHERE Nombre = ?";
        
        try {
            ps = conexion.prepareStatement(sql);
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

    @Override
    public boolean ingresar(List<Object> values, String tabla, String campos) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean borrar(int id_fila, String tabla, String id_pk) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean actualizar(List<Object> values, String tabla, String campos, String id_pk) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public DefaultTableModel selecionarLista(String tabla, String campos) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Object> Seleccion(int id_fila, String tabla, String campos, String id_pk) throws SQLException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
