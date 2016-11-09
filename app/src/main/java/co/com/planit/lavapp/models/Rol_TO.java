package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */

@SuppressWarnings("serial")
public class Rol_TO implements Serializable{

    /**
     *
     * Columna idRol
     */
    private int idRol;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    //Constructores
    public Rol_TO() {
    }

    //Constructor General
    public Rol_TO(int idRol, String nombre) {
        this.idRol = idRol;
        this.nombre = nombre;
    }

    public Rol_TO(int idRol) {
        this.idRol = idRol;
    }



    //Getter and Setter
    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Rol_TO{" + "idRol=" + idRol + ", nombre=" + nombre + '}';
    }

}

