package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 *
 * Objeto de negocios que modelo un SubServicio
 *
 * Tabla Relacionada SubServicio
 *
 * @author Planit
 */

@SuppressWarnings("serial")
public class SubServicio_TO implements Serializable {

    /**
     *
     * Columna idSubServicio
     */
    private int idSubServicio;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    /**
     *
     * Columna idServicio
     */
    private Servicio_TO servicio;

    //Constructores

    public SubServicio_TO() {
    }

    public SubServicio_TO(int idSubServicio) {
        this.idSubServicio = idSubServicio;
    }

    //Constructor General

    public SubServicio_TO(int idSubServicio, String nombre, Servicio_TO servicio) {
        this.idSubServicio = idSubServicio;
        this.nombre = nombre;
        this.servicio = servicio;
    }

    //Getter and Setter

    public int getIdSubServicio() {
        return idSubServicio;
    }

    public void setIdSubServicio(int idSubServicio) {
        this.idSubServicio = idSubServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Servicio_TO getServicio() {
        return servicio;
    }

    public void setServicio(Servicio_TO servicio) {
        this.servicio = servicio;
    }

    @Override
    public String toString() {
        return "SubServicio_TO{" + "idSubServicio=" + idSubServicio + ", nombre=" + nombre + ", servicio=" + servicio + '}';
    }



}
