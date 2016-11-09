package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 *
 * @author Desarrollo_Planit
 */

@SuppressWarnings("serial")
public class SubProducto_TO implements Serializable {

    private int idSubProducto;
    private String nombre;
    private String descripcion;
    private Producto_TO producto;
    private String rutaImagen;
    private Costo_TO costo;

    public SubProducto_TO() {
    }

    public SubProducto_TO(int idSubProducto) {
        this.idSubProducto = idSubProducto;
    }

    public SubProducto_TO(int idSubProducto, String nombre, String descripcion, Producto_TO producto) {
        this.idSubProducto = idSubProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.producto = producto;
    }

    public SubProducto_TO(int idSubProducto, String nombre, String descripcion, Producto_TO producto, String rutaImagen, Costo_TO costo) {
        this.idSubProducto = idSubProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.producto = producto;
        this.rutaImagen = rutaImagen;
        this.costo = costo;
    }

    public SubProducto_TO(int idSubProducto, String nombre) {
        this.idSubProducto = idSubProducto;
        this.nombre = nombre;
    }

    public SubProducto_TO(int idSubProducto, String nombre, Costo_TO costo) {
        this.idSubProducto = idSubProducto;
        this.nombre = nombre;
        this.costo = costo;
    }

    public int getIdSubProducto() {
        return idSubProducto;
    }

    public void setIdSubProducto(int idSubProducto) {
        this.idSubProducto = idSubProducto;
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

    public Producto_TO getProducto() {
        return producto;
    }

    public void setProducto(Producto_TO producto) {
        this.producto = producto;
    }

    public Costo_TO getCosto() {
        return costo;
    }

    public void setCosto(Costo_TO costo) {
        this.costo = costo;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    @Override
    public String toString() {
        return "SubProducto_TO{" +
                "idSubProducto=" + idSubProducto +
                ", nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", producto=" + producto +
                ", rutaImagen='" + rutaImagen + '\'' +
                ", costo=" + costo +
                '}';
    }
}
