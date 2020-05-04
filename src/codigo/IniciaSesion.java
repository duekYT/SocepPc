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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Acer
 */
public class IniciaSesion extends conexionBaseDatos{
    public IniciaSesion(Connection _conexion) {
        conexion = _conexion;
    }
    
    public boolean Inicia_Sesion(String correo, String contasenia, MiModeloUsuario mod, boolean respuesta){
        PreparedStatement ps = null;
        ResultSet resultado = null;
        
        
        
        String consultaSocio = "SELECT count(Id) AS 'socio' FROM socios WHERE Contraseña = ? and Correo = ?";
        String consultaUsuario = "SELECT count(Id) AS 'usuario' FROM usuario WHERE Contraseña = ? and Correo_electronico = ?";
        
        try {
            ps = conexion.prepareStatement(consultaSocio);
            ps.setString(1, contasenia);
            ps.setString(2, correo);
            resultado = ps.executeQuery();
            
            
            if ( resultado.next() ){
                if(resultado.getInt("socio") == 1){
                    System.out.println("estas en el area de Socios");
                    String InnerSocio = "SELECT roles.Id_rol, roles.Nombre, socios.Id, socios.Nombre FROM socios INNER JOIN roles on socios.Id_rol = roles.Id_rol WHERE socios.Id_rol = 2 AND socios.Correo = ? AND socios.Contraseña = ?";
                    ps = conexion.prepareStatement(InnerSocio);
                    ps.setString(1, correo);
                    ps.setString(2, contasenia);
                    resultado = ps.executeQuery();
                    
                    if(resultado.next()){
                        respuesta = true;
                        mod.setIdUsuario(resultado.getInt("socios.Id"));
                        mod.setNombreUsuario(resultado.getString("socios.Nombre"));
                        mod.setRol(resultado.getString("roles.Nombre"));
                        mod.setId_rol(resultado.getInt("roles.Id_rol"));
                    }else{
                        respuesta = false;
                    }
                    
                    ps.close();
                    resultado.close();
                    
                }else{
                    resultado = null;
                    ps = null;

                    ps = conexion.prepareStatement(consultaUsuario);
                    ps.setString(1, contasenia);
                    ps.setString(2, correo);
                    resultado = ps.executeQuery();
                    
                    if ( resultado.next() ){
                        
                        if(resultado.getInt("usuario") == 1){
                            System.out.println("estas en el area de Usuarios");
                            String InnerUsuario = "SELECT roles.Id_rol, roles.Nombre, usuario.Id, usuario.Nombre FROM usuario INNER JOIN roles on usuario.Id_roles = roles.Id_rol WHERE usuario.Id_roles = 1 and usuario.Contraseña = ? and usuario.Correo_electronico = ?;";
                            ps = conexion.prepareStatement(InnerUsuario);
                            ps.setString(1, contasenia);
                            ps.setString(2, correo);
                            resultado = ps.executeQuery();
                            if(resultado.next()){
                                respuesta = true;
                                mod.setIdUsuario(resultado.getInt("usuario.Id"));
                                mod.setNombreUsuario(resultado.getString("usuario.Nombre"));
                                mod.setRol(resultado.getString("roles.Nombre"));
                                mod.setId_rol(resultado.getInt("roles.Id_rol"));
                            }else{
                                respuesta = false;
                            }
                            ps.close();
                            resultado.close();
                        }
                    }
            }
            }else{
                int res = resultado.getInt("socio");
                System.out.println( res );
            }
            
            
             
        } catch (SQLException ex) {
            Logger.getLogger(IniciaSesion.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return respuesta;
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
