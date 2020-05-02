/*
  este archivo contine todos loa mensajes o tipos de notificaciones que podemos usar para poder utilizar con todas las 
  pantallas, se importa en la pantalla que nesesitamos, y solo pasar un menaje de texto para poder utilizar
 */
package utilidades;

import rojerusan.RSNotifyAnimated;



public class notificaciones {
  
  public void mensajeError(String mensaje){
    
    new rojerusan.RSNotifyAnimated("Error", mensaje, 4, RSNotifyAnimated.PositionNotify.BottomRight, 
    RSNotifyAnimated.AnimationNotify.RightLeft, RSNotifyAnimated.TypeNotify.ERROR).setVisible(true);
    
  }
  
  public void mensajeAdvertencia(String mensaje){
    
    new rojerusan.RSNotifyAnimated("Advertencia", mensaje, 4 , RSNotifyAnimated.PositionNotify.BottomRight, 
            RSNotifyAnimated.AnimationNotify.RightLeft, RSNotifyAnimated.TypeNotify.WARNING).setVisible(true);
    
  }
  
  public void mensjeInformacion(String mensaje){
    
    new rojerusan.RSNotifyAnimated("Advertencia", mensaje, 4 , RSNotifyAnimated.PositionNotify.BottomRight, 
            RSNotifyAnimated.AnimationNotify.RightLeft, RSNotifyAnimated.TypeNotify.INFORMATION).setVisible(true);
    
  }
  
  public void mensajeCorrecto(String mensaje){
    new rojerusan.RSNotifyAnimated("Listo", mensaje, 4, RSNotifyAnimated.PositionNotify.BottomRight, 
            RSNotifyAnimated.AnimationNotify.RightLeft, RSNotifyAnimated.TypeNotify.SUCCESS).setVisible(true);
  }
  
}
