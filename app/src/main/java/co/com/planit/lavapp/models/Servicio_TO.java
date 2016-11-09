package co.com.planit.lavapp.models;


import java.io.Serializable;

/**
 *
 * Objeto de negocios que modelo un Servicio
 *
 * Tabla Relacionada Servicio
 *
 * @author Planit
 */

@SuppressWarnings("serial")
public class Servicio_TO implements Serializable{

    /**
     *
     * Columna idServicio
     */
    private int idServicio;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    //Constructores
    public Servicio_TO() {
    }

    //Constructor General

    public Servicio_TO(int idServicio) {
        this.idServicio = idServicio;
    }


    public Servicio_TO(int idServicio, String nombre) {
        this.idServicio = idServicio;
        this.nombre = nombre;
    }

    //Getter and Setter
    public int getIdServicio() {
        return idServicio;
    }

    public void setIdServicio(int idServicio) {
        this.idServicio = idServicio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Servicio_TO{" + "idServicio=" + idServicio + ", nombre=" + nombre + '}';
    }

}
