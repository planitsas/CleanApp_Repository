package co.com.planit.lavapp.models;


import java.io.Serializable;

@SuppressWarnings("serial")
public class Producto_TO implements Serializable{

    /**
     *
     * Columna idProducto
     */
    private int idProducto;

    /**
     *
     * Columna nombre
     */
    private String nombre;

    /**
     *
     * Columna descripcion
     */
    private String descripcion;

    /**
     *
     * Columna idSubServicio
     */
    private SubServicio_TO subServicio;

    //Constructores
    public Producto_TO() {
    }

    public Producto_TO(int idProducto) {
        this.idProducto = idProducto;
    }


    //Constructor General
    public Producto_TO(int idProducto, String nombre, String descripcion, SubServicio_TO subServicio) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.subServicio = subServicio;
    }

    //Getter and Setter
    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public SubServicio_TO getSubServicio() {
        return subServicio;
    }

    public void setSubServicio(SubServicio_TO subServicio) {
        this.subServicio = subServicio;
    }

    @Override
    public String toString() {
        return "Producto_TO{" + "idProducto=" + idProducto + ", nombre=" + nombre + ", descripcion=" + descripcion + ", subServicio=" + subServicio + '}';
    }

}
