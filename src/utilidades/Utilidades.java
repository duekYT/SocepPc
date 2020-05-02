package utilidades;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;


/**
*
* @author RARS
*/

public class Utilidades 
{
  public static String PATH = "";
  public static String IMAGE_PATH = "/imagenes/";
  public static String CONFIGURACIONES_XML = "configuraciones.xml";
  
  public static String DIRECTORIO_EJECUCION;
  public static String ARCHIVO_CONFIGURACION;  

  private static Map<String,Object> tags_values = new HashMap<String,Object>() 
  {
    {
      put( 
        "DATABASE_HOST", 
        new HashMap<String,Object>() 
        {
          {
              put("tag_name", "database_host");
              put("tag_type", "String");
              put("tag_value", "");
            }
        }
      );
      put( 
        "DATABASE_NAME", 
        new HashMap<String,Object>() 
        {
          {
              put("tag_name", "database_name");
              put("tag_type", "String");
              put("tag_value", "");
            }
        }
      );
      put( 
        "DATABASE_USER", 
        new HashMap<String,Object>() 
        {
          {
              put("tag_name", "database_user");
              put("tag_type", "String");
              put("tag_value", "");
            }
        }
      );
      put( 
        "DATABASE_PASS", 
        new HashMap<String,Object>() 
        {
          {
              put("tag_name", "database_pass");
              put("tag_type", "String");
              put("tag_value", "");
            }
        }
      );
    }
  };

  public void Utilidades(String path)
  {
    this.PATH = path;
  }
  
  public void setPath(String path)
  {
	  PATH = path;
	  DIRECTORIO_EJECUCION = path;
	  IMAGE_PATH = "/imagenes/";
	  ARCHIVO_CONFIGURACION = PATH + "/" + CONFIGURACIONES_XML;
  }
  
  public Map<String, Object> getTags()
  {
	  return tags_values;
  }
  
  public void setTagValue(String tag, Object value) 
  {
	  ((Map<String, Object>) tags_values.get(tag)).put("tag_value", value);
  }
  
  public Object getTagValue(String tag)
  {
	  String type = ((Map<String, Object>) tags_values.get(tag)).get("tag_type").toString();
	  switch (type) {
		case "int":
			return Integer.parseInt(((Map<String, Object>) tags_values.get(tag)).get("tag_value").toString());
		case "String":
			return ((Map<String, Object>) tags_values.get(tag)).get("tag_value").toString();
		case "double":
			return Double.parseDouble(((Map<String, Object>) tags_values.get(tag)).get("tag_value").toString());
		default:
			return ((Map<String, Object>) tags_values.get(tag)).get("tag_value");
	  }
  }
  
  public String getTagName(String tag)
  {
	  return ((Map<String, Object>) tags_values.get(tag)).get("tag_name").toString();
  }
  
  public int getTagsCount() 
  {
	  return tags_values.size(); 
  }
    
  public boolean leerXml()
	{   
    SAXBuilder constructor = new SAXBuilder();
    File archivo_xml = new File( this.ARCHIVO_CONFIGURACION );
    
    try 
    {      
      Document documento = ( Document ) constructor.build(archivo_xml );
      Element rootNode = documento.getRootElement();
      java.util.List<Element> list = rootNode.getChildren( "configuraciones" );

      for ( int i = 0; i < list.size(); i++ ) 
      {
        Element node = (Element) list.get( i );
        if( node.getChildren().size() == this.getTagsCount() ) 
        {
          for( Map.Entry<String, Object> tag : this.getTags().entrySet() ) 
          {
            this.setTagValue( tag.getKey(),  node.getChildText( this.getTagName(tag.getKey()) ) );
          }
          System.out.println("Archivo de configuración leido correctamente");
          return true;
        }
      }
    }
    catch (IOException io) 
    {
      System.out.println(io.getMessage());
      return false;
		} 
	  catch (JDOMException jdomex) 
	  {
      System.out.println(jdomex.getMessage());
      return false;
		}
	  return false;
	}
  
  public URL obtenerDireccionImagen( String nombre_archivo )
  {
    return this.getClass().getResource( IMAGE_PATH + nombre_archivo );
  }
  
  public ImageIcon obtenerIcono( String nombre_icono )
  {
    return new ImageIcon( obtenerDireccionImagen( nombre_icono ) );
  }
  
  public ImageIcon obtenerIconoEscalado( String nombre_icono, int ancho, int alto )
  {
    return new ImageIcon(
      (
        new ImageIcon( 
          obtenerDireccionImagen( nombre_icono ) 
        ).getImage()
      ).getScaledInstance(
        ancho, 
        alto, 
        Image.SCALE_SMOOTH
      )
    );
  }

}