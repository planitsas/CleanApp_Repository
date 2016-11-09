package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 11/07/2016.
 */

@SuppressWarnings("serial")
public class Costo_TO implements Serializable{
    /**
     *
     * Columna idCosto
     */
    private int idCosto;

    /**
     *
     * Columna valor
     */
    private int valor;

    /**
     *
     * Columna idCosto
     */
    private SubProducto_TO subProducto;

    /**
     *
     * Columna idCosto
     */
    private Zona_TO zona;

    //Constructores
    public Costo_TO() {
    }

    public Costo_TO(int valor) {
        this.valor = valor;
    }

    public Costo_TO(int valor, SubProducto_TO subProducto, Zona_TO zona) {
        this.valor = valor;
        this.subProducto = subProducto;
        this.zona = zona;
    }

    public Costo_TO(int idCosto, int valor, SubProducto_TO subProducto, Zona_TO zona) {
        this.idCosto = idCosto;
        this.valor = valor;
        this.subProducto = subProducto;
        this.zona = zona;
    }



    //Getter and Setter
    public int getIdCosto() {
        return idCosto;
    }

    public void setIdCosto(int idCosto) {
        this.idCosto = idCosto;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public SubProducto_TO getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(SubProducto_TO subProducto) {
        this.subProducto = subProducto;
    }

    public Zona_TO getZona() {
        return zona;
    }

    public void setZona(Zona_TO zona) {
        this.zona = zona;
    }


    @Override
    public String toString() {
        return "Costo_TO{" +
                "idCosto=" + idCosto +
                ", valor=" + valor +
                ", subProducto=" + subProducto +
                ", zona=" + zona +
                '}';
    }
}

