package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */

@SuppressWarnings("serial")
public class Localidad_TO  implements Serializable{

    /**
     *
     * Columna idLocalidad
     */
    private int idLocalidad;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    /**
     *
     * Columna idCiudad
     */
    private Ciudad_TO ciudad;

    //Constructores

    public Localidad_TO() {
    }

    public Localidad_TO(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    // Constructor General
    public Localidad_TO(int idLocalidad, String nombre, Ciudad_TO ciudad) {
        this.idLocalidad = idLocalidad;
        this.nombre = nombre;
        this.ciudad = ciudad;
    }

    //Getter and Setter

    public int getIdLocalidad() {
        return idLocalidad;
    }

    public void setIdLocalidad(int idLocalidad) {
        this.idLocalidad = idLocalidad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Ciudad_TO getCiudad() {
        return ciudad;
    }

    public void setCiudad(Ciudad_TO ciudad) {
        this.ciudad = ciudad;
    }

    @Override
    public String toString() {
        return "Localidad_TO{" + "idLocalidad=" + idLocalidad + ", nombre=" + nombre + ", ciudad=" + ciudad + '}';
    }

}
