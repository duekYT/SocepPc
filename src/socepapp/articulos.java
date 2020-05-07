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
import coperativa.informacion;
import java.awt.Image;
import java.awt.Toolkit;
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
public class articulos extends javax.swing.JInternalFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    MiModeloUsuario mod;
    int filaId;
    File fichero;
    FileInputStream fis;
    
    String campos = "articulo.Id_socios, articulo.Nombre, articulo.Descripcion, articulo.Precio, articulo.Imagen";
    String campos2 = "articulo.Nombre = ?, articulo.Descripcion = ?, articulo.Precio = ?, articulo.Imagen = ?";
    String id = "articulo.Id";
    String tabla = "articulo";
    
    String tablainner = "articulo INNER JOIN socios ON articulo.Id_socios = socios.Id";
    String camposTabla = "articulo.Id, articulo.Nombre, articulo.Descripcion, articulo.Precio";
    /**
     * Creates new form articulos
     */
    public articulos() {
        initComponents();
    }
    
    public articulos(MiModeloUsuario mod) {
        this.mod = mod;
        initComponents();
        mostrar_articulos();
    }
    
    public void validarRegistro(){
        String boton = BtnGuardar.getText();
        switch(boton){
            case "GUARDAR":
                if(TxtNombre.getText().isEmpty() || TxtDescripcion.getText().isEmpty() || TxtPrecio.getText().isEmpty()){
                    JOptionPane.showMessageDialog(null, "Algunos campos estan vacios");
                }else if(crud.CompruebaExistencias(TxtNombre.getText(), tabla, "Nombre", id) == 0){
                    int idusuario = mod.getIdUsuario();
                    String nombre = TxtNombre.getText();
                    String descripcion = TxtDescripcion.getText();
                    double precio = Double.parseDouble(TxtPrecio.getText());

                    List<Object> datos = new ArrayList<>();
                    datos.add(idusuario);
                    datos.add(nombre);
                    datos.add(descripcion);
                    datos.add(precio);
                    datos.add(fichero);

                    try {
                        if(crud.ingresar(datos, tabla, campos)){
                            int idarticulo = crud.Seleccionar_id("articulo WHERE articulo.Id_socios = " + mod.getIdUsuario(), "articulo.Id");
                            System.out.println("El id nuevo es "+idarticulo);
                            ActualizaImagen(idarticulo);
                            JOptionPane.showMessageDialog(null, "Imagen Guardada");
                            JOptionPane.showMessageDialog(null, "datos guardados");
                            mostrar_articulos();
                            BtnGuardar.setText("GUARDAR");
                            LabelTitulo.setText("AGREGA UN NUEVO ARTICULO");
                            VentanaArticulos.setTitleAt(0, "AGREGA UN ARTICULO");
                            VentanaArticulos.setSelectedIndex(1);
                            
                        }else{
                            JOptionPane.showMessageDialog(null, "Error al registrar");
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(articulos.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    JOptionPane.showMessageDialog(null, "el articulo existe");
                }
            break;
            
            case "ACTUALIZA":
            try {
                    String nombre = TxtNombre.getText();
                    String descripcion = TxtDescripcion.getText();
                    double precio = Double.parseDouble(TxtPrecio.getText());

                    List<Object> datos2 = new ArrayList<>();
                    datos2.add(nombre);
                    datos2.add(descripcion);
                    datos2.add(precio);
                    datos2.add(fichero);
                    datos2.add(filaId);

                    if(crud.actualizar(datos2, tabla, campos2, id)){
                        ActualizaImagen(filaId);
                        mostrar_articulos();
                        BtnGuardar.setText("GUARDAR");
                        LabelTitulo.setText("AGREGA UN NUEVO ARTICULO");
                        VentanaArticulos.setTitleAt(0, "AGREGA UN ARTICULO");
                        VentanaArticulos.setSelectedIndex(1);
                    }else{
                        JOptionPane.showMessageDialog(null, "Error al registrar");
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(MisionVision.class.getName()).log(Level.SEVERE, null, ex);
            }
            break;
        }
        
    }
    
    public void ActualizaImagen(int id){
        try {
        PreparedStatement ps;
        ResultSet rs;
        Connection con =  config.getConexion().getConexion();
        ps = con.prepareStatement("UPDATE articulo SET articulo.Imagen = ? WHERE articulo.Id = ?");
        ps.setBinaryStream(1, fis, (int) fichero.length());
        ps.setInt(2, id);

        ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(articulos.class.getName()).log(Level.SEVERE, null, ex);
        }
        JOptionPane.showMessageDialog(null, "ARTICULO ACTUALIZADO");
    }
    
    
    public void Seleccionaimagen(){
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
    }
    
    public void mostrar_articulos(){
        try {
            DefaultTableModel modeloTabla = new DefaultTableModel();
            modeloTabla = crud.SeleccionaTabla(tablainner, camposTabla, "socios.Id", mod.getIdUsuario());
            TablaArticulos.setModel(modeloTabla);
            TablaArticulos.setRowHeight(64);
            mostrarTablaImagenes();
        } catch (SQLException ex) {
            Logger.getLogger(Miembros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void mostrarTablaImagenes(){
        TablaDeImagenes.setDefaultRenderer(Object.class, new TablaImagen());
        DefaultTableModel modeloTabla = new DefaultTableModel();
        
        ResultSet rs;
            rs = crud.ver(tablainner, "articulo.Imagen", "socios.Id", mod.getIdUsuario());
            modeloTabla.addColumn("imagen");
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
            TablaDeImagenes.setModel(modeloTabla);
            TablaDeImagenes.setRowHeight(64);
        } catch (SQLException ex) {
            Logger.getLogger(articulos.class.getName()).log(Level.SEVERE, null, ex);
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
        int fila = TablaArticulos.getSelectedRow();
        
        if(fila >= 0){
            try {
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaArticulos.getModel();
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
                Logger.getLogger(Miembros.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            JOptionPane.showMessageDialog(null, "Miembro no encontrado");
        }
    }
    
    public void actualizar(){
        int fila = TablaArticulos.getSelectedRow();
        if(fila >= 0){
            DefaultTableModel NuevoModeloTabla = (DefaultTableModel) TablaArticulos.getModel();
            filaId = Integer.parseInt(NuevoModeloTabla.getValueAt(fila, 0).toString());
            String filaNombre = NuevoModeloTabla.getValueAt(fila, 1).toString();
            String filaDescripcion = NuevoModeloTabla.getValueAt(fila, 2).toString();
            String filaPrecio = NuevoModeloTabla.getValueAt(fila, 3).toString();
            
            TxtNombre.setText(filaNombre);
            TxtPrecio.setText(filaPrecio);
            TxtDescripcion.setText(filaDescripcion);
            selecionaImagenActualiza();
            
            BtnGuardar.setText("ACTUALIZA");
            LabelTitulo.setText("ACTUALIZA ARTICULO");
            VentanaArticulos.setTitleAt(0, "ACTUALIZA UN ARTICULO");
            VentanaArticulos.setSelectedIndex(0);
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
            ps = con.prepareStatement("SELECT Imagen FROM articulo WHERE Id=?");
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
                    Logger.getLogger(informacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
//            Image img = Toolkit.getDefaultToolkit().createImage(image);
//            ImageIcon icon = new ImageIcon(img.getScaledInstance(402, 390, Image.SCALE_DEFAULT));
//            lblImagen.setIcon(icon);
//            lblImagen.repaint();
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

        jPopupMenu1 = new javax.swing.JPopupMenu();
        ItemActualiza = new javax.swing.JMenuItem();
        ItemBorrar = new javax.swing.JMenuItem();
        jPanel3 = new javax.swing.JPanel();
        VentanaArticulos = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        LabelTitulo = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        TxtPrecio = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        TxtNombre = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        TxtDescripcion = new javax.swing.JTextArea();
        BtnGuardar = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        PanelImagen = new javax.swing.JPanel();
        BtnImagen = new javax.swing.JButton();
        jPanel8 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton4 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        TablaArticulos = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        TablaDeImagenes = new javax.swing.JTable();

        ItemActualiza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/actualizar.png"))); // NOI18N
        ItemActualiza.setText("ACTUALIZAR");
        ItemActualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemActualizaActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ItemActualiza);

        ItemBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/Eliminar.png"))); // NOI18N
        ItemBorrar.setText("ELIMINAR");
        ItemBorrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ItemBorrarActionPerformed(evt);
            }
        });
        jPopupMenu1.add(ItemBorrar);

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        LabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        LabelTitulo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/anadir-a-la-cesta.png"))); // NOI18N
        LabelTitulo.setText("INSERTA UN NUEVO PRODUCTO");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("NOMBRE:");

        TxtPrecio.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("PRECIO:");

        TxtNombre.setHorizontalAlignment(javax.swing.JTextField.LEFT);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("DESCRIPCION:");

        TxtDescripcion.setColumns(20);
        TxtDescripcion.setRows(5);
        jScrollPane2.setViewportView(TxtDescripcion);

        BtnGuardar.setBackground(new java.awt.Color(102, 255, 102));
        BtnGuardar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        BtnGuardar.setText("GUARDAR");
        BtnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnGuardarActionPerformed(evt);
            }
        });

        jButton3.setBackground(new java.awt.Color(255, 0, 0));
        jButton3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton3.setForeground(new java.awt.Color(255, 255, 255));
        jButton3.setText("CANCELAR");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TxtPrecio)
            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(TxtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane2)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(BtnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 154, Short.MAX_VALUE)
                .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(TxtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(17, 17, 17)
                .addComponent(TxtPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(37, 37, 37)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(BtnGuardar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

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

        BtnImagen.setBackground(new java.awt.Color(0, 51, 102));
        BtnImagen.setForeground(new java.awt.Color(255, 255, 255));
        BtnImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/paisaje.png"))); // NOI18N
        BtnImagen.setText("INSERTA IMAGEN");
        BtnImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnImagenActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(BtnImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 322, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BtnImagen)
                .addContainerGap(42, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(LabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(LabelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );

        VentanaArticulos.addTab("PRODUCTO", new javax.swing.ImageIcon(getClass().getResource("/imagenes/anadir-a-la-cesta.png")), jPanel1); // NOI18N

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/papel.png"))); // NOI18N
        jLabel6.setText("MIS PRODUCTOS");

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/buscar.png"))); // NOI18N
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

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
                .addGap(0, 505, Short.MAX_VALUE))
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

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        TablaArticulos.setModel(new javax.swing.table.DefaultTableModel(
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
        TablaArticulos.setComponentPopupMenu(jPopupMenu1);
        jScrollPane1.setViewportView(TablaArticulos);

        TablaDeImagenes.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(TablaDeImagenes);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 708, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 447, Short.MAX_VALUE)
                    .addComponent(jScrollPane3)))
        );

        VentanaArticulos.addTab("MIS PRODUCTOS", new javax.swing.ImageIcon(getClass().getResource("/imagenes/papel.png")), jPanel8); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaArticulos)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(VentanaArticulos)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnGuardarActionPerformed
        validarRegistro();
    }//GEN-LAST:event_BtnGuardarActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed

    }//GEN-LAST:event_jButton4ActionPerformed

    private void ItemBorrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemBorrarActionPerformed
        Confirmar_eliminacion();
    }//GEN-LAST:event_ItemBorrarActionPerformed

    private void BtnImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnImagenActionPerformed
        Seleccionaimagen();
    }//GEN-LAST:event_BtnImagenActionPerformed

    private void ItemActualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ItemActualizaActionPerformed
        actualizar();
    }//GEN-LAST:event_ItemActualizaActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnGuardar;
    private javax.swing.JButton BtnImagen;
    private javax.swing.JMenuItem ItemActualiza;
    private javax.swing.JMenuItem ItemBorrar;
    private javax.swing.JLabel LabelTitulo;
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JTable TablaArticulos;
    private javax.swing.JTable TablaDeImagenes;
    private javax.swing.JTextArea TxtDescripcion;
    private javax.swing.JTextField TxtNombre;
    private javax.swing.JTextField TxtPrecio;
    private javax.swing.JTabbedPane VentanaArticulos;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jTextField4;
    // End of variables declaration//GEN-END:variables
}
