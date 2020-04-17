/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codigo;

/**
 *
 * @author Alcantara Trejo
 */
public class usuario {
    
    int id;
    String lada;
    String telefono;
    String logo;
    String descripcion;
    String nombre;
    int id_direcciones;
    int id_redes_sociales;
    String contrasenia;
    String correo;

    public usuario() {
    }

    public usuario(int id, String lada, String telefono, String logo, String descripcion, String nombre, int id_direcciones, int id_redes_sociales, String contrasenia, String correo) {
        this.id = id;
        this.lada = lada;
        this.telefono = telefono;
        this.logo = logo;
        this.descripcion = descripcion;
        this.nombre = nombre;
        this.id_direcciones = id_direcciones;
        this.id_redes_sociales = id_redes_sociales;
        this.contrasenia = contrasenia;
        this.correo = correo;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLada() {
        return lada;
    }

    public void setLada(String lada) {
        this.lada = lada;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getId_direcciones() {
        return id_direcciones;
    }

    public void setId_direcciones(int id_direcciones) {
        this.id_direcciones = id_direcciones;
    }

    public int getId_redes_sociales() {
        return id_redes_sociales;
    }

    public void setId_redes_sociales(int id_redes_sociales) {
        this.id_redes_sociales = id_redes_sociales;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }   
}
