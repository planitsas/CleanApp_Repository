package co.com.planit.lavapp.models;

import java.io.Serializable;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
@SuppressWarnings("serial")
public class DescripcionPedido_TO implements Serializable, Comparable<DescripcionPedido_TO> {

    /**
     *
     * Columna idDescripcionPedido
     */
    private int idDescripcionPedido;

    /**
     *
     * Columna idEstado
     */
    private Estado_TO estado;

    /**
     *
     * Columna idProducto
     */
    private SubProducto_TO subProducto;

    /**
     *
     * Columna descripcion
     */
    private String descripcion;

    /**
     *
     * Columna ObservacionAsesor
     */
    private String observacionAsesor;

    /**
     *
     * Columna ObservacionAdministrador
     */
    private String observacionAdministrador;

    /**
     *
     * Columna foto1
     */
    private String foto1;

    /**
     *
     * Columna foto2
     */
    private String foto2;

    /**
     *
     * Columna foto3
     */
    private String foto3;

    /**
     *
     * Columna idColor
     */
    private Color_TO color;

    /**
     *
     * Columna idPedido
     */
    private Pedido_TO pedido;

    /**
     *
     * Columna codigo
     */
    private String codigo;

    /**
     *
     * Columna nombrefoto1
     */
    private String nombrefoto1;

    /**
     *
     * Columna nombrefoto2
     */
    private String nombrefoto2;

    /**
     *
     * Columna nombrefoto3
     */
    private String nombrefoto3;

    //Constructores
    public DescripcionPedido_TO() {
    }

    public DescripcionPedido_TO(int idDescripcionPedido) {
        this.idDescripcionPedido = idDescripcionPedido;
    }

    public DescripcionPedido_TO(int idDescripcionPedido, Estado_TO estado, String observacionAsesor, String foto1, String foto2, String foto3, Color_TO color, String codigo, String nombrefoto1, String nombrefoto2, String nombrefoto3) {
        this.idDescripcionPedido = idDescripcionPedido;
        this.estado = estado;
        this.observacionAsesor = observacionAsesor;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.foto3 = foto3;
        this.color = color;
        this.codigo = codigo;
        this.nombrefoto1 = nombrefoto1;
        this.nombrefoto2 = nombrefoto2;
        this.nombrefoto3 = nombrefoto3;
    }

    public DescripcionPedido_TO(SubProducto_TO subProducto) {
        this.subProducto = subProducto;
    }

    public DescripcionPedido_TO(Estado_TO estado, SubProducto_TO subProducto, String descripcion, Color_TO color, Pedido_TO pedido) {
        this.estado = estado;
        this.subProducto = subProducto;
        this.descripcion = descripcion;
        this.color = color;
        this.pedido = pedido;
    }

    public DescripcionPedido_TO(Estado_TO estado, String descripcion, Color_TO color, SubProducto_TO subProducto) {
        this.estado = estado;
        this.descripcion = descripcion;
        this.color = color;
        this.subProducto = subProducto;
    }

    //Constructor General
    public DescripcionPedido_TO(int idDescripcionPedido, Estado_TO estado, SubProducto_TO subProducto, String descripcion, String observacionAsesor, String observacionAdministrador, String foto1, String foto2, String foto3, Color_TO color, Pedido_TO pedido) {
        this.idDescripcionPedido = idDescripcionPedido;
        this.estado = estado;
        this.subProducto = subProducto;
        this.descripcion = descripcion;
        this.observacionAsesor = observacionAsesor;
        this.observacionAdministrador = observacionAdministrador;
        this.foto1 = foto1;
        this.foto2 = foto2;
        this.foto3 = foto3;
        this.color = color;
        this.pedido = pedido;
    }

    //Getter and Setter
    public int getIdDescripcionPedido() {
        return idDescripcionPedido;
    }

    public void setIdDescripcionPedido(int idDescripcionPedido) {
        this.idDescripcionPedido = idDescripcionPedido;
    }

    public Estado_TO getEstado() {
        return estado;
    }

    public void setEstado(Estado_TO estado) {
        this.estado = estado;
    }

    public SubProducto_TO getSubProducto() {
        return subProducto;
    }

    public void setSubProducto(SubProducto_TO subProducto) {
        this.subProducto = subProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getObservacionAsesor() {
        return observacionAsesor;
    }

    public void setObservacionAsesor(String observacionAsesor) {
        this.observacionAsesor = observacionAsesor;
    }

    public String getObservacionAdministrador() {
        return observacionAdministrador;
    }

    public void setObservacionAdministrador(String observacionAdministrador) {
        this.observacionAdministrador = observacionAdministrador;
    }

    public String getFoto1() {
        return foto1;
    }

    public void setFoto1(String foto1) {
        this.foto1 = foto1;
    }

    public String getFoto2() {
        return foto2;
    }

    public void setFoto2(String foto2) {
        this.foto2 = foto2;
    }

    public String getFoto3() {
        return foto3;
    }

    public void setFoto3(String foto3) {
        this.foto3 = foto3;
    }

    public Color_TO getColor() {
        return color;
    }

    public void setColor(Color_TO color) {
        this.color = color;
    }

    public Pedido_TO getPedido() {
        return pedido;
    }

    public void setPedido(Pedido_TO pedido) {
        this.pedido = pedido;
    }

    public String getNombrefoto1() {
        return nombrefoto1;
    }

    public void setNombrefoto1(String nombrefoto1) {
        this.nombrefoto1 = nombrefoto1;
    }

    public String getNombrefoto2() {
        return nombrefoto2;
    }

    public void setNombrefoto2(String nombrefoto2) {
        this.nombrefoto2 = nombrefoto2;
    }

    public String getNombrefoto3() {
        return nombrefoto3;
    }

    public void setNombrefoto3(String nombrefoto3) {
        this.nombrefoto3 = nombrefoto3;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    @Override
    public String toString() {
        return "DescripcionPedido_TO{" +
                "idDescripcionPedido=" + idDescripcionPedido +
                ", estado=" + estado +
                ", subProducto=" + subProducto +
                ", descripcion='" + descripcion + '\'' +
                ", observacionAsesor='" + observacionAsesor + '\'' +
                ", observacionAdministrador='" + observacionAdministrador + '\'' +
                ", foto1='" + foto1 + '\'' +
                ", foto2='" + foto2 + '\'' +
                ", foto3='" + foto3 + '\'' +
                ", color=" + color +
                ", pedido=" + pedido +
                ", codigo='" + codigo + '\'' +
                '}';
    }

    @Override
    public int compareTo(DescripcionPedido_TO o) {
        if (subProducto.getIdSubProducto() < o.subProducto.getIdSubProducto()) {
            return -1;
        }
        if (subProducto.getIdSubProducto() > o.subProducto.getIdSubProducto()) {
            return 1;
        }
        return 0;
    }

}
