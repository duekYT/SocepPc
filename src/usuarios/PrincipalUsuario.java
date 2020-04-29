/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import socepapp.INICIO;
import socepapp.MISION_VISION;

/**
 *
 * @author Acer
 */
public class PrincipalUsuario extends javax.swing.JFrame {

    /**
     * Creates new form PrincipalUsuario
     */
    public PrincipalUsuario() {
        initComponents();
        pantallaSocioUsuario();
    }
    
    private void pantallaProductoUsuario() {
    try {
      //habrimos la pantalla de clientes
      //String grupo = jLabel3.getText();
      boolean b = true;
      UsuarioProductos UP = new UsuarioProductos();
      this.EscritorioUsuario.removeAll();
      this.EscritorioUsuario.repaint();
      this.EscritorioUsuario.add(UP);
      UP.setMaximum(b);
      UP.setVisible(true);  

    } catch (PropertyVetoException ex) {
      Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    }
    
    private void pantallaServicioUsuario() {
    try {
      //habrimos la pantalla de clientes
      //String grupo = jLabel3.getText();
      boolean b = true;
      UsuarioServicios US = new UsuarioServicios();
      this.EscritorioUsuario.removeAll();
      this.EscritorioUsuario.repaint();
      this.EscritorioUsuario.add(US);
      US.setMaximum(b);
      US.setVisible(true);  

    } catch (PropertyVetoException ex) {
      Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    }
    
    private void pantallaEventosUsuario() {
    try {
      //habrimos la pantalla de clientes
      //String grupo = jLabel3.getText();
      boolean b = true;
      EventosUsuario UE = new EventosUsuario();
      this.EscritorioUsuario.removeAll();
      this.EscritorioUsuario.repaint();
      this.EscritorioUsuario.add(UE);
      UE.setMaximum(b);
      UE.setVisible(true);  

    } catch (PropertyVetoException ex) {
      Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    }
    
    private void pantallaSocioUsuario() {
    try {
      //habrimos la pantalla de clientes
      //String grupo = jLabel3.getText();
      boolean b = true;
      UsuariosSocio USO = new UsuariosSocio();
      this.EscritorioUsuario.removeAll();
      this.EscritorioUsuario.repaint();
      this.EscritorioUsuario.add(USO);
      USO.setMaximum(b);
      USO.setVisible(true);  

    } catch (PropertyVetoException ex) {
      Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        BtnPerfilUsuario = new javax.swing.JToggleButton();
        BtnProductoUsuario = new javax.swing.JToggleButton();
        BtnServiciosUsuario = new javax.swing.JToggleButton();
        BtnEventosUsuario = new javax.swing.JToggleButton();
        BntCerrarSecion = new javax.swing.JButton();
        EscritorioUsuario = new javax.swing.JDesktopPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empresa.png"))); // NOI18N

        BtnPerfilUsuario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BtnPerfilUsuario);
        BtnPerfilUsuario.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnPerfilUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empresamenu.png"))); // NOI18N
        BtnPerfilUsuario.setText("Coperativas");
        BtnPerfilUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnPerfilUsuarioActionPerformed(evt);
            }
        });

        BtnProductoUsuario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BtnProductoUsuario);
        BtnProductoUsuario.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnProductoUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/rebaja.png"))); // NOI18N
        BtnProductoUsuario.setText("Productos");
        BtnProductoUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnProductoUsuarioActionPerformed(evt);
            }
        });

        BtnServiciosUsuario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BtnServiciosUsuario);
        BtnServiciosUsuario.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnServiciosUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/recepcion.png"))); // NOI18N
        BtnServiciosUsuario.setText("Servicios");
        BtnServiciosUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnServiciosUsuarioActionPerformed(evt);
            }
        });

        BtnEventosUsuario.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BtnEventosUsuario);
        BtnEventosUsuario.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnEventosUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/calendario.png"))); // NOI18N
        BtnEventosUsuario.setText("Eventos");
        BtnEventosUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEventosUsuarioActionPerformed(evt);
            }
        });

        BntCerrarSecion.setBackground(new java.awt.Color(255, 255, 255));
        BntCerrarSecion.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BntCerrarSecion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        BntCerrarSecion.setText("Cerrar Sesion");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BtnPerfilUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BtnProductoUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BtnServiciosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BtnEventosUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BntCerrarSecion, javax.swing.GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnPerfilUsuario)
                .addGap(18, 18, 18)
                .addComponent(BtnProductoUsuario)
                .addGap(18, 18, 18)
                .addComponent(BtnServiciosUsuario)
                .addGap(18, 18, 18)
                .addComponent(BtnEventosUsuario)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 128, Short.MAX_VALUE)
                .addComponent(BntCerrarSecion, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        javax.swing.GroupLayout EscritorioUsuarioLayout = new javax.swing.GroupLayout(EscritorioUsuario);
        EscritorioUsuario.setLayout(EscritorioUsuarioLayout);
        EscritorioUsuarioLayout.setHorizontalGroup(
            EscritorioUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 910, Short.MAX_VALUE)
        );
        EscritorioUsuarioLayout.setVerticalGroup(
            EscritorioUsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(EscritorioUsuario))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(EscritorioUsuario)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnProductoUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnProductoUsuarioActionPerformed
        pantallaProductoUsuario();
    }//GEN-LAST:event_BtnProductoUsuarioActionPerformed

    private void BtnServiciosUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnServiciosUsuarioActionPerformed
        pantallaServicioUsuario();
    }//GEN-LAST:event_BtnServiciosUsuarioActionPerformed

    private void BtnPerfilUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnPerfilUsuarioActionPerformed
        pantallaSocioUsuario();
    }//GEN-LAST:event_BtnPerfilUsuarioActionPerformed

    private void BtnEventosUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEventosUsuarioActionPerformed
        pantallaEventosUsuario();
    }//GEN-LAST:event_BtnEventosUsuarioActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalUsuario().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BntCerrarSecion;
    private javax.swing.JToggleButton BtnEventosUsuario;
    private javax.swing.JToggleButton BtnPerfilUsuario;
    private javax.swing.JToggleButton BtnProductoUsuario;
    private javax.swing.JToggleButton BtnServiciosUsuario;
    private javax.swing.JDesktopPane EscritorioUsuario;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}