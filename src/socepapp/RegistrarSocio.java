/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socepapp;

import codigo.Cifrado;
import codigo.Conexion;
import codigo.ConsultasCrud;
import codigo.Imagen;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.filechooser.*;
import utilidades.configuracionXml;

/**
 *
 * @author Acer
 */
public class RegistrarSocio extends javax.swing.JFrame {
    File fichero;
    FileInputStream fis;
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    
    
    private String camposSocios = "lada, Telefono, logo, Nombre, Id_Direcciones, Id_Redes_sociales, Contraseña, Correo, NombreSocio, Id_rol";
    private String id_pkSocios = "Id";
    private String tablaSocios = "socios";
    
    private String camposDirecciones = "Direccion, Estado, Municipio, Codigo_postal";
    private String id_pkDirecciones = "Id";
    private String tablaDirecciones = "direcciones_socios";
    
    private String camposSociales = "Facebook, Instagram, Twitter, Youtube";
    private String id_pkSociales = "Id";
    private String tablaSociales = "redes_sociales";
    /**
     * Creates new form RegistrarSocio
     */
    public RegistrarSocio() {

        initComponents();
    }
    
    public void ValidaRegistro(){
        
        String direccion = TxtDireccion.getText();
        String estado = (String) ComboEstados.getSelectedItem();
        String municipio = TxtLocalidad.getText();
        
        String telefono = TxtTelefono.getText();
        String nombreEmpresa = TxtNombreEmpresa.getText();
        String MiContrasenia = new String (TxtContraseña.getPassword());
        String nuevaContraseña = Cifrado.sha1(MiContrasenia);
        String correo = TxtCorreo.getText();
        String nombreSocio = TxtNombreSocio.getText();
        int rol = 2;
        
        if(TxtNombreSocio.getText().isEmpty() || TxtNombreEmpresa.getText().isEmpty() || TxtCorreo.getText().isEmpty()
                || TxtLada.getText().isEmpty() || TxtTelefono.getText().isEmpty() || TxtLocalidad.getText().isEmpty()
                || TxtCodigoPostal.getText().isEmpty()|| TxtContraseña.getText().isEmpty() || TxtDireccion.getText().isEmpty()){
            labeltitulo.setText("ALGUNOS CAMPOS ESTAN VACIOS");
            
        }else if(crud.esEmail(TxtCorreo.getText())){
            
            if(crud.CompruebaExistencias(TxtDireccion.getText(), tablaDirecciones, "Direccion", id_pkDirecciones) == 0){
                
                if(crud.CompruebaExistencias(TxtNombreEmpresa.getText(), tablaSocios, "Nombre", id_pkSocios) == 0){
                    int codigopostal = Integer.parseInt(TxtCodigoPostal.getText());
                    int lada = Integer.parseInt(TxtLada.getText());
                    try {
                        
                        List<Object> datosDireccion = new ArrayList<>();
                        datosDireccion.add(direccion);
                        datosDireccion.add(estado);
                        datosDireccion.add(municipio);
                        datosDireccion.add(codigopostal);
                        
                        List<Object> datosSociales = new ArrayList<>();
                        datosSociales.add("");
                        datosSociales.add("");
                        datosSociales.add("");
                        datosSociales.add("");
                        
                        if(crud.ingresar(datosDireccion, tablaDirecciones, camposDirecciones) && crud.ingresar(datosSociales, tablaSociales, camposSociales)){
                           int idDireccion = crud.Seleccionar_id(tablaDirecciones, id_pkDirecciones);
                           int idSociales = crud.Seleccionar_id(tablaSociales, id_pkSociales);
                           System.out.println(idDireccion + idSociales);
                           
                           List<Object> datosSocios = new ArrayList<>();
                           datosSocios.add(lada);
                           datosSocios.add(telefono);
                           datosSocios.add(fichero);
                           datosSocios.add(nombreEmpresa);
                           datosSocios.add(idDireccion);
                           datosSocios.add(idSociales);
                           datosSocios.add(nuevaContraseña);
                           datosSocios.add(correo);
                           datosSocios.add(nombreSocio);
                           datosSocios.add(rol);
                           if(crud.ingresar(datosSocios, tablaSocios, camposSocios)){
                               int idSocios = crud.Seleccionar_id(tablaSocios, id_pkSocios);
                               PreparedStatement ps;
                               ResultSet rs;
                               Connection con =  config.getConexion().getConexion();
                               ps = con.prepareStatement("UPDATE socios SET logo = ? WHERE Id = ?");
                               ps.setBinaryStream(1, fis, (int) fichero.length());
                               ps.setInt(2, idSocios);
                               ps.execute();
                               JOptionPane.showMessageDialog(null, "Imagen Guardada");
                               JOptionPane.showMessageDialog(null, "datos guardados");
                               
                           }else{
                               JOptionPane.showMessageDialog(null, "Error al registrar");
                           }
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(RegistrarSocio.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "La Empresa: "+TxtNombreEmpresa.getText()+ " ya existe"); 
                }
            }else{
              JOptionPane.showMessageDialog(null, "La direccion: "+TxtDireccion.getText()+ " ya existe");  
            }
        }else{
            JOptionPane.showMessageDialog(null, "correo invalido");
        }
    }
    
    /*public int GuardarDireccion(){
        
    }*/

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtUser = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        labeltitulo = new javax.swing.JLabel();
        PanelImagen = new javax.swing.JPanel();
        BtnInsertaLogo = new javax.swing.JButton();
        jLabel9 = new javax.swing.JLabel();
        TxtNombreSocio = new javax.swing.JTextField();
        TxtNombreEmpresa = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtCorreo = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        TxtTelefono = new javax.swing.JTextField();
        TxtLada = new javax.swing.JTextField();
        ComboEstados = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TxtLocalidad = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TxtCodigoPostal = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TxtContraseña = new javax.swing.JPasswordField();
        RegistrateScoio = new javax.swing.JButton();
        VentanaRegistroUsuario = new javax.swing.JButton();
        VentanaLogin = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        TxtDireccion = new javax.swing.JTextField();

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/negro.png"))); // NOI18N

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel8.setText("NOMBRE DE USUARIO:");
        jLabel8.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setText("REGISTRATE COMO SOCIO");
        jButton5.setActionCommand("");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/negro.png"))); // NOI18N

        labeltitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        labeltitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labeltitulo.setText("REGISTRAR SOCIO");

        javax.swing.GroupLayout PanelImagenLayout = new javax.swing.GroupLayout(PanelImagen);
        PanelImagen.setLayout(PanelImagenLayout);
        PanelImagenLayout.setHorizontalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        PanelImagenLayout.setVerticalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );

        BtnInsertaLogo.setBackground(new java.awt.Color(255, 255, 255));
        BtnInsertaLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paisaje.png"))); // NOI18N
        BtnInsertaLogo.setText("INSERTA LOGO");
        BtnInsertaLogo.setActionCommand("");
        BtnInsertaLogo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInsertaLogoActionPerformed(evt);
            }
        });

        jLabel9.setBackground(new java.awt.Color(255, 255, 255));
        jLabel9.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel9.setText("NOMBRE DEL SOCIO:");
        jLabel9.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        TxtNombreSocio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtNombreSocioKeyTyped(evt);
            }
        });

        TxtNombreEmpresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtNombreEmpresaKeyTyped(evt);
            }
        });

        jLabel10.setBackground(new java.awt.Color(255, 255, 255));
        jLabel10.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel10.setText("NOMBRE DE LA EMPRESA:");
        jLabel10.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel5.setText("CORREO ELECTRONICO:");
        jLabel5.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel6.setText("LADA Y TELEFONO:");
        jLabel6.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        TxtTelefono.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtTelefonoKeyTyped(evt);
            }
        });

        TxtLada.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtLadaKeyTyped(evt);
            }
        });

        ComboEstados.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        ComboEstados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "Coahuila de Zaragoza", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán de Ocampo", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz de Ignacio de la Llave", "Yucatán", "Zacatecas" }));
        ComboEstados.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboEstadosActionPerformed(evt);
            }
        });

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel7.setText("ESTADO:");
        jLabel7.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        jLabel11.setBackground(new java.awt.Color(255, 255, 255));
        jLabel11.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel11.setText("LOCALIDAD:");
        jLabel11.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        TxtLocalidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtLocalidadActionPerformed(evt);
            }
        });
        TxtLocalidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtLocalidadKeyTyped(evt);
            }
        });

        jLabel12.setBackground(new java.awt.Color(255, 255, 255));
        jLabel12.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel12.setText("CODIGO POSTAL:");
        jLabel12.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        TxtCodigoPostal.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtCodigoPostalKeyTyped(evt);
            }
        });

        jLabel13.setBackground(new java.awt.Color(255, 255, 255));
        jLabel13.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel13.setText("CONTRASEÑA:");
        jLabel13.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        RegistrateScoio.setBackground(new java.awt.Color(255, 255, 255));
        RegistrateScoio.setText("REGISTRATE");
        RegistrateScoio.setActionCommand("");
        RegistrateScoio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegistrateScoioActionPerformed(evt);
            }
        });

        VentanaRegistroUsuario.setBackground(new java.awt.Color(255, 255, 255));
        VentanaRegistroUsuario.setText("REGISTRATE COMO USUARIO");
        VentanaRegistroUsuario.setActionCommand("");
        VentanaRegistroUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentanaRegistroUsuarioActionPerformed(evt);
            }
        });

        VentanaLogin.setBackground(new java.awt.Color(255, 255, 255));
        VentanaLogin.setText("INICIA SESION");
        VentanaLogin.setActionCommand("");
        VentanaLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                VentanaLoginActionPerformed(evt);
            }
        });

        jLabel14.setBackground(new java.awt.Color(255, 255, 255));
        jLabel14.setFont(new java.awt.Font("Yu Gothic UI Light", 1, 18)); // NOI18N
        jLabel14.setText("DIRECCION:");
        jLabel14.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        TxtDireccion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TxtDireccionKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1108, Short.MAX_VALUE)
            .addComponent(labeltitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(BtnInsertaLogo, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                            .addComponent(PanelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(VentanaLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(4, 4, 4)))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TxtCodigoPostal, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(TxtCorreo)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtNombreSocio, javax.swing.GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtContraseña))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtLocalidad)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addComponent(TxtLada, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(TxtTelefono))
                                        .addComponent(TxtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(ComboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(RegistrateScoio, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(VentanaRegistroUsuario, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(TxtDireccion))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labeltitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TxtNombreSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtLada, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(TxtLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(TxtContraseña, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(TxtCodigoPostal, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(ComboEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(TxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 56, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(RegistrateScoio, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VentanaRegistroUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(VentanaLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(65, 65, 65))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnInsertaLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 666, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnInsertaLogoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInsertaLogoActionPerformed
        
        PanelImagen.removeAll();
        PanelImagen.repaint();
        
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*.PNG", "png");
        FileNameExtensionFilter filtro2 = new FileNameExtensionFilter("*.JPG", "jpg");
        fc.setFileFilter(filtro);
        fc.setFileFilter(filtro2);
        
        int selecion = fc.showOpenDialog(this);

        if(selecion == JFileChooser.APPROVE_OPTION){
            fichero = fc.getSelectedFile();
            try {
                fis = new FileInputStream(fichero);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(RegistrarSocio.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ruta = fichero.getAbsolutePath();
            System.out.println("tu imagen esta en: " + ruta);
            int ancho = PanelImagen.getWidth();
            int alto = PanelImagen.getHeight();
            Imagen img = new Imagen(ancho,alto,ruta);
            PanelImagen.repaint();
            PanelImagen.add(img);
        }
    }//GEN-LAST:event_BtnInsertaLogoActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void VentanaRegistroUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentanaRegistroUsuarioActionPerformed
        Registrar ru = new Registrar();
        ru.setVisible(true);
        ru.setLocationRelativeTo(null);
        dispose();
    }//GEN-LAST:event_VentanaRegistroUsuarioActionPerformed

    private void VentanaLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_VentanaLoginActionPerformed
        Login logeo = new Login();
        logeo.setVisible(true);
        logeo.setLocationRelativeTo(null);
        dispose();
    }//GEN-LAST:event_VentanaLoginActionPerformed

    private void TxtLocalidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtLocalidadActionPerformed

    }//GEN-LAST:event_TxtLocalidadActionPerformed

    private void ComboEstadosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboEstadosActionPerformed

    }//GEN-LAST:event_ComboEstadosActionPerformed

    private void TxtNombreSocioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombreSocioKeyTyped

        char c = evt.getKeyChar();
        if((c<'a' || c>'z') && (c<'A' || c>'Z')){
            evt.consume();
        }
        
    }//GEN-LAST:event_TxtNombreSocioKeyTyped

    private void TxtLadaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtLadaKeyTyped
        char c = evt.getKeyChar();
        if(c<'0' || c>'9'){
            evt.consume();
        }
    }//GEN-LAST:event_TxtLadaKeyTyped

    private void TxtNombreEmpresaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtNombreEmpresaKeyTyped
        char c = evt.getKeyChar();
        if((c<'a' || c>'z') && (c<'A' || c>'Z')){
            evt.consume();
        }
    }//GEN-LAST:event_TxtNombreEmpresaKeyTyped

    private void TxtTelefonoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtTelefonoKeyTyped
        char c = evt.getKeyChar();
        if(c<'0' || c>'9'){
            evt.consume();
        }
    }//GEN-LAST:event_TxtTelefonoKeyTyped

    private void TxtCodigoPostalKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtCodigoPostalKeyTyped
        char c = evt.getKeyChar();
        if(c<'0' || c>'9'){
            evt.consume();
        }
    }//GEN-LAST:event_TxtCodigoPostalKeyTyped

    private void RegistrateScoioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegistrateScoioActionPerformed
        ValidaRegistro();
    }//GEN-LAST:event_RegistrateScoioActionPerformed

    private void TxtLocalidadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtLocalidadKeyTyped
        char c = evt.getKeyChar();
        if((c<'a' || c>'z') && (c<'A' || c>'Z')){
            evt.consume();
        }
    }//GEN-LAST:event_TxtLocalidadKeyTyped

    private void TxtDireccionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TxtDireccionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtDireccionKeyTyped

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
            java.util.logging.Logger.getLogger(RegistrarSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(RegistrarSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(RegistrarSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistrarSocio.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new RegistrarSocio().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnInsertaLogo;
    private javax.swing.JComboBox<String> ComboEstados;
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JButton RegistrateScoio;
    private javax.swing.JTextField TxtCodigoPostal;
    private javax.swing.JPasswordField TxtContraseña;
    private javax.swing.JTextField TxtCorreo;
    private javax.swing.JTextField TxtDireccion;
    private javax.swing.JTextField TxtLada;
    private javax.swing.JTextField TxtLocalidad;
    private javax.swing.JTextField TxtNombreEmpresa;
    private javax.swing.JTextField TxtNombreSocio;
    private javax.swing.JTextField TxtTelefono;
    private javax.swing.JButton VentanaLogin;
    private javax.swing.JButton VentanaRegistroUsuario;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labeltitulo;
    private javax.swing.JTextField txtUser;
    // End of variables declaration//GEN-END:variables
}
