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
import codigo.Imagen;
import coperativa.Miembros;
import coperativa.MisionVision;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
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
 * @author luisc
 */
public class servicios extends javax.swing.JInternalFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    MiModeloUsuario mod;
    
    int filaId;
    File fichero;
    
    boolean hayimagen = false;
    FileInputStream fis;
    
    String campos = "Precio, Nombre, Descripcion, Imagen, MiSocio, Plan";
    String id = "ID_Servicios";
    String tabla = "servicios";
    
    String campos2 = "servicios.Nombre = ?, servicios.Precio = ?, servicios.Descripcion = ?, Imagen = ?, servicios.Plan = ?";
    
    String tablainner = "servicios INNER JOIN socios ON servicios.MiSocio = socios.Id";
    String camposTabla = "servicios.ID_Servicios, servicios.Nombre, servicios.Precio, servicios.Descripcion, servicios.Plan";
    /**
     * Creates new form servicios
     */
    public servicios() {
        initComponents();
    }
    
    public servicios(MiModeloUsuario mod) {
        this.mod = mod;
        initComponents();
        BtnGuardar.setEnabled(false);
        mostrar_articulos();
    }
    
    public void validar(){
        String boton = BtnGuardar.getText();
        switch(boton){
            case "GUARDAR":
                if(TxtNombre.getText().isEmpty() || TxtPrecio.getText().isEmpty() || TxtDescripcion.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
                }else if(crud.CompruebaExistencias(TxtNombre.getText(), tabla, "Nombre", id) == 0){
                    double precio = Double.parseDouble(TxtPrecio.getText());
                    String nombre = TxtNombre.getText();
                    String descripcion = TxtDescripcion.getText();
                    int socioId = mod.getIdUsuario();
                    String plan = (String) ComboPlan.getSelectedItem();
                    
                    List<Object> datos = new ArrayList<>();
                    datos.add(precio);
                    datos.add(nombre);
                    datos.add(descripcion);
                    datos.add(fichero);
                    datos.add(socioId);
                    datos.add(plan);
                    
                try {
                    if(crud.ingresar(datos, tabla, campos)){
                        int idservicio = crud.Seleccionar_id("servicios WHERE servicios.MiSocio = " + mod.getIdUsuario(), "servicios.ID_Servicios");
                        ActualizaImagen(idservicio);
                        JOptionPane.showMessageDialog(null, "datos guardados");
                        mostrar_articulos();
                        limpiar();
                        BtnGuardar.setText("GUARDAR");
                        LabelTitulo.setText("AGREGA UN NUEVO SERVICIO");
                        VentanaArticulos.setTitleAt(0, "AGREGA UN SERVICIO");
                        VentanaArticulos.setSelectedIndex(1);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al registrar");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
                }else{
                    JOptionPane.showMessageDialog(null, "El servicio " + TxtNombre.getText()+" ya existe");
                }
            break;
            case "ACTUALIZA":
            if(hayimagen){
                try {
                        double precio = Double.parseDouble(TxtPrecio.getText());
                        String nombre = TxtNombre.getText();
                        String descripcion = TxtDescripcion.getText();
                        String plan = (String) ComboPlan.getSelectedItem();

                        List<Object> datos2 = new ArrayList<>();
                        datos2.add(nombre);
                        datos2.add(precio);
                        datos2.add(descripcion);
                        datos2.add(fichero);
                        datos2.add(plan);
                        datos2.add(filaId);

                        if(crud.actualizar(datos2, tabla, campos2, id)){
                            ActualizaImagen(filaId);
                            JOptionPane.showMessageDialog(null, "SERVICIO ACTUALIZADO");
                            mostrar_articulos();
                            limpiar();
                            BtnGuardar.setText("GUARDAR");
                            LabelTitulo.setText("AGREGA UN NUEVO SERVICIO");
                            VentanaArticulos.setTitleAt(0, "AGREGA UN SERVICIO");
                            VentanaArticulos.setSelectedIndex(1);
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }else{
                try {
                        double precio = Double.parseDouble(TxtPrecio.getText());
                        String nombre = TxtNombre.getText();
                        String descripcion = TxtDescripcion.getText();
                        String plan = (String) ComboPlan.getSelectedItem();
                        
                        String campos3 = "servicios.Nombre = ?, servicios.Precio = ?, servicios.Descripcion = ?, servicios.Plan = ?";
                        List<Object> datos3 = new ArrayList<>();
                        datos3.add(nombre);
                        datos3.add(precio);
                        datos3.add(descripcion);
                        datos3.add(plan);
                        datos3.add(filaId);

                        if(crud.actualizar(datos3, tabla, campos3, id)){
                            JOptionPane.showMessageDialog(null, "SERVICIO ACTUALIZADO");
                            mostrar_articulos();
                            BtnGuardar.setText("GUARDAR");
                            limpiar();
                            LabelTitulo.setText("AGREGA UN NUEVO ARTICULO");
                            VentanaArticulos.setTitleAt(0, "AGREGA UN ARTICULO");
                            VentanaArticulos.setSelectedIndex(1);
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            break;
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
                Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
            String ruta = fichero.getAbsolutePath();
            System.out.println("tu imagen esta en: " + ruta);
            int ancho = PanelImagen.getWidth();
            int alto = PanelImagen.getHeight();
            Imagen img = new Imagen(ancho,alto,ruta);
            PanelImagen.repaint();
            PanelImagen.add(img);
        }
    }
    
    public void ActualizaImagen(int id){
        try {
        PreparedStatement ps;
        ResultSet rs;
        Connection con =  config.getConexion().getConexion();
        ps = con.prepareStatement("UPDATE servicios SET servicios.Imagen = ? WHERE servicios.ID_Servicios = ?");
        ps.setBinaryStream(1, fis, (int) fichero.length());
        ps.setInt(2, id);

        ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public void mostrar_articulos(){
        try {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = crud.SeleccionaTabla(tablainner, camposTabla, "socios.Id", mod.getIdUsuario());
            TablaServicios.setModel(modeloTabla);
            TablaServicios.setRowHeight(64);
            mostrarTablaImagenes();
        } catch (SQLException ex) {
            Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTablaImagenes(){
        TablaImagen.setDefaultRenderer(Object.class, new TablaImagen());
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        ResultSet rs;
            rs = crud.ver(tablainner, "servicios.Imagen", "socios.Id", mod.getIdUsuario());
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
            TablaImagen.setModel(modeloTabla);
            TablaImagen.setRowHeight(64);
        } catch (SQLException ex) {
            Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
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
        int fila = TablaServicios.getSelectedRow();
        
        if(fila >= 0){
            try {
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaServicios.getModel();
            int filas = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            
            boolean resultado = crud.borrar(filas, tabla, id);
            
            if(resultado){
                JOptionPane.showMessageDialog(null, "Has eliminado un articulo");
                mostrar_articulos();
                mostrarTablaImagenes();
            }else{
                JOptionPane.showMessageDialog(null, "Error al eliminar");
            }
            
            
            } catch (SQLException ex) {
                Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Miembro no encontrado");
        }
    }
    
    public void actualizar(){
        int fila = TablaServicios.getSelectedRow();
        if(fila >= 0){
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaServicios.getModel();
            filaId = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            String filaPrecio = NuevoModeloTabla.getValueAt(fila, 2).toString();
            String filaNombre = NuevoModeloTabla.getValueAt(fila, 1).toString();
            String filaDescripcion = NuevoModeloTabla.getValueAt(fila, 3).toString();
            String filaPlan = NuevoModeloTabla.getValueAt(fila, 4).toString();
            
            
            TxtNombre.setText(filaNombre);
            TxtPrecio.setText(filaPrecio);
            TxtDescripcion.setText(filaDescripcion);
            ComboPlan.setSelectedItem(filaPlan);
            selecionaImagenActualiza();
            
            
            BtnGuardar.setText("ACTUALIZA");
            LabelTitulo.setText("ACTUALIZA SERVCIO");
            VentanaArticulos.setTitleAt(0, "ACTUALIZA UN SERVICIO");
            VentanaArticulos.setSelectedIndex(0);
            String boton = BtnGuardar.getText();
            if(boton == "ACTUALIZA"){
                BtnGuardar.setEnabled(true);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Miembro no encontrado");
        }
    }
    
    public void selecionaImagenActualiza(){
        PreparedStatement ps;
        ResultSet rs;
        PanelImagen.removeAll();
        PanelImagen.repaint();

        try {
            Connection con =  config.getConexion().getConexion();
            ps = con.prepareStatement("SELECT Imagen FROM servicios WHERE ID_Servicios=?");
            ps.setInt(1, filaId);
            rs = ps.executeQuery();

            BufferedImage buffimg = null;
            byte[] image = null;
            while (rs.next()) {
                image = rs.getBytes("Imagen");
                InputStream img = rs.getBinaryStream(1);
                try {
                    buffimg = ImageIO.read(img);
                    ImagenMySQL imagen = new ImagenMySQL(PanelImagen.getHeight(), PanelImagen.getWidth(), buffimg);
                    PanelImagen.add(imagen);
                    PanelImagen.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(servicios.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    public void limpiar(){
        TxtNombre.setText("");
        TxtPrecio.setText("");
        TxtDescripcion.setText("");
        PanelImagen.removeAll();
        PanelImagen.repaint();
        hayimagen = false;
        BtnGuardar.setText("GUARDAR");
        LabelTitulo.setText("AGREGA UN NUEVO SERVICIO");
        VentanaArticulos.setTitleAt(0, "AGREGA UN SERVICIO");
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

        jLabel7 = new javax.swing.JLabel();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        ItemActualiza = new javax.swing.JMenuItem();
        ItemElimina = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        VentanaArticulos = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        LabelTitulo = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtPrecio = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        ComboPlan = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtDescripcion = new javax.swing.JTextArea();
        BtnGuardar = new javax.swing.JButton();
        BtnLimpiar = new javax.swing.JButton();
        jPanel7 = new javax.swing.JPanel();
        PanelImagen = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaServicios = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaImagen = new javax.swing.JTable();

        jLabel7.setText("jLabel7");

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

        LabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedor.png"))); // NOI18N
        LabelTitulo.setText("INSERTA UN NUEVO SERVICIO");

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("NOMBRE:");

        TxtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtNombreActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("PRECIO:");

        TxtPrecio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TxtPrecioActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("PLAN:");

        ComboPlan.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SEMANAL", "MENSUAL", "ANUAL", " " }));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("DECRIPCION:");

        TxtDescripcion.setColumns(20);
        TxtDescripcion.setRows(5);
        jScrollPane2.setViewportView(TxtDescripcion);

        BtnGuardar.setBackground(new java.awt.Color(153, 255, 153));
        BtnGuardar.setText("GUARDAR");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        BtnLimpiar.setBackground(new java.awt.Color(255, 51, 51));
        BtnLimpiar.setText("CANCELAR");
        BtnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TxtNombre)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TxtPrecio)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(ComboPlan, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 542, Short.MAX_VALUE)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnLimpiar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ComboPlan, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 20, Short.MAX_VALUE))
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
            .addGap(0, 280, Short.MAX_VALUE)
        );

        jButton1.setBackground(new java.awt.Color(0, 51, 102));
        jButton1.setForeground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paisaje.png"))); // NOI18N
        jButton1.setText("INSERTA IMAGEN");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE))
                .addGap(36, 36, 36))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(LabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 910, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(LabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        VentanaArticulos.addTab("SERVICIOS", new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedor.png")), jPanel2); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papel.png"))); // NOI18N
        jLabel6.setText("MIS SERVICIOS");

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 456, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextField4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        TablaServicios.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaServicios.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(TablaServicios);

        TablaImagen.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(TablaImagen);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
            .addComponent(jScrollPane3)
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        VentanaArticulos.addTab("MIS SERVICIOS", new javax.swing.ImageIcon(getClass().getResource("/imagenes/papel.png")), jPanel3); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaArticulos)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaArticulos)
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

    private void TxtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtNombreActionPerformed

    private void TxtPrecioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TxtPrecioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TxtPrecioActionPerformed

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        validar();
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
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
    }//GEN-LAST:event_jButton1ActionPerformed

    private void ItemEliminaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemEliminaActionPerformed
        Confirmar_eliminacion();
    }//GEN-LAST:event_ItemEliminaActionPerformed

    private void ItemActualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemActualizaActionPerformed
        actualizar();
    }//GEN-LAST:event_ItemActualizaActionPerformed

    private void BtnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_BtnLimpiarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnLimpiar;
    private javax.swing.JComboBox<String> ComboPlan;
    private javax.swing.JMenuItem ItemActualiza;
    private javax.swing.JMenuItem ItemElimina;
    private javax.swing.JLabel LabelTitulo;
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JTable TablaImagen;
    private javax.swing.JTable TablaServicios;
    private javax.swing.JTextArea TxtDescripcion;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtPrecio;
    private javax.swing.JTabbedPane VentanaArticulos;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
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
