/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Acer
 */
public class Imagen extends JPanel{
    String ruta;

    public Imagen(int x, int y, String ruta) {
        this.setSize(x, y);
        this.ruta = ruta;
    }
    
    public void paint(Graphics g){
        Dimension tamanio = getSize();
        
        Image img = new ImageIcon(ruta).getImage();
        
        g.drawImage(img, 0, 0, tamanio.width, tamanio.height, null);
        setOpaque(false);
        super.paintComponent(g);
    }
    
}
