package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */

@SuppressWarnings("serial")
public class Zona_TO implements Serializable{

    /**
     *
     * Columna idZona
     */
    private int idZona;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    /**
     *
     * Columna descripcion
     */
    private String descripcion;

    //Constructores

    public Zona_TO() {
    }

    public Zona_TO(int idZona) {
        this.idZona = idZona;
    }


    //Constructor general
    public Zona_TO(int idZona, String nombre, String descripcion) {
        this.idZona = idZona;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    //Getter and Setter

    public int getIdZona() {
        return idZona;
    }

    public void setIdZona(int idZona) {
        this.idZona = idZona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Zona_TO{" + "idZona=" + idZona + ", nombre=" + nombre + ", descripcion=" + descripcion + '}';
    }

}

