/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package socepapp;

import Objetos.ImagenMySQL;
import Objetos.MiModeloUsuario;
import Objetos.TablaImagen;
import codigo.ConsultasCrud;
import java.util.Date;
import codigo.Imagen;
import java.awt.Image;
import java.io.IOException;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import utilidades.configuracionXml;

/**
 *
 * @author Acer
 */
public class eventos extends javax.swing.JInternalFrame {
    
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    MiModeloUsuario mod;
    
    int filaId;
    File fichero;
    
    boolean hayimagen = false;
    FileInputStream fis;
    
    String campos = "Id_socio, Nombre, Descripcion, Direccion, Estado, Localidad, Fecha_inicio, Fecha_final, Imagen";
    String id = "Id";
    String tabla = "eventos";
    
    String campos2 = "eventos.Nombre = ?, eventos.Descripcion = ?, eventos.Direccion = ?, "
            + "eventos.Estado = ?, eventos.Localidad = ?, eventos.Fecha_inicio = ?, eventos.Fecha_final = ?, eventos.Imagen = ?";
    
    String tablainner = "eventos INNER JOIN socios ON eventos.Id_socio = socios.Id";
    
    String camposTabla = "eventos.Id, eventos.Nombre, eventos.Descripcion, eventos.Direccion, "
            + "eventos.Estado, eventos.Localidad, eventos.Fecha_inicio, eventos.Fecha_final";
    
    
    /**
     * Creates new form eventos
     */
    public eventos() {
        initComponents();
    }
    
    public eventos(MiModeloUsuario mod) {
        this.mod = mod;
        initComponents();
        BtnGuardar.setEnabled(false);
        mostrar_articulos();
    }
    
    public void validar(){
        String boton = BtnGuardar.getText();
        
        
        
        switch(boton){
            case "GUARDAR":
                if(TxtNombre.getText().isEmpty() || TxtLocalidad.getText().isEmpty() || TxtDireccion.getText().isEmpty() || TxtDescripcion.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
                }else if(crud.CompruebaExistencias(TxtNombre.getText(), tabla, "Nombre", id) == 0){
                    
                    Date fecha_convercion = (Date) TxtFechaInicio.getDatoFecha();
                    long fecha_convercion_recuperacion = fecha_convercion.getTime();
                    java.sql.Date fechaGuardar = new java.sql.Date(fecha_convercion_recuperacion);

                    Date fecha_convercionFin = (Date) TxtFechaFinal.getDatoFecha();
                    long fecha_convercion_recuperacionFIn = fecha_convercionFin.getTime();
                    java.sql.Date fechaGuardarFin = new java.sql.Date(fecha_convercion_recuperacionFIn);
                    
                    int SocioId = mod.getIdUsuario();
                    String nombre = TxtNombre.getText();
                    String descripcion = TxtDescripcion.getText();
                    String direccion = TxtDireccion.getText();
                    String estado = (String) ComoEstados.getSelectedItem();
                    String localidad = TxtLocalidad.getText();
                    
                    List<Object> datos = new ArrayList<>();
                    datos.add(SocioId);
                    datos.add(nombre);
                    datos.add(descripcion);
                    datos.add(direccion);
                    datos.add(estado);
                    datos.add(localidad);
                    datos.add(fechaGuardar);
                    datos.add(fechaGuardarFin);
                    datos.add(fichero);
                    
            try {
                if(crud.ingresar(datos, tabla, campos)){
                    int idservicio = crud.Seleccionar_id("eventos WHERE eventos.Id_socio = " + mod.getIdUsuario(), "eventos.Id");
                     ActualizaImagen(idservicio);
                        JOptionPane.showMessageDialog(null, "datos guardados");
                        mostrar_articulos();
                        limpiar();
                        BtnGuardar.setText("GUARDAR");
                        LabelTitulo.setText("AGREGA UN NUEVO EVENTO");
                        VentanaEventos.setTitleAt(0, "AGREGA UN EVENTO");
                        VentanaEventos.setSelectedIndex(1);
                }
            } catch (SQLException ex) {
                Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
            }
                    
              }else{
                    JOptionPane.showMessageDialog(null, "El evento: "+TxtNombre.getText()+" ya existe");
                }
            break;
            case "ACTUALIZA":
            if(hayimagen){
                try {
                        Date fecha_convercion = (Date) TxtFechaInicio.getDatoFecha();
                        long fecha_convercion_recuperacion = fecha_convercion.getTime();
                        java.sql.Date fechaGuardar = new java.sql.Date(fecha_convercion_recuperacion);

                        Date fecha_convercionFin = (Date) TxtFechaFinal.getDatoFecha();
                        long fecha_convercion_recuperacionFIn = fecha_convercionFin.getTime();
                        java.sql.Date fechaGuardarFin = new java.sql.Date(fecha_convercion_recuperacionFIn);

                        String nombre = TxtNombre.getText();
                        String descripcion = TxtDescripcion.getText();
                        String direccion = TxtDireccion.getText();
                        String estado = (String) ComoEstados.getSelectedItem();
                        String localidad = TxtLocalidad.getText();

                        List<Object> datos2 = new ArrayList<>();
                        datos2.add(nombre);
                        datos2.add(descripcion);
                        datos2.add(direccion);
                        datos2.add(estado);
                        datos2.add(localidad);
                        datos2.add(fechaGuardar);
                        datos2.add(fechaGuardarFin);
                        datos2.add(fichero);
                        datos2.add(filaId);

                        if(crud.actualizar(datos2, tabla, campos2, id)){
                            ActualizaImagen(filaId);
                            JOptionPane.showMessageDialog(null, "SERVICIO ACTUALIZADO");
                            mostrar_articulos();
                            limpiar();
                            BtnGuardar.setText("GUARDAR");
                            LabelTitulo.setText("AGREGA UN NUEVO EVENTO");
                            VentanaEventos.setTitleAt(0, "AGREGA UN EVENTO");
                            VentanaEventos.setSelectedIndex(1);
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                        Date fecha_convercion = (Date) TxtFechaInicio.getDatoFecha();
                        long fecha_convercion_recuperacion = fecha_convercion.getTime();
                        java.sql.Date fechaGuardar = new java.sql.Date(fecha_convercion_recuperacion);

                        Date fecha_convercionFin = (Date) TxtFechaFinal.getDatoFecha();
                        long fecha_convercion_recuperacionFIn = fecha_convercionFin.getTime();
                        java.sql.Date fechaGuardarFin = new java.sql.Date(fecha_convercion_recuperacionFIn);

                        String nombre = TxtNombre.getText();
                        String descripcion = TxtDescripcion.getText();
                        String direccion = TxtDireccion.getText();
                        String estado = (String) ComoEstados.getSelectedItem();
                        String localidad = TxtLocalidad.getText();
                        
                        String campos3 = "eventos.Nombre = ?, eventos.Descripcion = ?, eventos.Direccion = ?, "
                                    + "eventos.Estado = ?, eventos.Localidad = ?, eventos.Fecha_inicio = ?, eventos.Fecha_final = ?";
                        
                        List<Object> datos3 = new ArrayList<>();
                        datos3.add(nombre);
                        datos3.add(descripcion);
                        datos3.add(direccion);
                        datos3.add(estado);
                        datos3.add(localidad);
                        datos3.add(fechaGuardar);
                        datos3.add(fechaGuardarFin);
                        datos3.add(filaId);
                        

                        if(crud.actualizar(datos3, tabla, campos3, id)){
                            JOptionPane.showMessageDialog(null, "EVENTO ACTUALIZADO");
                            mostrar_articulos();
                            BtnGuardar.setText("GUARDAR");
                            limpiar();
                            LabelTitulo.setText("AGREGA UN NUEVO EVENTO");
                            VentanaEventos.setTitleAt(0, "AGREGA UN EVENTO");
                            VentanaEventos.setSelectedIndex(1);
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    public void seleccionaImagen(){
        PanelImagen.removeAll();
        PanelImagen.repaint();
        
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("*imagenes", "png", "jpg");
        fc.setFileFilter(filtro);
        
        int selecion = fc.showOpenDialog(this);

        if(selecion == JFileChooser.APPROVE_OPTION){
            fichero = fc.getSelectedFile();
            try {
                fis = new FileInputStream(fichero);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ruta = fichero.getAbsolutePath();
            System.out.println("tu imagen esta en: " + ruta);
            int ancho = PanelImagen.getWidth();
            int alto = PanelImagen.getHeight();
            Imagen img = new Imagen(ancho,alto,ruta);
            PanelImagen.repaint();
            PanelImagen.add(img);
        }else{
        }
    }
    
    public void ActualizaImagen(int id){
        try {
        PreparedStatement ps;
        ResultSet rs;
        Connection con =  config.getConexion().getConexion();
        ps = con.prepareStatement("UPDATE eventos SET eventos.Imagen = ? WHERE eventos.Id = ?");
        ps.setBinaryStream(1, fis, (int) fichero.length());
        ps.setInt(2, id);

        ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void mostrar_articulos(){
        try {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = crud.SeleccionaTabla(tablainner, camposTabla, "socios.Id", mod.getIdUsuario());
            TablaEventos.setModel(modeloTabla);
            TablaEventos.setRowHeight(64);
            mostrarTablaImagenes();
        } catch (SQLException ex) {
            Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTablaImagenes(){
        TablaImagenes.setDefaultRenderer(Object.class, new TablaImagen());
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        ResultSet rs;
            rs = crud.ver(tablainner, "eventos.Imagen", "socios.Id", mod.getIdUsuario());
            modeloTabla.addColumn("Imagen");
        try {
            while(rs.next()){
                Object[]fila = new Object[1];
                Blob blob = rs.getBlob(1);
                byte[] imagen = blob.getBytes(1, (int)blob.length());
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new ByteArrayInputStream(imagen));
                } catch (Exception e) {
                }
                ImageIcon icono = new ImageIcon(img.getScaledInstance(120, 64, Image.SCALE_DEFAULT));
                fila[0] = new JLabel(icono);
                modeloTabla.addRow(fila);
            }
            TablaImagenes.setModel(modeloTabla);
            TablaImagenes.setRowHeight(64);
        } catch (SQLException ex) {
            Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
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
        int fila = TablaEventos.getSelectedRow();
        
        if(fila >= 0){
            try {
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaEventos.getModel();
            int filas = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            
            boolean resultado = crud.borrar(filas, tabla, id);
            
            if(resultado){
                JOptionPane.showMessageDialog(null, "Has eliminado un Evento");
                mostrar_articulos();
                mostrarTablaImagenes();
            }else{
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
            
            
            } catch (SQLException ex) {
                Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Evento no encontrado");
        }
    }
    
    public void selecionaImagenActualiza(){
        PreparedStatement ps;
        ResultSet rs;
        PanelImagen.removeAll();
        PanelImagen.repaint();

        try {
            Connection con =  config.getConexion().getConexion();
            ps = con.prepareStatement("SELECT Imagen FROM eventos WHERE Id=?");
            ps.setInt(1, filaId);
            rs = ps.executeQuery();

            BufferedImage buffimg = null;
            byte[] image = null;
            while (rs.next()) {
                image = rs.getBytes("Imagen");
                InputStream img = rs.getBinaryStream(1);
                try {
                    buffimg = ImageIO.read(img);
                    ImagenMySQL imagen = new ImagenMySQL(442, 300, buffimg);
                    PanelImagen.add(imagen);
                    PanelImagen.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(eventos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void actualizar(){
        int fila = TablaEventos.getSelectedRow();
        if(fila >= 0){
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaEventos.getModel();
            filaId = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            String filaNombre = NuevoModeloTabla.getValueAt(fila, 1).toString();
            String filaDescripcion = NuevoModeloTabla.getValueAt(fila, 2).toString();
            String filaDireccion = NuevoModeloTabla.getValueAt(fila, 3).toString();
            String filaEstado = NuevoModeloTabla.getValueAt(fila, 4).toString();
            String filaLocalidad = NuevoModeloTabla.getValueAt(fila, 5).toString();
            Date FilaFechaInicio = (Date) NuevoModeloTabla.getValueAt(fila, 6);
            Date FilaFechaFin = (Date) NuevoModeloTabla.getValueAt(fila, 7);
            
            
            TxtNombre.setText(filaNombre);
            TxtDescripcion.setText(filaDescripcion);
            TxtDireccion.setText(filaDireccion);
            ComoEstados.setSelectedItem(filaEstado);
            TxtLocalidad.setText(filaLocalidad);
            TxtFechaInicio.setDatoFecha(FilaFechaInicio);
            TxtFechaFinal.setDatoFecha(FilaFechaFin);
            selecionaImagenActualiza();
            
            BtnGuardar.setText("ACTUALIZA");
            LabelTitulo.setText("ACTUALIZA EVENTO");
            VentanaEventos.setTitleAt(0, "ACTUALIZA UN EVENTO");
            VentanaEventos.setSelectedIndex(0);
            String boton = BtnGuardar.getText();
            if(boton == "ACTUALIZA"){
                BtnGuardar.setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "evento no encontrado");
        }
    }
    
    public void limpiar(){
        TxtNombre.setText("");
        TxtDescripcion.setText("");
        TxtDireccion.setText("");
        ComoEstados.setSelectedItem("");
        TxtLocalidad.setText("");
        TxtFechaInicio.setDatoFecha(null);
        TxtFechaFinal.setDatoFecha(null);
        PanelImagen.removeAll();
        PanelImagen.repaint();
        hayimagen = false;
        BtnGuardar.setText("GUARDAR");
        LabelTitulo.setText("AGREGA UN NUEVO EVENTO");
        VentanaEventos.setTitleAt(0, "AGREGA UN EVENTO");
        BtnGuardar.setEnabled(false);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ItemActualiza = new javax.swing.JMenuItem();
        ItemElimina = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        VentanaEventos = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        LabelEventos = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ComoEstados = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TxtDireccion = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        TxtLocalidad = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtDescripcion = new javax.swing.JTextArea();
        TxtFechaInicio = new rojeru_san.componentes.RSDateChooser();
        TxtFechaFinal = new rojeru_san.componentes.RSDateChooser();
        jPanel7 = new javax.swing.JPanel();
        PanelImagen = new javax.swing.JPanel();
        BtnInsertaImagen = new javax.swing.JButton();
        BtnGuardar = new javax.swing.JButton();
        BtnLimpiar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        LabelTitulo = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaEventos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaImagenes = new javax.swing.JTable();

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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        LabelEventos.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelEventos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/caja-abierta.png"))); // NOI18N
        LabelEventos.setText("CREA UN EVENTO");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Descripcion:");

        TxtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Estado:");

        ComoEstados.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        ComoEstados.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Aguascalientes", "Baja California", "Baja California Sur", "Campeche", "Chiapas", "Chihuahua", "Coahuila de Zaragoza", "Colima", "Durango", "Estado de México", "Guanajuato", "Guerrero", "Hidalgo", "Jalisco", "Michoacán de Ocampo", "Morelos", "Nayarit", "Nuevo León", "Oaxaca", "Puebla", "Querétaro", "Quintana Roo", "San Luis Potosí", "Sinaloa", "Sonora", "Tabasco", "Tamaulipas", "Tlaxcala", "Veracruz de Ignacio de la Llave", "Yucatán", "Zacatecas" }));

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Localidad:");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Nombre:");

        TxtDireccion.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel9.setText("Fecha inicio:");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel10.setText("Fecha final:");

        TxtLocalidad.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel11.setText("Direccion:");

        TxtDescripcion.setColumns(20);
        TxtDescripcion.setRows(5);
        jScrollPane2.setViewportView(TxtDescripcion);

        TxtFechaFinal.setToolTipText("");
        TxtFechaFinal.setName(""); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelEventos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TxtNombre)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ComoEstados, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(TxtLocalidad)))
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TxtDireccion)
            .addComponent(jScrollPane2)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(TxtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 5, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(LabelEventos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComoEstados, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TxtLocalidad, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(TxtFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(7, 7, 7))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(TxtFechaFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                .addComponent(jLabel11)
                .addGap(8, 8, 8)
                .addComponent(TxtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout PanelImagenLayout = new javax.swing.GroupLayout(PanelImagen);
        PanelImagen.setLayout(PanelImagenLayout);
        PanelImagenLayout.setHorizontalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        PanelImagenLayout.setVerticalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        BtnInsertaImagen.setBackground(new java.awt.Color(0, 51, 102));
        BtnInsertaImagen.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnInsertaImagen.setForeground(new java.awt.Color(255, 255, 255));
        BtnInsertaImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paisaje.png"))); // NOI18N
        BtnInsertaImagen.setText("INSERTA IMAGEN");
        BtnInsertaImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnInsertaImagenActionPerformed(evt);
            }
        });

        BtnGuardar.setBackground(new java.awt.Color(153, 255, 153));
        BtnGuardar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnGuardar.setForeground(new java.awt.Color(255, 255, 255));
        BtnGuardar.setText("GUARDAR");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnLimpiar.setBackground(new java.awt.Color(255, 51, 51));
        BtnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        BtnLimpiar.setForeground(new java.awt.Color(255, 255, 255));
        BtnLimpiar.setText("CANCELAR");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(86, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(BtnInsertaImagen, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(BtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BtnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(76, 76, 76))
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnInsertaImagen)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnGuardar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BtnLimpiar)
                .addContainerGap(93, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        VentanaEventos.addTab("EVENTOS", new javax.swing.ImageIcon(getClass().getResource("/imagenes/calendario.png")), jPanel2); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        LabelTitulo.setBackground(new java.awt.Color(255, 255, 255));
        LabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papel.png"))); // NOI18N
        LabelTitulo.setText("EVENTOS");

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel7.setText("00-00-0000 00:00:00");

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/reloj.png"))); // NOI18N
        jLabel8.setText("PROXIMO EVENTO");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                .addComponent(jLabel7))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(LabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LabelTitulo)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jTextField4))
                    .addComponent(jLabel7))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        TablaEventos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaEventos.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(TablaEventos);

        TablaImagenes.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaImagenes.setComponentPopupMenu(jPopupMenu1);
        jScrollPane3.setViewportView(TablaImagenes);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 437, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)))
        );

        VentanaEventos.addTab("MIS EVENTOS", new javax.swing.ImageIcon(getClass().getResource("/imagenes/papel.png")), jPanel3); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaEventos)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaEventos)
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
        validar();
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_BtnLimpiarActionPerformed

    private void BtnInsertaImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnInsertaImagenActionPerformed
        seleccionaImagen();
        hayimagen = true;
        String boton = BtnGuardar.getText();
        if(boton == "GUARDAR"){
            if(hayimagen){
                BtnGuardar.setEnabled(true);
            }else{
                BtnGuardar.setEnabled(false);
            }
        }
    }//GEN-LAST:event_BtnInsertaImagenActionPerformed

    private void ItemActualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemActualizaActionPerformed
        actualizar();
    }//GEN-LAST:event_ItemActualizaActionPerformed

    private void ItemEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemEliminaActionPerformed
        Confirmar_eliminacion();
    }//GEN-LAST:event_ItemEliminaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnInsertaImagen;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JComboBox<String> ComoEstados;
    private javax.swing.JMenuItem ItemActualiza;
    private javax.swing.JMenuItem ItemElimina;
    private javax.swing.JLabel LabelEventos;
    private javax.swing.JLabel LabelTitulo;
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JTable TablaEventos;
    private javax.swing.JTable TablaImagenes;
    private javax.swing.JTextArea TxtDescripcion;
    private javax.swing.JTextField TxtDireccion;
    private rojeru_san.componentes.RSDateChooser TxtFechaFinal;
    private rojeru_san.componentes.RSDateChooser TxtFechaInicio;
    private javax.swing.JTextField TxtLocalidad;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTabbedPane VentanaEventos;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
