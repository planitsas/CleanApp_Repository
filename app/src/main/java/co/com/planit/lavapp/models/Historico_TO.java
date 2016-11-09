package co.com.planit.lavapp.models;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Jose on 29/08/2016.
 */

@SuppressWarnings("serial")
public class Historico_TO  implements Serializable {

    /**
     *
     * Columna idHistorico
     */
    private int idHistorico;

    /**
     *
     * Columna idDescripcionPedido
     */
    private DescripcionPedido_TO descripcionPedido;

    /**
     *
     * Columna estado
     */
    private Estado_TO estado;

    /**
     *
     * Columna fecha
     */
    private Date fecha;

    /**
     *
     * Columna fecha
     */
    private String fechaString;

    //Constructores


    public Historico_TO() {
    }

    public Historico_TO(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public Historico_TO(int idHistorico, DescripcionPedido_TO descripcionPedido, Estado_TO estado, Date fecha) {
        this.idHistorico = idHistorico;
        this.descripcionPedido = descripcionPedido;
        this.estado = estado;
        this.fecha = fecha;
    }

    public Historico_TO(int idHistorico, DescripcionPedido_TO descripcionPedido, Estado_TO estado, String fechaString) {
        this.idHistorico = idHistorico;
        this.descripcionPedido = descripcionPedido;
        this.estado = estado;
        this.fechaString = fechaString;
    }

    //Getters and Setters


    public int getIdHistorico() {
        return idHistorico;
    }

    public void setIdHistorico(int idHistorico) {
        this.idHistorico = idHistorico;
    }

    public DescripcionPedido_TO getDescripcionPedido() {
        return descripcionPedido;
    }

    public void setDescripcionPedido(DescripcionPedido_TO descripcionPedido) {
        this.descripcionPedido = descripcionPedido;
    }

    public Estado_TO getEstado() {
        return estado;
    }

    public void setEstado(Estado_TO estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getFechaString() {
        return fechaString;
    }

    public void setFechaString(String fechaString) {
        this.fechaString = fechaString;
    }

    @Override
    public String toString() {
        return "Historico_TO{" +
                "idHistorico=" + idHistorico +
                ", descripcionPedido=" + descripcionPedido +
                ", estado=" + estado +
                ", fecha=" + fecha +
                ", fechaString='" + fechaString + '\'' +
                '}';
    }
}
