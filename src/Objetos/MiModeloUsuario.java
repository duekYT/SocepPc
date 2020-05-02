/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objetos;

import java.io.File;

/**
 *
 * @author Acer
 */
public class MiModeloUsuario {
    private int idUsuario;
    private String nombreUsuario;
    private String contrasenia;
    private String Correo;
    
    //Variables Socio
    private String lada;
    private String Telefono;
    private File logo;
    private String mision;
    private int idDirecciones;
    private int idRedesSociales;
    private String NombreSocio;
    private String vision;
    //fin variables Socio

    private int id_rol;
    private String rol;

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String Correo) {
        this.Correo = Correo;
    }

    public String getLada() {
        return lada;
    }

    public void setLada(String lada) {
        this.lada = lada;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String Telefono) {
        this.Telefono = Telefono;
    }

    public File getLogo() {
        return logo;
    }

    public void setLogo(File logo) {
        this.logo = logo;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }

    public int getIdDirecciones() {
        return idDirecciones;
    }

    public void setIdDirecciones(int idDirecciones) {
        this.idDirecciones = idDirecciones;
    }

    public int getIdRedesSociales() {
        return idRedesSociales;
    }

    public void setIdRedesSociales(int idRedesSociales) {
        this.idRedesSociales = idRedesSociales;
    }

    public String getNombreSocio() {
        return NombreSocio;
    }

    public void setNombreSocio(String NombreSocio) {
        this.NombreSocio = NombreSocio;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
    
    
    
    
}
