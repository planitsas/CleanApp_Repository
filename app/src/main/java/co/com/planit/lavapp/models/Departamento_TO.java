package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */

@SuppressWarnings("serial")
public class Departamento_TO implements Serializable {

    /**
     *
     * Columna idDepartamento
     */
    private int idDepartamento;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    /**
     *
     * Columna idPais
     */
    private Pais_TO pais;

    //Constructores
    public Departamento_TO() {
    }

    //Constructor General
    public Departamento_TO(int idDepartamento, String nombre, Pais_TO pais) {
        this.idDepartamento = idDepartamento;
        this.nombre = nombre;
        this.pais = pais;
    }

    public Departamento_TO(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }



    //Getter and Setter
    public int getIdDepartamento() {
        return idDepartamento;
    }

    public void setIdDepartamento(int idDepartamento) {
        this.idDepartamento = idDepartamento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Pais_TO getPais() {
        return pais;
    }

    public void setPais(Pais_TO pais) {
        this.pais = pais;
    }

    @Override
    public String toString() {
        return "Departamento_TO{" + "idDepartamento=" + idDepartamento + ", nombre=" + nombre + ", pais=" + pais + '}';
    }

}

