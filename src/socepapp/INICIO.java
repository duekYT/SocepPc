/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socepapp;

import Objetos.ImagenMySQL;
import Objetos.MiModeloUsuario;
import codigo.ConsultasCrud;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import utilidades.configuracionXml;

/**
 *
 * @author luisc
 */
public class INICIO extends javax.swing.JFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    MiModeloUsuario mod;
    
    String campos = "Nombre";
    String id = "Id";
    String tabla = "socios";
   
    /**
     * Creates new form INICIO
     */
    public INICIO(MiModeloUsuario mod) {
        this.mod = mod;
        initComponents();
        pantallaCoperativa();
        ConfigurarPanel();
    }
    public INICIO() {
        initComponents();
        pantallaCoperativa();
    }
    
    public void MiImagen(){
        PreparedStatement ps;
        ResultSet rs;

        try {
            Connection con =  config.getConexion().getConexion();
            ps = con.prepareStatement("SELECT logo FROM socios WHERE Id=?");
            ps.setInt(1, mod.getIdUsuario());
            rs = ps.executeQuery();

            BufferedImage buffimg = null;
            byte[] image = null;
            while (rs.next()) {
                image = rs.getBytes("logo");
                InputStream img = rs.getBinaryStream(1);
                try {
                    buffimg = ImageIO.read(img);
                    ImagenMySQL imagen = new ImagenMySQL(jpImagen.getHeight(), jpImagen.getWidth(), buffimg);
                } catch (IOException ex) {
                    Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            Image img = Toolkit.getDefaultToolkit().createImage(image);
            ImageIcon icon = new ImageIcon(img.getScaledInstance(120, 128, Image.SCALE_DEFAULT));
            lblImagen.setIcon(icon);
            lblImagen.setHorizontalAlignment(JLabel.CENTER);
            lblImagen.repaint();
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void ConfigurarPanel(){
        coperativaLabel.setText(mod.getNombreUsuario());
        MiImagen();
    }
    
    private void pantallaCoperativa() {
    try {
      //habrimos la pantalla de clientes
      //String grupo = jLabel3.getText();
      boolean b = true;
      MISION_VISION coperativa = new MISION_VISION(mod);
      this.escritorio.removeAll();
      this.escritorio.repaint();
      this.escritorio.add(coperativa);
      coperativa.setMaximum(b);
      coperativa.setVisible(true);  

    } catch (PropertyVetoException ex) {
      Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    }
    
    private void pantallaArticulos() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          articulos _articulos = new articulos(mod);
          this.escritorio.removeAll();
          this.escritorio.repaint();
          this.escritorio.add(_articulos);
          _articulos.setMaximum(b);
          _articulos.setVisible(true);  

        } catch (PropertyVetoException ex) {
          Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
        private void pantallaServicios() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          servicios _servicios = new servicios();
          this.escritorio.removeAll();
          this.escritorio.repaint();
          this.escritorio.add(_servicios);
          _servicios.setMaximum(b);
          _servicios.setVisible(true);  

        } catch (PropertyVetoException ex) {
          Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
        
        private void pantallaVentas() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          ventas _ventas = new ventas();
          this.escritorio.removeAll();
          this.escritorio.repaint();
          this.escritorio.add(_ventas);
          _ventas.setMaximum(b);
          _ventas.setVisible(true);  

        } catch (PropertyVetoException ex) {
          Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
        
        private void pantallaeEventos() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          eventos _eventos = new eventos();
          this.escritorio.removeAll();
          this.escritorio.repaint();
          this.escritorio.add(_eventos);
          _eventos.setMaximum(b);
          _eventos.setVisible(true);  

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

        jMenu3 = new javax.swing.JMenu();
        jMenu1 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        cholo = new javax.swing.JPanel();
        escritorio = new javax.swing.JDesktopPane();
        jPanel3 = new javax.swing.JPanel();
        coperativaLabel = new javax.swing.JLabel();
        BTN_coperativa = new javax.swing.JToggleButton();
        BTN_articulos = new javax.swing.JToggleButton();
        BTN_servicios = new javax.swing.JToggleButton();
        BTN_eventos = new javax.swing.JToggleButton();
        jpImagen = new javax.swing.JPanel();
        lblImagen = new javax.swing.JLabel();
        BTN_salir = new javax.swing.JButton();

        jMenu3.setText("jMenu3");

        jMenu1.setText("jMenu1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cholo.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 977, Short.MAX_VALUE)
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setMaximumSize(new java.awt.Dimension(100, 100));

        coperativaLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        coperativaLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        coperativaLabel.setText("Mi coperativa");
        coperativaLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        BTN_coperativa.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_coperativa);
        BTN_coperativa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/modouser.png"))); // NOI18N
        BTN_coperativa.setText("MI COPERATIVA");
        BTN_coperativa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_coperativaActionPerformed(evt);
            }
        });

        BTN_articulos.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_articulos);
        BTN_articulos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/rebaja.png"))); // NOI18N
        BTN_articulos.setText("MIS ARTICULOS");
        BTN_articulos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_articulosActionPerformed(evt);
            }
        });

        BTN_servicios.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_servicios);
        BTN_servicios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/recepcion.png"))); // NOI18N
        BTN_servicios.setText("MIS SERVICIOS");
        BTN_servicios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_serviciosActionPerformed(evt);
            }
        });

        BTN_eventos.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_eventos);
        BTN_eventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/caja-abierta.png"))); // NOI18N
        BTN_eventos.setText("MIS EVENTOS");
        BTN_eventos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_eventosActionPerformed(evt);
            }
        });

        jpImagen.setBackground(new java.awt.Color(255, 255, 255));

        lblImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empresa.png"))); // NOI18N

        javax.swing.GroupLayout jpImagenLayout = new javax.swing.GroupLayout(jpImagen);
        jpImagen.setLayout(jpImagenLayout);
        jpImagenLayout.setHorizontalGroup(
            jpImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jpImagenLayout.setVerticalGroup(
            jpImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(coperativaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
            .addComponent(BTN_coperativa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BTN_articulos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BTN_servicios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(BTN_eventos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jpImagen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jpImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(coperativaLabel)
                .addGap(18, 18, 18)
                .addComponent(BTN_coperativa)
                .addGap(18, 18, 18)
                .addComponent(BTN_articulos)
                .addGap(18, 18, 18)
                .addComponent(BTN_servicios)
                .addGap(18, 18, 18)
                .addComponent(BTN_eventos)
                .addContainerGap(145, Short.MAX_VALUE))
        );

        BTN_salir.setBackground(new java.awt.Color(255, 255, 255));
        BTN_salir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/salida.png"))); // NOI18N
        BTN_salir.setText("SALIR");

        javax.swing.GroupLayout choloLayout = new javax.swing.GroupLayout(cholo);
        cholo.setLayout(choloLayout);
        choloLayout.setHorizontalGroup(
            choloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, choloLayout.createSequentialGroup()
                .addGroup(choloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BTN_salir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(escritorio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        choloLayout.setVerticalGroup(
            choloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(choloLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BTN_salir, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(escritorio)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cholo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cholo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTN_coperativaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_coperativaActionPerformed

        pantallaCoperativa();
    }//GEN-LAST:event_BTN_coperativaActionPerformed

    private void BTN_articulosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_articulosActionPerformed
       pantallaArticulos();
    }//GEN-LAST:event_BTN_articulosActionPerformed

    private void BTN_serviciosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_serviciosActionPerformed
        pantallaServicios();
    }//GEN-LAST:event_BTN_serviciosActionPerformed

    private void BTN_eventosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_eventosActionPerformed
        pantallaeEventos();
    }//GEN-LAST:event_BTN_eventosActionPerformed

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
            java.util.logging.Logger.getLogger(INICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(INICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(INICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(INICIO.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new INICIO().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BTN_articulos;
    private javax.swing.JToggleButton BTN_coperativa;
    private javax.swing.JToggleButton BTN_eventos;
    private javax.swing.JButton BTN_salir;
    private javax.swing.JToggleButton BTN_servicios;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JPanel cholo;
    private javax.swing.JLabel coperativaLabel;
    private javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jpImagen;
    private javax.swing.JLabel lblImagen;
    // End of variables declaration//GEN-END:variables
}
