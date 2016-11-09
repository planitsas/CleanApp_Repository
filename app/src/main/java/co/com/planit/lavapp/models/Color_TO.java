package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */

@SuppressWarnings("serial")
public class Color_TO implements Serializable {

    /**
     *
     * Columna idColor
     */
    private int idColor;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    //Constructores
    public Color_TO() {
    }

    public Color_TO(int idColor) {
        this.idColor = idColor;
    }

    //Constructor General
    public Color_TO(int idColor, String nombre) {
        this.idColor = idColor;
        this.nombre = nombre;
    }

    //Getter and Setter
    public int getIdColor() {
        return idColor;
    }

    public void setIdColor(int idColor) {
        this.idColor = idColor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Color_TO{" + "idColor=" + idColor + ", nombre=" + nombre + '}';
    }

}
