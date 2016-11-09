package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */

@SuppressWarnings("serial")
public class Estado_TO implements Serializable {

    /**
     *
     * Columna idEstado
     */
    private int idEstado;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    //Constructores
    public Estado_TO() {
    }

    //Constructor General
    public Estado_TO(int idEstado, String nombre) {
        this.idEstado = idEstado;
        this.nombre = nombre;
    }

    public Estado_TO(int idEstado) {
        this.idEstado = idEstado;
    }



    //Getter and Setter
    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Estado_TO{" + "idEstado=" + idEstado + ", nombre=" + nombre + '}';
    }

}

