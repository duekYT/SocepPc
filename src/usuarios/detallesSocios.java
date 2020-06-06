/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usuarios;

import Objetos.ImagenMySQL;
import Objetos.ModelitoSocio;
import codigo.ConsultasCrud;
import java.awt.Desktop;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import utilidades.configuracionXml;

/**
 *
 * @author Acer
 */
public class detallesSocios extends javax.swing.JInternalFrame {
    configuracionXml config = new configuracionXml();
    ConsultasCrud crud = new ConsultasCrud( config.getConexion().getConexion());
    ModelitoSocio mod = new ModelitoSocio();
    String Nombresocio;
    String tabla = "socios";
    String campos = "socios.Nombre";
    

    /**
     * Creates new form detallesSocios
     */
    public detallesSocios() {
        initComponents();
    }
    
    public detallesSocios( ModelitoSocio mod) {
        this.mod = mod;
        initComponents();
        Nombresocio = mod.getNombre();
        System.out.println("Hola "+mod.getNombre());
        LabelNombre.setText("Empresa: "+mod.getNombre());
        MisionVision();
        selecionaImagenActualiza(Nombresocio);
        mostrar_articulos();
        ListaArticulos.setSelectedIndex(0);
        ListaServicios.setSelectedIndex(0);
        ListaEventos.setSelectedIndex(0);
        seleccionearItem();
    }
    
    public void MisionVision(){
        String Mision = crud.SeleccinaUnCampoLista(tabla, "socios.Mision", "socios.Nombre", Nombresocio);
        AreaMision.setText(Mision);
        String Vision = crud.SeleccinaUnCampoLista(tabla, "socios.Vision", "socios.Nombre", Nombresocio);
        AreaVision.setText(Vision);
        
        String tablain = "direcciones_socios INNER JOIN socios on direcciones_socios.Id = socios.Id_Direcciones";
        
        String Direccion = crud.SeleccinaUnCampoLista(tablain, "direcciones_socios.Direccion", "socios.Nombre", Nombresocio);
        String Localidad = crud.SeleccinaUnCampoLista(tablain, "direcciones_socios.Municipio", "socios.Nombre", Nombresocio);
        String Estado = crud.SeleccinaUnCampoLista(tablain, "direcciones_socios.Estado", "socios.Nombre", Nombresocio);
        
        LabelDireccion.setText("Encuentranos en: "+Direccion+", "+Localidad+", "+Estado);
    }
    
    public void selecionaImagenActualiza(String res){
        PreparedStatement ps;
        ResultSet rs;
        PanelImagen.removeAll();
        PanelImagen.repaint();

        try {
            Connection con =  config.getConexion().getConexion();
            ps = con.prepareStatement("SELECT logo FROM socios WHERE socios.Nombre=?");
            ps.setString(1, res);
            rs = ps.executeQuery();

            BufferedImage buffimg = null;
            byte[] image = null;
            while (rs.next()) {
                image = rs.getBytes("logo");
                InputStream img = rs.getBinaryStream(1);
                try {
                    buffimg = ImageIO.read(img);
                    ImagenMySQL imagen = new ImagenMySQL(200, 200, buffimg);
                    PanelImagen.add(imagen);
                    PanelImagen.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
    
    public void mostrar_articulos(){
        String tablain = "articulo INNER JOIN socios on articulo.Id_socios = socios.Id";
        String tablainS = "servicios INNER JOIN socios on servicios.MiSocio = socios.Id";
        String tablainE = "eventos INNER JOIN socios on eventos.Id_socio = socios.Id";
        try {
            DefaultListModel modeloLista = new DefaultListModel();
            modeloLista = crud.listaCombo(tablain, "articulo.Nombre", campos, Nombresocio);
            ListaArticulos.setModel(modeloLista);
            
            DefaultListModel modeloListaS = new DefaultListModel();
            modeloListaS = crud.listaCombo(tablainS, "servicios.Nombre", campos, Nombresocio);
            ListaServicios.setModel(modeloListaS);
            
            DefaultListModel modeloListaE = new DefaultListModel();
            modeloListaE = crud.listaCombo(tablainE, "eventos.Nombre", campos, Nombresocio);
            ListaEventos.setModel(modeloListaE);
            
        } catch (SQLException ex) {
            Logger.getLogger(UsuarioProductos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void seleccionearItem(){
        int fila = ListaArticulos.getSelectedIndex();
        if(fila >= 0){
            
            DefaultListModel NuevoModeloLista = (DefaultListModel) ListaArticulos.getModel();
            String filaNombre = NuevoModeloLista.getElementAt(fila).toString();
            String precio = crud.SeleccinaUnCampoLista("articulo", "articulo.Precio", "articulo.Nombre", filaNombre);
            String descripcion = crud.SeleccinaUnCampoLista("articulo", "articulo.Descripcion", "articulo.Nombre", filaNombre);
            
            selecionaImagenActualizaArticulo("Imagen","articulo","articulo.Nombre",filaNombre,PanelImagenArticulo);
            LabelNombreArticulo.setText("Nombre: "+filaNombre);
            LabelPrecioArticulo.setText("Precio: $"+precio);
            DescripcionAreaArticulo.setText(descripcion);
        }
        
        int filaS = ListaServicios.getSelectedIndex();
        
        if(filaS >= 0){
            DefaultListModel NuevoModeloLista = (DefaultListModel) ListaServicios.getModel();
            String filaNombreS = NuevoModeloLista.getElementAt(filaS).toString();
            String precio = crud.SeleccinaUnCampoLista("servicios", "servicios.Precio", "servicios.Nombre", filaNombreS);
            String descripcion = crud.SeleccinaUnCampoLista("servicios", "servicios.Descripcion", "servicios.Nombre", filaNombreS);
            String Plan = crud.SeleccinaUnCampoLista("servicios", "servicios.Plan", "servicios.Nombre", filaNombreS);
            selecionaImagenActualizaArticulo("Imagen","servicios","servicios.Nombre",filaNombreS,PanelImagenServicios);
            LabelNombreServicios.setText("Nombre: "+filaNombreS);
            LabelPrecioServicios.setText("Precio: $"+precio);
            LabelPlanServicios.setText("Plan: "+Plan);
            DescripcionAreaServicios.setText(descripcion);
        }
        
        int filaE = ListaEventos.getSelectedIndex();
        if(filaE >= 0){
            DefaultListModel NuevoModeloLista = (DefaultListModel) ListaEventos.getModel();
            String filaNombreE = NuevoModeloLista.getElementAt(filaE).toString();
            String descripcion = crud.SeleccinaUnCampoLista("eventos", "eventos.Descripcion", "eventos.Nombre", filaNombreE);
            String fechaInicio = crud.SeleccinaUnCampoLista("eventos", "eventos.Fecha_inicio", "eventos.Nombre", filaNombreE);
            String fechaFinal = crud.SeleccinaUnCampoLista("eventos", "eventos.Fecha_final", "eventos.Nombre", filaNombreE);
            String estado = crud.SeleccinaUnCampoLista("eventos", "eventos.Estado", "eventos.Nombre", filaNombreE);
            String localidad = crud.SeleccinaUnCampoLista("eventos", "eventos.Localidad", "eventos.Nombre", filaNombreE);
            String direccion = crud.SeleccinaUnCampoLista("eventos", "eventos.Direccion", "eventos.Nombre", filaNombreE);
            selecionaImagenActualizaArticulo("Imagen","eventos","eventos.Nombre",filaNombreE,PanelImagenEventos);
            LabelNombreEvento.setText("Nombre: "+filaNombreE);
            DescripcionAreaEvento.setText(descripcion);
            LabelFechaEventos.setText("Inicia el: "+fechaInicio+" Finaliza el: "+fechaFinal);
            LabelEstadoEvento.setText("Estado: "+estado);
            LabelLocalidadEvento.setText("Localidad: "+localidad);
            LabelDireccionEvento.setText("Direccion: "+direccion);
            
        }
    }
    
     public void selecionaImagenActualizaArticulo(String Campo, String Tabla, String CampoWhere ,String res, JPanel panel){
        PreparedStatement ps;
        ResultSet rs;
        panel.removeAll();
        panel.repaint();

        try {
            Connection con =  config.getConexion().getConexion();
            ps = con.prepareStatement("SELECT " +Campo+" FROM " + Tabla +" WHERE "+CampoWhere+" = ?;");
            ps.setString(1, res);
            rs = ps.executeQuery();

            BufferedImage buffimg = null;
            byte[] image = null;
            while (rs.next()) {
                image = rs.getBytes(Campo);
                InputStream img = rs.getBinaryStream(1);
                try {
                    buffimg = ImageIO.read(img);
                    ImagenMySQL imagen = new ImagenMySQL(302, 306, buffimg);
                    panel.add(imagen);
                    panel.repaint();
                } catch (IOException ex) {
                    Logger.getLogger(UsuarioProductos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println(ex.toString());
        }
    }
     
    public void redesSociales(int caso){
        String tablaRin = "redes_sociales INNER JOIN socios ON redes_sociales.Id = socios.Id";
        String Facebook = crud.SeleccinaUnCampoLista(tablaRin, "redes_sociales.Facebook", campos, Nombresocio);
        String Instagram = crud.SeleccinaUnCampoLista(tablaRin, "redes_sociales.Instagram", campos, Nombresocio);
        String Twitter = crud.SeleccinaUnCampoLista(tablaRin, "redes_sociales.Twitter", campos, Nombresocio);
        String Youtube = crud.SeleccinaUnCampoLista(tablaRin, "redes_sociales.Youtube", campos, Nombresocio);
        URL url=null;
        switch(caso){
            case 1:
                if(Facebook.isEmpty()){
                    JOptionPane.showMessageDialog(null, "La empresa "+Nombresocio+" no cuenta con Facebook");
                }else{
                    try {
                        url = new URL(Facebook);
                        try {
                            Desktop.getDesktop().browse(url.toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "URL no encontrada");
                    }
                }
            break;
            
            case 2:
                if(Instagram.isEmpty()){
                    JOptionPane.showMessageDialog(null, "La empresa "+Nombresocio+" no cuenta con Instagram");
                }else{
                    try {
                        url = new URL(Instagram);
                        try {
                            Desktop.getDesktop().browse(url.toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "URL no encontrada");
                    }
                }
            break;
            
            case 3:
                if(Twitter.isEmpty()){
                    JOptionPane.showMessageDialog(null, "La empresa "+Nombresocio+" no cuenta con Twitter");
                }else{
                    try {
                        url = new URL(Twitter);
                        try {
                            Desktop.getDesktop().browse(url.toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "URL no encontrada");
                    }
                }
            break;
            
            case 4:
                if(Youtube.isEmpty()){
                    JOptionPane.showMessageDialog(null, "URL no encontrada");
                }else{
                    try {
                        url = new URL(Youtube);
                        try {
                            Desktop.getDesktop().browse(url.toURI());
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (URISyntaxException e) {
                            e.printStackTrace();
                        }
                    } catch (MalformedURLException e1) {
                        e1.printStackTrace();
                        JOptionPane.showMessageDialog(null, "URL no encontrada");
                    }
                }
            break;
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
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel10 = new javax.swing.JPanel();
        PanelImagen = new javax.swing.JPanel();
        LabelNombre = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        AreaMision = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        AreaVision = new javax.swing.JTextArea();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        LabelDireccion = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jPanel11 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        ListaArticulos = new javax.swing.JList<>();
        PanelImagenArticulo = new javax.swing.JPanel();
        LabelNombreArticulo = new javax.swing.JLabel();
        LabelPrecioArticulo = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        DescripcionAreaArticulo = new javax.swing.JTextArea();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane6 = new javax.swing.JScrollPane();
        ListaServicios = new javax.swing.JList<>();
        PanelImagenServicios = new javax.swing.JPanel();
        LabelNombreServicios = new javax.swing.JLabel();
        LabelPlanServicios = new javax.swing.JLabel();
        LabelPrecioServicios = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        DescripcionAreaServicios = new javax.swing.JTextArea();
        jPanel13 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        ListaEventos = new javax.swing.JList<>();
        PanelImagenEventos = new javax.swing.JPanel();
        LabelNombreEvento = new javax.swing.JLabel();
        LabelFechaEventos = new javax.swing.JLabel();
        LabelEstadoEvento = new javax.swing.JLabel();
        LabelLocalidadEvento = new javax.swing.JLabel();
        LabelDireccionEvento = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        DescripcionAreaEvento = new javax.swing.JTextArea();

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N

        PanelImagen.setBackground(new java.awt.Color(0, 255, 102));

        javax.swing.GroupLayout PanelImagenLayout = new javax.swing.GroupLayout(PanelImagen);
        PanelImagen.setLayout(PanelImagenLayout);
        PanelImagenLayout.setHorizontalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 200, Short.MAX_VALUE)
        );
        PanelImagenLayout.setVerticalGroup(
            PanelImagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        LabelNombre.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelNombre.setText("jLabel1");

        AreaMision.setEditable(false);
        AreaMision.setColumns(20);
        AreaMision.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        AreaMision.setLineWrap(true);
        AreaMision.setRows(5);
        jScrollPane1.setViewportView(AreaMision);

        AreaVision.setEditable(false);
        AreaVision.setColumns(20);
        AreaVision.setFont(new java.awt.Font("Monospaced", 1, 18)); // NOI18N
        AreaVision.setRows(5);
        jScrollPane2.setViewportView(AreaVision);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel2.setText("Mision:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel3.setText("Vision:");

        LabelDireccion.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelDireccion.setText("jLabel1");

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/facebook.png"))); // NOI18N
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/instagram.png"))); // NOI18N
        jButton5.setBorder(null);
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/twitter.png"))); // NOI18N
        jButton6.setBorder(null);
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/youtube.png"))); // NOI18N
        jButton7.setBorder(null);
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(PanelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addComponent(LabelNombre)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jPanel10Layout.createSequentialGroup()
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
                                    .addComponent(jScrollPane2)))))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(LabelNombre)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE)
                            .addComponent(jScrollPane1))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(LabelDireccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Informacion", jPanel10);

        jPanel11.setBackground(new java.awt.Color(255, 255, 255));

        ListaArticulos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ListaArticulos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaArticulosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ListaArticulosMouseReleased(evt);
            }
        });
        ListaArticulos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListaArticulosValueChanged(evt);
            }
        });
        jScrollPane3.setViewportView(ListaArticulos);

        javax.swing.GroupLayout PanelImagenArticuloLayout = new javax.swing.GroupLayout(PanelImagenArticulo);
        PanelImagenArticulo.setLayout(PanelImagenArticuloLayout);
        PanelImagenArticuloLayout.setHorizontalGroup(
            PanelImagenArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );
        PanelImagenArticuloLayout.setVerticalGroup(
            PanelImagenArticuloLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        LabelNombreArticulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelNombreArticulo.setText("jLabel1");

        LabelPrecioArticulo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelPrecioArticulo.setForeground(new java.awt.Color(51, 255, 51));
        LabelPrecioArticulo.setText("jLabel4");

        DescripcionAreaArticulo.setEditable(false);
        DescripcionAreaArticulo.setColumns(20);
        DescripcionAreaArticulo.setRows(5);
        jScrollPane4.setViewportView(DescripcionAreaArticulo);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PanelImagenArticulo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNombreArticulo)
                    .addComponent(LabelPrecioArticulo)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelImagenArticulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addComponent(LabelNombreArticulo)
                        .addGap(18, 18, 18)
                        .addComponent(LabelPrecioArticulo)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 239, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Productos", jPanel11);

        jPanel12.setBackground(new java.awt.Color(255, 255, 255));

        ListaServicios.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ListaServicios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaServiciosMouseClicked(evt);
            }
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                ListaServiciosMouseReleased(evt);
            }
        });
        ListaServicios.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                ListaServiciosValueChanged(evt);
            }
        });
        jScrollPane6.setViewportView(ListaServicios);

        javax.swing.GroupLayout PanelImagenServiciosLayout = new javax.swing.GroupLayout(PanelImagenServicios);
        PanelImagenServicios.setLayout(PanelImagenServiciosLayout);
        PanelImagenServiciosLayout.setHorizontalGroup(
            PanelImagenServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );
        PanelImagenServiciosLayout.setVerticalGroup(
            PanelImagenServiciosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        LabelNombreServicios.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelNombreServicios.setText("jLabel1");

        LabelPlanServicios.setBackground(new java.awt.Color(255, 255, 255));
        LabelPlanServicios.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelPlanServicios.setForeground(new java.awt.Color(255, 51, 51));
        LabelPlanServicios.setText("jLabel1");

        LabelPrecioServicios.setBackground(new java.awt.Color(255, 255, 255));
        LabelPrecioServicios.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelPrecioServicios.setForeground(new java.awt.Color(51, 255, 51));
        LabelPrecioServicios.setText("jLabel1");

        DescripcionAreaServicios.setEditable(false);
        DescripcionAreaServicios.setColumns(20);
        DescripcionAreaServicios.setRows(5);
        jScrollPane5.setViewportView(DescripcionAreaServicios);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PanelImagenServicios, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNombreServicios)
                    .addComponent(LabelPlanServicios)
                    .addComponent(LabelPrecioServicios)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(LabelNombreServicios)
                        .addGap(18, 18, 18)
                        .addComponent(LabelPlanServicios)
                        .addGap(18, 18, 18)
                        .addComponent(LabelPrecioServicios)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane5))
                    .addComponent(PanelImagenServicios, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Servicios", jPanel12);

        jPanel13.setBackground(new java.awt.Color(255, 255, 255));

        ListaEventos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
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
        jScrollPane7.setViewportView(ListaEventos);

        javax.swing.GroupLayout PanelImagenEventosLayout = new javax.swing.GroupLayout(PanelImagenEventos);
        PanelImagenEventos.setLayout(PanelImagenEventosLayout);
        PanelImagenEventosLayout.setHorizontalGroup(
            PanelImagenEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 302, Short.MAX_VALUE)
        );
        PanelImagenEventosLayout.setVerticalGroup(
            PanelImagenEventosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 319, Short.MAX_VALUE)
        );

        LabelNombreEvento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelNombreEvento.setText("jLabel1");

        LabelFechaEventos.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        LabelFechaEventos.setText("Inicia el: 0000-00-00 Finaliza el: 0000-00-00");

        LabelEstadoEvento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelEstadoEvento.setText("jLabel1");

        LabelLocalidadEvento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelLocalidadEvento.setText("jLabel1");

        LabelDireccionEvento.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        LabelDireccionEvento.setText("jLabel1");

        DescripcionAreaEvento.setEditable(false);
        DescripcionAreaEvento.setColumns(20);
        DescripcionAreaEvento.setRows(5);
        jScrollPane8.setViewportView(DescripcionAreaEvento);

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(PanelImagenEventos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(LabelNombreEvento)
                    .addComponent(LabelFechaEventos)
                    .addComponent(LabelEstadoEvento)
                    .addComponent(LabelLocalidadEvento)
                    .addComponent(LabelDireccionEvento)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(19, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addComponent(LabelNombreEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelFechaEventos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelEstadoEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelLocalidadEvento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(LabelDireccionEvento)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane8))
                    .addComponent(PanelImagenEventos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane2.addTab("Eventos", jPanel13);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
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

    private void ListaArticulosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaArticulosMouseClicked
        seleccionearItem();
    }//GEN-LAST:event_ListaArticulosMouseClicked

    private void ListaArticulosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaArticulosMouseReleased
        seleccionearItem();
    }//GEN-LAST:event_ListaArticulosMouseReleased

    private void ListaArticulosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListaArticulosValueChanged
        seleccionearItem();
    }//GEN-LAST:event_ListaArticulosValueChanged

    private void ListaServiciosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaServiciosMouseClicked
        seleccionearItem();
    }//GEN-LAST:event_ListaServiciosMouseClicked

    private void ListaServiciosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaServiciosMouseReleased
        seleccionearItem();
    }//GEN-LAST:event_ListaServiciosMouseReleased

    private void ListaServiciosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListaServiciosValueChanged
        seleccionearItem();
    }//GEN-LAST:event_ListaServiciosValueChanged

    private void ListaEventosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEventosMouseClicked
        seleccionearItem();
    }//GEN-LAST:event_ListaEventosMouseClicked

    private void ListaEventosMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEventosMouseReleased
        seleccionearItem();
    }//GEN-LAST:event_ListaEventosMouseReleased

    private void ListaEventosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_ListaEventosValueChanged
        seleccionearItem();
    }//GEN-LAST:event_ListaEventosValueChanged

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        redesSociales(1);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        redesSociales(2);
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        redesSociales(3);
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        redesSociales(4);
    }//GEN-LAST:event_jButton7ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea AreaMision;
    private javax.swing.JTextArea AreaVision;
    private javax.swing.JTextArea DescripcionAreaArticulo;
    private javax.swing.JTextArea DescripcionAreaEvento;
    private javax.swing.JTextArea DescripcionAreaServicios;
    private javax.swing.JLabel LabelDireccion;
    private javax.swing.JLabel LabelDireccionEvento;
    private javax.swing.JLabel LabelEstadoEvento;
    private javax.swing.JLabel LabelFechaEventos;
    private javax.swing.JLabel LabelLocalidadEvento;
    private javax.swing.JLabel LabelNombre;
    private javax.swing.JLabel LabelNombreArticulo;
    private javax.swing.JLabel LabelNombreEvento;
    private javax.swing.JLabel LabelNombreServicios;
    private javax.swing.JLabel LabelPlanServicios;
    private javax.swing.JLabel LabelPrecioArticulo;
    private javax.swing.JLabel LabelPrecioServicios;
    private javax.swing.JList<String> ListaArticulos;
    private javax.swing.JList<String> ListaEventos;
    private javax.swing.JList<String> ListaServicios;
    private javax.swing.JPanel PanelImagen;
    private javax.swing.JPanel PanelImagenArticulo;
    private javax.swing.JPanel PanelImagenEventos;
    private javax.swing.JPanel PanelImagenServicios;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane2;
    // End of variables declaration//GEN-END:variables
}
