/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socepapp;

import Objetos.MiModeloUsuario;
import codigo.ConsultasCrud;
import coperativa.Miembros;
import coperativa.MisionVision;
import coperativa.RedesSociales;
import coperativa.informacion;
import java.beans.PropertyVetoException;
import java.util.logging.Level;
import java.util.logging.Logger;
import utilidades.configuracionXml;

/**
 *
 * @author luisc
 */
public class MISION_VISION extends javax.swing.JInternalFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    MiModeloUsuario mod;
    /**
     * Creates new form MISION_VISION
     */
    public MISION_VISION(MiModeloUsuario mod) {
        this.mod = mod;
        initComponents();
        ConfigurarPanel();
        pantallaInformacion();
    }
    
    public MISION_VISION() {
        initComponents();
    }
    
    public void ConfigurarPanel(){
        LabelCoperativa.setText(mod.getNombreUsuario());
    }
    
    private void pantallaMisionVision() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          MisionVision MV = new MisionVision(mod);
          this.escritorioCoperativa.removeAll();
          this.escritorioCoperativa.repaint();
          this.escritorioCoperativa.add(MV);
          MV.setMaximum(b);
          MV.setVisible(true);  

        } catch (PropertyVetoException ex) {
          Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
    private void pantallaInformacion() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          informacion _info = new informacion(mod);
          this.escritorioCoperativa.removeAll();
          this.escritorioCoperativa.repaint();
          this.escritorioCoperativa.add(_info);
          _info.setMaximum(b);
          _info.setVisible(true);  

        } catch (PropertyVetoException ex) {
          Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
    private void pantallaResdesSociales() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          RedesSociales RS = new RedesSociales();
          this.escritorioCoperativa.removeAll();
          this.escritorioCoperativa.repaint();
          this.escritorioCoperativa.add(RS);
          RS.setMaximum(b);
          RS.setVisible(true);  

        } catch (PropertyVetoException ex) {
          Logger.getLogger(INICIO.class.getName()).log(Level.SEVERE, null, ex);
        }
   
    }
    
    private void pantallaMiembros() {
        try {
          //habrimos la pantalla de clientes
          //String grupo = jLabel3.getText();
          boolean b = true;
          Miembros Miem = new Miembros();
          this.escritorioCoperativa.removeAll();
          this.escritorioCoperativa.repaint();
          this.escritorioCoperativa.add(Miem);
          Miem.setMaximum(b);
          Miem.setVisible(true);  

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
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        LabelCoperativa = new javax.swing.JLabel();
        BTN_mision = new javax.swing.JToggleButton();
        BTN_rs = new javax.swing.JToggleButton();
        BTN_IC = new javax.swing.JToggleButton();
        BTN_nl = new javax.swing.JToggleButton();
        escritorioCoperativa = new javax.swing.JDesktopPane();

        setPreferredSize(new java.awt.Dimension(1250, 763));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empresa.png"))); // NOI18N

        LabelCoperativa.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelCoperativa.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        LabelCoperativa.setText("Mi coperativa");

        BTN_mision.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_mision);
        BTN_mision.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/negocios.png"))); // NOI18N
        BTN_mision.setText("MISION Y VISION");
        BTN_mision.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_misionActionPerformed(evt);
            }
        });

        BTN_rs.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_rs);
        BTN_rs.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/megafono.png"))); // NOI18N
        BTN_rs.setText("REDES SOCIALES");
        BTN_rs.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_rsActionPerformed(evt);
            }
        });

        BTN_IC.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_IC);
        BTN_IC.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/miembro.png"))); // NOI18N
        BTN_IC.setText("MIEMBROS");
        BTN_IC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_ICActionPerformed(evt);
            }
        });

        BTN_nl.setBackground(new java.awt.Color(255, 255, 255));
        buttonGroup1.add(BTN_nl);
        BTN_nl.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lapiz.png"))); // NOI18N
        BTN_nl.setText("INFORMACION");
        BTN_nl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BTN_nlActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BTN_mision, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(BTN_nl, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_rs)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BTN_IC, javax.swing.GroupLayout.PREFERRED_SIZE, 265, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(LabelCoperativa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(428, 428, 428))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(LabelCoperativa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BTN_mision)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BTN_rs)
                    .addComponent(BTN_IC)
                    .addComponent(BTN_nl))
                .addContainerGap(14, Short.MAX_VALUE))
            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout escritorioCoperativaLayout = new javax.swing.GroupLayout(escritorioCoperativa);
        escritorioCoperativa.setLayout(escritorioCoperativaLayout);
        escritorioCoperativaLayout.setHorizontalGroup(
            escritorioCoperativaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        escritorioCoperativaLayout.setVerticalGroup(
            escritorioCoperativaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 451, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 977, Short.MAX_VALUE)
            .addComponent(escritorioCoperativa)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(escritorioCoperativa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BTN_misionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_misionActionPerformed
        pantallaMisionVision();
    }//GEN-LAST:event_BTN_misionActionPerformed

    private void BTN_rsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_rsActionPerformed
       pantallaResdesSociales();
    }//GEN-LAST:event_BTN_rsActionPerformed

    private void BTN_nlActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_nlActionPerformed
        pantallaInformacion();
    }//GEN-LAST:event_BTN_nlActionPerformed

    private void BTN_ICActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BTN_ICActionPerformed
        pantallaMiembros();
    }//GEN-LAST:event_BTN_ICActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton BTN_IC;
    private javax.swing.JToggleButton BTN_mision;
    private javax.swing.JToggleButton BTN_nl;
    private javax.swing.JToggleButton BTN_rs;
    private javax.swing.JLabel LabelCoperativa;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JDesktopPane escritorioCoperativa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    // End of variables declaration//GEN-END:variables
}
