/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import Objetos.ImagenMySQL;
import codigo.ConsultasCrud;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import socepapp.articulos;
import utilidades.configuracionXml;

/**
 *
 * @author Acer
 */
public class EventosUsuario extends javax.swing.JInternalFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    
    String tabla = "eventos";
    String campos = "eventos.Nombre";
    String tablain = "eventos INNER JOIN socios on eventos.Id_socio = socios.Id";
    String filaNombre;
    /**
     * Creates new form EventosUsuario
     */
    public EventosUsuario() {
        initComponents();
        mostrar_Eventos();
        ListaEventos.setSelectedIndex(0);
        mostrar_filtro();
        seleccionearItem();
    }
    
    public void mostrar_Eventos(){
        try {
            DefaultListModel modeloLista = new DefaultListModel();
            modeloLista = crud.listas(tabla, campos);
            ListaEventos.setModel(modeloLista);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrar_filtro(){
        try {
            String tabla2 = "socios";
            String campos2 = "socios.Nombre";
            DefaultComboBoxModel modeloCombo = new DefaultComboBoxModel();
            modeloCombo = crud.Combo(tabla2, campos2);
            ComboFiltro.setModel(modeloCombo);
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void seleccionearItem(){
        int fila = ListaEventos.getSelectedIndex();
        if(fila >= 0){
            DefaultListModel NuevoModeloLista = (DefaultListModel) ListaEventos.getModel();
            filaNombre = NuevoModeloLista.getElementAt(fila).toString();
            String empresa = crud.SeleccinaUnCampoLista(tablain, "socios.Nombre", "eventos.Nombre", filaNombre);
            String descripcion = crud.SeleccinaUnCampoLista(tabla, "eventos.Descripcion", "eventos.Nombre", filaNombre);
            String fechaInicio = crud.SeleccinaUnCampoLista(tabla, "eventos.Fecha_inicio", "eventos.Nombre", filaNombre);
            String fechaFinal = crud.SeleccinaUnCampoLista(tabla, "eventos.Fecha_final", "eventos.Nombre", filaNombre);
            String estado = crud.SeleccinaUnCampoLista(tabla, "eventos.Estado", "eventos.Nombre", filaNombre);
            String localidad = crud.SeleccinaUnCampoLista(tabla, "eventos.Localidad", "eventos.Nombre", filaNombre);
            String direccion = crud.SeleccinaUnCampoLista(tabla, "eventos.Direccion", "eventos.Nombre", filaNombre);
            selecionaImagenActualiza(filaNombre);
            LabelNombre.setText("Nombre: "+filaNombre);
            LabelEmpresa.setText("Organizado por: "+empresa);
            DescrpcionArea.setText(descripcion);
            LabelFechaInicio.setText("Inicia el: "+fechaInicio);
            LabelFechaFinal.setText("Finaliza el: "+fechaFinal);
            LabelEstado.setText("Estado: "+estado);
            LabelLocalidad.setText("Localidad: "+localidad);
            LabelDireccion.setText("Direccion: "+direccion);
            
        }
    }
    
    public void selecionaImagenActualiza(String res){
        PreparedStatement ps;
        ResultSet rs;
        PanelImagen.removeAll();
        PanelImagen.repaint();

        try {
            Connection con =  config.getConexion().getConexion();
            ps = con.prepareStatement("SELECT Imagen FROM eventos WHERE eventos.Nombre=?");
            ps.setString(1, res);
            rs = ps.executeQuery();

            BufferedImage buffimg = null;
            byte[] image = null;
            while (rs.next()) {
                image = rs.getBytes("Imagen");
                InputStream img = rs.getBinaryStream(1);
                try {
                    buffimg = ImageIO.read(img);
                    ImagenMySQL imagen = new ImagenMySQL(520, 279, buffimg);
                    PanelImagen.add(imagen);
                    PanelImagen.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(EventosUsuario.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListaEventos = new javax.swing.JList<>();
        ComboFiltro = new javax.swing.JComboBox<>();
        PanelImagen = new javax.swing.JPanel();
        LabelEmpresa = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        LabelFechaInicio = new javax.swing.JLabel();
        LabelFechaFinal = new javax.swing.JLabel();
        LabelDireccion = new javax.swing.JLabel();
        LabelLocalidad = new javax.swing.JLabel();
        LabelEstado = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        DescrpcionArea = new javax.swing.JTextArea();
        LabelNombre = new javax.swing.JLabel();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        ListaEventos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ListaEventos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Evento 1", "Evento 2", "Evento 3", "Evento 4", "Evento 5", "Evento 6" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ListaEventos.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        ListaEventos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaEventosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ListaEventosMouseReleased(evt);
            }
        });
        ListaEventos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListaEventosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(ListaEventos);

        ComboFiltro.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ComboFiltro.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Todos", "Coperativa 1", "Coperativa 2", "Coperativa 3", "Coperativa 4", "Coperativa 5" }));
        ComboFiltro.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                ComboFiltroItemStateChanged(evt);
            }
        });

        PanelImagen.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelImagenLayout = new javax.swing.GroupLayout(PanelImagen);
        PanelImagen.setLayout(PanelImagenLayout);
        PanelImagenLayout.setHorizontalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelImagenLayout.setVerticalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 279, Short.MAX_VALUE)
        );

        LabelEmpresa.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelEmpresa.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelEmpresa.setText("Coperativa: nombre de la coperativa");

        jPanel3.setBackground(new java.awt.Color(153, 153, 255));

        LabelFechaInicio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelFechaInicio.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelFechaInicio.setText("Fecha de inicio: 00/00/0000 00:00");

        LabelFechaFinal.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelFechaFinal.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelFechaFinal.setText("Fecha de final: 00/00/0000 00:00");

        LabelDireccion.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelDireccion.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelDireccion.setText("Fecha de final: 00/00/0000 00:00");

        LabelLocalidad.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelLocalidad.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelLocalidad.setText("Fecha de final: 00/00/0000 00:00");

        LabelEstado.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelEstado.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelEstado.setText("Fecha de final: 00/00/0000 00:00");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(LabelFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addComponent(LabelFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(LabelDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(LabelLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(LabelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelFechaFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                    .addComponent(LabelFechaInicio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(16, 16, 16)
                .addComponent(LabelEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelLocalidad, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 20, Short.MAX_VALUE))
        );

        DescrpcionArea.setEditable(false);
        DescrpcionArea.setColumns(20);
        DescrpcionArea.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        DescrpcionArea.setLineWrap(true);
        DescrpcionArea.setRows(5);
        jScrollPane2.setViewportView(DescrpcionArea);

        LabelNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelNombre.setForeground(new java.awt.Color(0, 204, 204));
        LabelNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        LabelNombre.setText("Coperativa: nombre de la coperativa");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
                    .addComponent(ComboFiltro, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addComponent(LabelNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(LabelEmpresa, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(ComboFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 383, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelEmpresa)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ListaEventosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEventosMouseClicked
        seleccionearItem();
    }//GEN-LAST:event_ListaEventosMouseClicked

    private void ListaEventosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEventosMouseReleased
        seleccionearItem();
    }//GEN-LAST:event_ListaEventosMouseReleased

    private void ListaEventosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListaEventosValueChanged
        seleccionearItem();
    }//GEN-LAST:event_ListaEventosValueChanged

    private void ComboFiltroItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_ComboFiltroItemStateChanged
        String opcion = (String)ComboFiltro.getSelectedItem();
        if(opcion == "Todos"){
            mostrar_Eventos();
            ListaEventos.setSelectedIndex(0);
            seleccionearItem();
        }else{
            try {
                DefaultListModel modeloLista = new DefaultListModel();
                modeloLista = crud.listaCombo(tablain, campos, "socios.Nombre", opcion);
                ListaEventos.setModel(modeloLista);
                ListaEventos.setSelectedIndex(0);
                seleccionearItem();
            } catch (SQLException ex) {
                Logger.getLogger(UsuarioProductos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }//GEN-LAST:event_ComboFiltroItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboFiltro;
    private javax.swing.JTextArea DescrpcionArea;
    private javax.swing.JLabel LabelDireccion;
    private javax.swing.JLabel LabelEmpresa;
    private javax.swing.JLabel LabelEstado;
    private javax.swing.JLabel LabelFechaFinal;
    private javax.swing.JLabel LabelFechaInicio;
    private javax.swing.JLabel LabelLocalidad;
    private javax.swing.JLabel LabelNombre;
    private javax.swing.JList<String> ListaEventos;
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    // End of variables declaration//GEN-END:variables
}
