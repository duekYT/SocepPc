/*
 * configuracion y lectura del xml v 1.0
 * creado por luis Alberto Coba Ventura 
*/
package utilidades;

import codigo.conexionBaseDatos;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import socepapp.Login;
import utilidades.Utilidades;

public class configuracionXml extends JFrame {
  
  //mandamos a traer e iniciamos la clase de utilidades donde pasa el archivo de xml
  Utilidades utilidades = new Utilidades();
  // mandamos a traer e inicamo la clase conexionBaseDatos del paquete de conexion
  conexionBaseDatos conexion = new conexionBaseDatos() {
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
    
  };
  
  public configuracionXml()
  {
    
    String path = System.getProperty( "user.dir" );
    System.out.println( "JAVA corre desde: " + path );
    utilidades.setPath( path );
  
    boolean resultado_inicio_configuraciones = iniciarConfiguraciones();
      if( resultado_inicio_configuraciones )
      {
        System.out.println(
        "La conexión a la base de datos \"" + 
        utilidades.getTagValue( "DATABASE_NAME" ).toString() + 
        "\" fue exitosa" 
        );
      }
    
  }
   
  private boolean iniciarConfiguraciones() {
    
  //Leer Archivo de Configuraciones
  boolean resultado_leer = utilidades.leerXml();
  
    if (resultado_leer == false) {
      mostrarMensajeErrorUsuario("Favor de verificar el archivo de configuración e intente reconectar: \n"
        + utilidades.ARCHIVO_CONFIGURACION
      );
      return resultado_leer;
    } else {
      boolean resultado_bd = true;
      try {
        /*pamos los parametros a la conexion */
        conexion.baseDatosConexion(
          utilidades.getTagValue("DATABASE_HOST").toString(),
          utilidades.getTagValue("DATABASE_NAME").toString(),
          utilidades.getTagValue("DATABASE_USER").toString(),
          utilidades.getTagValue("DATABASE_PASS").toString()
        );
        
      } catch (ClassNotFoundException | SQLException e) {
        e.printStackTrace();
        System.out.println( "hola mundo 4" );
        JOptionPane.showMessageDialog(null, "Fallo de conexion");
        resultado_bd = false;
      }
      if (resultado_bd == false) {
        mostrarMensajeErrorUsuario("No se pudo realizar la conexión a la Base de datos: \n\n"
          + "Servidor: " + utilidades.getTagValue("DATABASE_HOST").toString() + "\n"
          + "Base de datos: " + utilidades.getTagValue("DATABASE_NAME").toString() + "\n"
          + "Usuario: " + utilidades.getTagValue("DATABASE_"
                  + ".USER").toString() + "\n"
          + "Contraseña: " + utilidades.getTagValue("DATABASE_PASS").toString() + "\n"
        );
        return resultado_bd;
      }
      return true;
    }
  }
  
  public conexionBaseDatos getConexion() {
    return conexion;
  }

  public void setConexion(conexionBaseDatos conexion) {
    this.conexion = conexion;
  }
    
  //mensaje de alerta
  private void mostrarMensajeErrorUsuario( String mensaje )
  {
    mostrarMensajeUsuario( mensaje,	"Error", JOptionPane.ERROR_MESSAGE );
    System.out.println( "hola mundo 1" );
  }

  //metodo de salida de mansajes 
  private void mostrarMensajeUsuario(String mensaje, String titulo, int tipo_mensaje ) {
    JOptionPane.showMessageDialog( this, mensaje, titulo, tipo_mensaje );
    System.out.println( mensaje );
  }
  
  
}
