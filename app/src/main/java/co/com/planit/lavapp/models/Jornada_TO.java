package co.com.planit.lavapp.models;

import java.io.Serializable;

import javax.crypto.SecretKey;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */

@SuppressWarnings("serial")
public class Jornada_TO implements Serializable{

    /**
     *
     * Columna idJornada
     */
    private int idJornada;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    //Constructores
    public Jornada_TO() {
    }

    //Constructor General

    public Jornada_TO(int idJornada) {
        this.idJornada = idJornada;
    }

    public Jornada_TO(int idJornada, String nombre) {
        this.idJornada = idJornada;
        this.nombre = nombre;
    }

    //Getter and Setter
    public int getIdJornada() {
        return idJornada;
    }

    public void setIdJornada(int idJornada) {
        this.idJornada = idJornada;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Jornada_TO{" + "idJornada=" + idJornada + ", nombre=" + nombre + '}';
    }

}
