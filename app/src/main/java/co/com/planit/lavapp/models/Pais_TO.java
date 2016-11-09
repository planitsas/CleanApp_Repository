package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */
@SuppressWarnings("serial")
public class Pais_TO implements Serializable {

    /**
     *
     * Columna idPais
     */
    private int idPais;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    @Override
    public String toString() {
        return "Pais_TO{" + "idPais=" + idPais + ", nombre=" + nombre + '}';
    }

}
