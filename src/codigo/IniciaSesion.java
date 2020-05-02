/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import Objetos.MiModeloUsuario;

/**
 *
 * @author Acer
 */
public class IniciaSesion extends conexionBaseDatos{

    public IniciaSesion(Connection _conexion) {
        conexion = _conexion;
    }
    
    public void Inicia_Sesion(){
        
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
