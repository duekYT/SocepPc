/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package coperativa;

import Objetos.MiModeloUsuario;
import codigo.ConsultasCrud;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import utilidades.configuracionXml;

/**
 *
 * @author Acer
 */
public class Miembros extends javax.swing.JInternalFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    MiModeloUsuario mod;
    String campos = "Nombre, Correo, Lada, Telefono, Socios_ID";
    String campos2 = "Nombre = ?, Correo = ?, Lada = ?, Telefono = ?";
    String id = "Id_miembro";
    String tablas = "miembros";
    
    String CamposTabla = "miembros.Id_miembro, miembros.Nombre, miembros.Correo, miembros.Lada, miembros.Telefono";
    String tablaIneer = "miembros INNER JOIN socios ON miembros.Socios_ID = socios.Id";
    
    int filaA;
    int filaId;
    /**
     * Creates new form Miembros
     */
    public Miembros() {
        initComponents();
    }
    
    public Miembros(MiModeloUsuario mod) {
        this.mod = mod;
        initComponents();
        mostrarMiembros();
    }
    
    public void mostrarMiembros(){
        try {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = crud.SeleccionaTabla(tablaIneer, CamposTabla, "socios.Id", mod.getIdUsuario());
            TablaMiembros.setModel(modeloTabla);
        } catch (SQLException ex) {
            Logger.getLogger(Miembros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void ValidarRegistro(){
        String boton = BtnGuardar.getText();
        switch(boton){
            case "GUARDAR":
                if(TtxNombre.getText().isEmpty() || TxtCorreo.getText().isEmpty() 
                   || TxtLada.getText().isEmpty() || TxtTelefono.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
                }else if(crud.esEmail(TxtCorreo.getText())){
                    int exNombre = crud.CompruebaExistencias(TtxNombre.getText(), tablas, "Nombre", id);
                    int exCorreo = crud.CompruebaExistencias(TxtCorreo.getText(), tablas, "Correo", id);
                    int exCorreoRes = crud.CompruebaExistencias(TxtCorreo.getText(), "socios", "Correo", "Id");
                    int exTelefono = crud.CompruebaExistencias(TxtTelefono.getText(), tablas, "Telefono", id);
                    System.out.println(exNombre + " " + exCorreo + " " + exTelefono + " " + exCorreoRes);
                    if((exNombre == 0) && (exCorreo == 0) && (exTelefono == 0) && (exCorreoRes == 0)){
                        String nombre = TtxNombre.getText();
                        String correo = TxtCorreo.getText();
                        String lada = TxtLada.getText();
                        String telefono = TxtTelefono.getText();

                        List<Object> datos = new ArrayList<>();
                        datos.add(nombre);
                        datos.add(correo);
                        datos.add(lada);
                        datos.add(telefono);
                        datos.add(mod.getIdUsuario());

                        try {
                            if(crud.ingresar(datos, tablas, campos)){
                                JOptionPane.showMessageDialog(null, "registro Guardado");
                                limpiar();
                                BtnGuardar.setText("GUARDAR");
                                LabelTitulo.setText("AGREGA UN NUEVO MIEMBRO");
                                VentanaMiembros.setTitleAt(0, "AGREGA MIEMBROS");
                                mostrarMiembros();
                                VentanaMiembros.setSelectedIndex(1);
                            }else{
                                JOptionPane.showMessageDialog(null, "Error al registrar");
                            }
                        } catch (SQLException ex) {
                            Logger.getLogger(Miembros.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    }else{
                        JOptionPane.showMessageDialog(null, "El miembro: "+TtxNombre.getText()+ " ya existe"); 
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "El correo es incorrecto");
                }
            break;
            
            case "ACTUALIZA":
            try {
                    String nombre = TtxNombre.getText();
                    String correo = TxtCorreo.getText();
                    String lada = TxtLada.getText();
                    String telefono = TxtTelefono.getText();

                    List<Object> datos2 = new ArrayList<>();
                    datos2.add(nombre);
                    datos2.add(correo);
                    datos2.add(lada);
                    datos2.add(telefono);
                    datos2.add(filaId);

                    if(crud.actualizar(datos2, tablas, campos2, id)){
                        JOptionPane.showMessageDialog(null, "MIEMBRO ACTUALIZADO");
                        limpiar();
                        BtnGuardar.setText("GUARDAR");
                        LabelTitulo.setText("AGREGA UN NUEVO MIEMBRO");
                        VentanaMiembros.setTitleAt(0, "AGREGA MIEMBROS");
                        mostrarMiembros();
                        VentanaMiembros.setSelectedIndex(1);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al registrar");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MisionVision.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
        }
        
    }
    
    public void Confirmar_eliminacion(){
        int dialogo = JOptionPane.YES_NO_OPTION;
        int respuesta = JOptionPane.showConfirmDialog(null, "estas seguro de esta accion? ", "eliminar", dialogo );
        
        if(respuesta == 0){
            eliminar();
        }
    }
    
    public void eliminar(){
        int fila = TablaMiembros.getSelectedRow();
        
        if(fila >= 0){
            try {
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaMiembros.getModel();
            int filas = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            
            boolean resultado = crud.borrar(filas, tablas, id);
            
            if(resultado){
                JOptionPane.showMessageDialog(null, "Has eliminado a un miembro");
                mostrarMiembros();
            }else{
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
            
            
            } catch (SQLException ex) {
                Logger.getLogger(Miembros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Miembro no encontrado");
        }
    }
    
    public void actualizar(){
        int fila = TablaMiembros.getSelectedRow();
        if(fila >= 0){
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaMiembros.getModel();
            filaId = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            String filaNombre = NuevoModeloTabla.getValueAt(fila, 1).toString();
            String filaCorreo = NuevoModeloTabla.getValueAt(fila, 2).toString();
            String filaLada = NuevoModeloTabla.getValueAt(fila, 3).toString();
            String filaTelefono = NuevoModeloTabla.getValueAt(fila, 4).toString();
            TtxNombre.setText(filaNombre);
            TxtCorreo.setText(filaCorreo);
            TxtLada.setText(filaLada);
            TxtTelefono.setText(filaTelefono);
            
            BtnGuardar.setText("ACTUALIZA");
            LabelTitulo.setText("ACTUALIZA MIEMBRO");
            VentanaMiembros.setTitleAt(0, "ACTUALIZA MIEMBROS");
            VentanaMiembros.setSelectedIndex(0);
        }else{
            JOptionPane.showMessageDialog(null, "Miembro no encontrado");
        }
    }
    
    public void limpiar(){
        TtxNombre.setText("");
        TxtCorreo.setText("");
        TxtLada.setText("");
        TxtTelefono.setText("");
        BtnGuardar.setText("GUARDAR");
        LabelTitulo.setText("AGREGA UN NUEVO MIEMBRO");
        VentanaMiembros.setTitleAt(0, "AGREGA MIEMBROS");
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ItemActualiza = new javax.swing.JMenuItem();
        ItemElimina = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        VentanaMiembros = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        LabelTitulo = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        TtxNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        TxtCorreo = new javax.swing.JTextField();
        TxtLada = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TxtTelefono = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        TablaArticulos = new javax.swing.JScrollPane();
        TablaMiembros = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("jLabel1");

        ItemActualiza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        ItemActualiza.setText("ACTUALIZAR");
        ItemActualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemActualizaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ItemActualiza);

        ItemElimina.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar.png"))); // NOI18N
        ItemElimina.setText("ELIMINAR");
        ItemElimina.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemEliminaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ItemElimina);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        LabelTitulo.setFont(new java.awt.Font("Tahoma", 0, 32)); // NOI18N
        LabelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grupo.png"))); // NOI18N
        LabelTitulo.setText("AGREGA UN NUEVO MIEMBRO");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel3.setText("NOMBRE:");

        TtxNombre.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TtxNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel4.setText("CORREO ELECTRONICO:");

        TxtCorreo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TxtCorreo.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        TxtLada.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TxtLada.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel5.setText("LADA Y TELEFONO:");

        TxtTelefono.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        TxtTelefono.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/negro.png"))); // NOI18N

        jButton2.setBackground(new java.awt.Color(255, 51, 51));
        jButton2.setText("LIMPIAR");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        BtnGuardar.setBackground(new java.awt.Color(153, 255, 153));
        BtnGuardar.setText("GUARDAR");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelTitulo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TtxNombre, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(TxtCorreo, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addComponent(TxtLada, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtTelefono)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(LabelTitulo)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TtxNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TxtLada, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE)
                    .addComponent(BtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        VentanaMiembros.addTab("AGREGA MIEMBRO", jPanel2);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        TablaMiembros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        TablaMiembros.setComponentPopupMenu(jPopupMenu1);
        TablaArticulos.setViewportView(TablaMiembros);

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grupo.png"))); // NOI18N
        jLabel7.setText("MIEMBROS");

        jTextField5.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(TablaArticulos)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, 269, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addGap(341, 606, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TablaArticulos, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        VentanaMiembros.addTab("MIEMBROS", jPanel3);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaMiembros)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaMiembros, javax.swing.GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        ValidarRegistro();
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void ItemActualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemActualizaActionPerformed
        actualizar();
    }//GEN-LAST:event_ItemActualizaActionPerformed

    private void ItemEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemEliminaActionPerformed
        Confirmar_eliminacion();
    }//GEN-LAST:event_ItemEliminaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       limpiar();
    }//GEN-LAST:event_jButton2ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JMenuItem ItemActualiza;
    private javax.swing.JMenuItem ItemElimina;
    private javax.swing.JLabel LabelTitulo;
    private javax.swing.JScrollPane TablaArticulos;
    private javax.swing.JTable TablaMiembros;
    private javax.swing.JTextField TtxNombre;
    private javax.swing.JTextField TxtCorreo;
    private javax.swing.JTextField TxtLada;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JTabbedPane VentanaMiembros;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JTextField jTextField5;
    // End of variables declaration//GEN-END:variables
}
