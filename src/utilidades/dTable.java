/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilidades;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author L
 */
public class dTable extends JTable {
  
  @Override
  public Component prepareRenderer(TableCellRenderer render, int rowIndex, int columnaIndex){
    
    Component component = super.prepareRenderer(render, rowIndex, columnaIndex);
    component.setBackground(Color.WHITE);
    component.setForeground(Color.BLACK);
    
    if( (getValueAt(rowIndex, columnaIndex)!= null) ) {
      String valor = getValueAt(rowIndex, columnaIndex).toString();
      
      if("Activa".equals(valor)){
        component.setBackground(Color.RED);
        component.setForeground(Color.BLACK);
      }if("Desactivo".equals(valor)){
        component.setBackground(Color.green);
        component.setForeground(Color.BLACK);
      }
    } else {
    }
    
    return null;
  }
  
  
}
