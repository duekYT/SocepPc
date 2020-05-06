/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import utilidades.*;
import java.sql.SQLException;
import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Acer
 */
public class ConsultasCrud extends conexionBaseDatos{

    public ConsultasCrud(Connection _conexion) {
        conexion = _conexion;
    }
    

    @Override
    public boolean ingresar( List<Object> values, String tabla, String campos) throws SQLException {
        String valores = "";
        int contador = 0;
        
        int tamanioLista = values.size();
        
        for (int i = 0; i < tamanioLista; i++) {
            if(i == 0){
                valores = "?";
            }else{
                valores += ",?";
            }
        }
        
        String query = "INSERT INTO " + tabla + " (" + campos + ") VALUES" + " (" + valores + ");";
        
        PreparedStatement ps = conexion.prepareStatement(query);
        
        for (int j = 0; j <= tamanioLista - 1; j++) {
            contador = j+1;
            ps.setObject(contador, values.get(j));
        }
        
        int filasAgregadas = ps.executeUpdate();
        ps.close();
        return filasAgregadas > 0;
    }

    @Override
    public boolean borrar(int id_fila, String tabla, String id_pk) throws SQLException {
        String query = "DELETE FROM " + tabla + " WHERE " + id_pk + " = ?;";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setObject(1, id_fila);
        int filaEliminada = ps.executeUpdate();
        ps.close();
        return filaEliminada > 0;
    }

    @Override
    public boolean actualizar(List<Object> values, String tabla, String campos, String id_pk) throws SQLException {
        int contador = 0;
        int tamanioLista = values.size();
        String query = "UPDATE " + tabla + " SET " + campos + " WHERE " + id_pk + " = ?;";
        System.out.println(query);
        PreparedStatement ps = conexion.prepareStatement(query);
        
        for (int j = 0; j < tamanioLista; j++) {
            contador = j+1;
            ps.setObject(contador, values.get(j));
        }
        
        int filasActualizadas = ps.executeUpdate();
        ps.close();
        return filasActualizadas > 0;
    }

    @Override
    public DefaultTableModel selecionarLista(String tabla, String campos) throws SQLException {
        String query = "SELECT " + campos + " FROM " + tabla +";";
        PreparedStatement ps = conexion.prepareStatement(query);
        ResultSet resultado = ps.executeQuery();
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        utilidades.ConversorResultSetADefaultTableModel.rellena(resultado, modelo);
        ps.close();
        resultado.close();
        return modelo;
    }

    @Override
    public List<Object> Seleccion(int id_fila, String tabla, String campos, String id_pk) throws SQLException {
        String query = "SELECT " + campos + " FROM " + tabla +" WHERE " + id_pk + " = ? ;";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setObject(1, id_fila);
        ResultSet resultado = ps.executeQuery();
        ResultSetMetaData md = resultado.getMetaData();
        
        int columnas = md.getColumnCount();
        List<Object> filalista = new ArrayList<>();
        
        while(resultado.next()){
            Map<String,Object> fila = new HashMap<String,Object>();
            for (int i = 0; i <= columnas; i++) {
                fila.put(md.getColumnLabel(i).toLowerCase(), resultado.getObject(i));
            }
            filalista.add(fila);
        }
        
        ps.close();
        resultado.close();
        return filalista;
    }
    
    public int CompruebaExistencias (Object valor, String Tabla, String Campo, String Id){
        
        PreparedStatement ps = null;
        ResultSet resultado = null;
        
        String sql = "SELECT count(" + Id + ") FROM " + Tabla + " WHERE "+ Campo + " = ?;";
        
        try {
            ps = conexion.prepareStatement(sql);
            ps.setObject(1, valor);
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
    
    public int Seleccionar_id (String tabla, String campos){
        
        int id = 0;
        String query = "SELECT " + campos + " FROM " + tabla + " ;";
        
        try {
            
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet resultado = ps.executeQuery();
            
            while( resultado.next() ){
                id = resultado.getInt( campos );
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSql.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        
         return id;
    }
    
    public List<String> SeleccionInformacion (int respuesta){
        
        String query = "SELECT direcciones_socios.Estado, direcciones_socios.Municipio, "
                + "direcciones_socios.Direccion, socios.Nombre, socios.lada, socios.Telefono, socios.Correo " 
                + "FROM socios INNER JOIN direcciones_socios on socios.Id_Direcciones = direcciones_socios.Id WHERE socios.Id = " 
                + respuesta + ";";
        List<String> milista = new ArrayList<>();
        
            try {
            
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet resultado = ps.executeQuery();
            int contador = 0;
            if(resultado.next()){
                milista.add(resultado.getString("socios.Nombre"));
                milista.add(resultado.getString("socios.lada"));
                milista.add(resultado.getString("socios.Telefono"));
                milista.add(resultado.getString("socios.Correo"));
                milista.add(resultado.getString("direcciones_socios.Estado"));
                milista.add(resultado.getString("direcciones_socios.Municipio"));
                milista.add(resultado.getString("direcciones_socios.Direccion"));
                while( resultado.next() ){
                    resultado.getInt(milista.get(contador));
                    contador++;
                }
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSql.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        
         return milista;
    }
    
    public boolean esEmail(String correo) {

        // Patrón para validar el email
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");

        Matcher mather = pattern.matcher(correo);

        return mather.find();

    }
    
    public String SeleccinaUnCampo (String tabla, String campos, String id, int respuesta){
        
        String Campo = "";
        String query = "SELECT " + campos + " FROM " + tabla + " WHERE "+id+" = "+respuesta+";";
        try {
            
            PreparedStatement ps = conexion.prepareStatement(query);
            ResultSet resultado = ps.executeQuery();
            
            while( resultado.next() ){
                Campo = resultado.getString(campos );
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(ConsultasSql.class.getName()).log(Level.SEVERE, null, ex);
           
        }
        
         return Campo;
    }
    
     public DefaultTableModel SeleccionaTabla(String tabla, String campos, String id, int respuesta) throws SQLException {
        String query = "SELECT " + campos + " FROM " + tabla +" WHERE " + id + " = ?;";
        PreparedStatement ps = conexion.prepareStatement(query);
        ps.setInt(1, respuesta);
        ResultSet resultado = ps.executeQuery();
        DefaultTableModel modelo = new DefaultTableModel(){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };
        
        utilidades.ConversorResultSetADefaultTableModel.rellena(resultado, modelo);
        ps.close();
        resultado.close();
        return modelo;
    }
    
}
