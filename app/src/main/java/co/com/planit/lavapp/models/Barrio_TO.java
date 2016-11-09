package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */
@SuppressWarnings("serial")
    public class Barrio_TO implements Serializable {

    /**
     *
     * Columna idBarrios
     */
    private int idBarrios;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    /**
     *
     * Columna idLocalidad
     */
    private Localidad_TO localidad;

    /**
     *
     * Columna idZona
     */
    private Zona_TO Zona;

    /**
     *
     * Columna idEstrato
     */
    private Estrato_TO estrato;


    // Constructores

    public Barrio_TO() {
    }

    // Constructor General
    public Barrio_TO(int idBarrios, String nombre, Localidad_TO localidad, Zona_TO Zona, Estrato_TO estrato) {
        this.idBarrios = idBarrios;
        this.nombre = nombre;
        this.localidad = localidad;
        this.Zona = Zona;
        this.estrato = estrato;
    }

    public Barrio_TO(int idBarrios) {
        this.idBarrios = idBarrios;
    }


    // Getter and Setter

    public int getIdBarrios() {
        return idBarrios;
    }

    public void setIdBarrios(int idBarrios) {
        this.idBarrios = idBarrios;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Localidad_TO getLocalidad() {
        return localidad;
    }

    public void setLocalidad(Localidad_TO localidad) {
        this.localidad = localidad;
    }

    public Zona_TO getZona() {
        return Zona;
    }

    public void setZona(Zona_TO Zona) {
        this.Zona = Zona;
    }

    public Estrato_TO getEstrato() {
        return estrato;
    }

    public void setEstrato(Estrato_TO estrato) {
        this.estrato = estrato;
    }

    @Override
    public String toString() {
        return "Barrio_TO{" + "idBarrios=" + idBarrios + ", nombre=" + nombre + ", localidad=" + localidad + ", Zona=" + Zona + ", estrato=" + estrato + '}';
    }

}
