package co.com.planit.lavapp.models;

import java.io.Serializable;

import javax.crypto.SecretKey;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */

@SuppressWarnings("serial")
public class Estrato_TO implements Serializable{

    /**
     *
     * Columna idEstrato
     */
    private int idEstrato;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    //Constructores
    public Estrato_TO() {
    }

    public Estrato_TO(int idEstrato) {
        this.idEstrato = idEstrato;
    }

    //Constructor General
    public Estrato_TO(int idEstrato, String nombre) {
        this.idEstrato = idEstrato;
        this.nombre = nombre;
    }

    //Getter and Setter
    public int getIdEstrato() {
        return idEstrato;
    }

    public void setIdEstrato(int idEstrato) {
        this.idEstrato = idEstrato;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Estrato_TO{" + "idEstrato=" + idEstrato + ", nombre=" + nombre + '}';
    }

}
