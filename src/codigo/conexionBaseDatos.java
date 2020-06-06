/*
 * conexion a base de datos v1.0
 * creado por luis Alberto Coba Ventura 
 */
package codigo;

import utilidades.*;
import java.sql.*;
import java.sql.Connection;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public abstract class conexionBaseDatos {
  
  protected String url = "";
  protected String usuario = "";
  protected String contasena = "";
  protected Connection conexion = null;
  
  /*preparamos los metodos de captura de la conexion, extrallendo de configuracion xml */
  public Connection getConexion() {
    return conexion;
  }

  public void setConexion(Connection conexion) {
    this.conexion = conexion;
  }
  
  /*
  creamos la conexion a la base de datos pasando los datos recoletados de la clase de configuracion  
  */
  
  public void baseDatosConexion
  //pasamos los datos de la conexion0
  (
    String host,
    String db_name,
    String user,
    String password
  )throws SQLException, ClassNotFoundException 
  {
    //creamos la conexion
   Class.forName("com.mysql.jdbc.Driver");
   this.usuario = user;
   this.contasena = password;
   this.url = String.format("jdbc:mysql://%s/%s", host, db_name + "?useSSL=false");
   this.conexion = iniciarConexion();
  }
  
  //iniciamos la conexion a la base de datos 
  public Connection iniciarConexion() throws SQLException{
    return DriverManager.getConnection(url, usuario, contasena);
    
    
  }
  
  //se crean los metodos abtractos para las clases hijas.
  public abstract boolean ingresar( List<Object> values, String tabla, String campos )throws SQLException;
  public abstract boolean borrar( int id_fila, String tabla, String id_pk ) throws SQLException;
  public abstract boolean actualizar( List<Object> values, String tabla, String campos, String id_pk) throws SQLException;
  public abstract DefaultTableModel selecionarLista(String tabla, String campos) throws SQLException;
  public abstract List<Object> Seleccion( int id_fila, String tabla, String campos, String id_pk) throws SQLException;
  
  
  //se cierra la conexion a la base de datos
  public void cerrarConexion() throws SQLException 
  {
    this.conexion.close();
  }
  
}
