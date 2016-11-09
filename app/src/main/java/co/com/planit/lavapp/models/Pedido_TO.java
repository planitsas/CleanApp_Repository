package co.com.planit.lavapp.models;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
@SuppressWarnings("serial")
public class Pedido_TO implements Serializable {

    /**
     *
     * Columna idPedido
     */
    private int idPedido;

    /**
     *
     * Columna idUsuario
     */
    private Usuario_TO usuario;

    /**
     *
     * Columna fechaInicio
     */
    private Date fechaInicio;

    /**
     *
     * Columna fechaInicio
     */
    private String fechaInicioString;

    /**
     *
     * Columna fechaEntrega
     */
    private Date fechaEntrega;

    /**
     *
     * Columna fechaEntrega
     */
    private String fechaEntregaString;

    /**
     *
     * Columna fechaRecogida
     */
    private Date fechaRecogida;

    /**
     *
     * Columna fechaRecogida
     */
    private String fechaRecogidaString;

    /**
     *
     * Columna quienEntrega
     */
    private String quienEntrega;

    /**
     *
     * Columna quienRecibe
     */
    private String quienRecibe;

    /**
     *
     * Columna direccionRecogida
     */
    private String direccionRecogida;

    /**
     *
     * Columna direccionEntrega
     */
    private String direccionEntrega;

    /**
     *
     * Columna horaInicio_idHorario
     */
    private Horario_TO horaInicio;

    /**
     *
     * Columna horaFinal_idHorario
     */
    private Horario_TO horaFinal;

    /**
     *
     * Columna horaFinal_idHorario
     */
    private Estado_TO estado;

    /**
     *
     * Columna idProveedor
     */
    private Proveedor_TO proveedor;

    /**
     *
     * Columna idBarrios
     */
    private Barrio_TO idBarrioRecogida;

    /**
     *
     * Columna idBarrios
     */
    private Barrio_TO idBarrioEntrega;

    /**
     *
     * Columna Lista DescripcionPedido
     */
    private List<DescripcionPedido_TO> productos;

    //Json
    /**
     * Serialización del objeto
     * que será devuelto como un String en fomrato jSon
     */
    public String serializePedido(){
        Gson pedidoJson = new Gson();
        return pedidoJson.toJson(this);
    }

    /**
     * Creación del objeto desde el jSon
     */
    public static Pedido_TO createPedidoFromJson(String pedidoJson){
        Gson objectJson = new Gson();
        return objectJson.fromJson(pedidoJson, Pedido_TO.class);
    }

    //Constructores
    public Pedido_TO() {
    }

    //Constructor General
    public Pedido_TO(int idPedido) {
        this.idPedido = idPedido;
    }

    public Pedido_TO(int idPedido, Usuario_TO usuario, Date fechaInicio, String fechaInicioString, Date fechaEntrega, String fechaEntregaString, Date fechaRecogida, String fechaRecogidaString, String quienEntrega, String quienRecibe, String direccionRecogida, String direccionEntrega, Horario_TO horaInicio, Horario_TO horaFinal, Estado_TO estado, Proveedor_TO proveedor, List<DescripcionPedido_TO> productos) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaInicioString = fechaInicioString;
        this.fechaEntrega = fechaEntrega;
        this.fechaEntregaString = fechaEntregaString;
        this.fechaRecogida = fechaRecogida;
        this.fechaRecogidaString = fechaRecogidaString;
        this.quienEntrega = quienEntrega;
        this.quienRecibe = quienRecibe;
        this.direccionRecogida = direccionRecogida;
        this.direccionEntrega = direccionEntrega;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.proveedor = proveedor;
        this.productos = productos;
    }

    public Pedido_TO(Usuario_TO usuario, Date fechaInicio, Horario_TO horaInicio, Horario_TO horaFinal, Estado_TO estado, Proveedor_TO proveedor) {
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.proveedor = proveedor;
    }

    public Pedido_TO(int idPedido, Usuario_TO usuario, Date fechaInicio, Horario_TO horaInicio, Horario_TO horaFinal, Estado_TO estado, Proveedor_TO proveedor) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.proveedor = proveedor;
    }

    public Pedido_TO(int idPedido, Usuario_TO usuario, Date fechaInicio, String fechaInicioString, Horario_TO horaInicio, Horario_TO horaFinal, Estado_TO estado, Proveedor_TO proveedor, List<DescripcionPedido_TO> productos) {
        this.idPedido = idPedido;
        this.usuario = usuario;
        this.fechaInicio = fechaInicio;
        this.fechaInicioString = fechaInicioString;
        this.horaInicio = horaInicio;
        this.horaFinal = horaFinal;
        this.estado = estado;
        this.proveedor = proveedor;
        this.productos = productos;
    }

    //Getter and Setter


    public int getIdPedido() {
        return idPedido;
    }

    public void setIdPedido(int idPedido) {
        this.idPedido = idPedido;
    }

    public Usuario_TO getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario_TO usuario) {
        this.usuario = usuario;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaInicioString() {
        return fechaInicioString;
    }

    public void setFechaInicioString(String fechaInicioString) {
        this.fechaInicioString = fechaInicioString;
    }

    public Date getFechaEntrega() {
        return fechaEntrega;
    }

    public void setFechaEntrega(Date fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public String getFechaEntregaString() {
        return fechaEntregaString;
    }

    public void setFechaEntregaString(String fechaEntregaString) {
        this.fechaEntregaString = fechaEntregaString;
    }

    public Date getFechaRecogida() {
        return fechaRecogida;
    }

    public void setFechaRecogida(Date fechaRecogida) {
        this.fechaRecogida = fechaRecogida;
    }

    public String getFechaRecogidaString() {
        return fechaRecogidaString;
    }

    public void setFechaRecogidaString(String fechaRecogidaString) {
        this.fechaRecogidaString = fechaRecogidaString;
    }

    public String getQuienEntrega() {
        return quienEntrega;
    }

    public void setQuienEntrega(String quienEntrega) {
        this.quienEntrega = quienEntrega;
    }

    public String getQuienRecibe() {
        return quienRecibe;
    }

    public void setQuienRecibe(String quienRecibe) {
        this.quienRecibe = quienRecibe;
    }

    public String getDireccionRecogida() {
        return direccionRecogida;
    }

    public void setDireccionRecogida(String direccionRecogida) {
        this.direccionRecogida = direccionRecogida;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public Horario_TO getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(Horario_TO horaInicio) {
        this.horaInicio = horaInicio;
    }

    public Horario_TO getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(Horario_TO horaFinal) {
        this.horaFinal = horaFinal;
    }

    public Estado_TO getEstado() {
        return estado;
    }

    public void setEstado(Estado_TO estado) {
        this.estado = estado;
    }

    public Proveedor_TO getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor_TO proveedor) {
        this.proveedor = proveedor;
    }

    public List<DescripcionPedido_TO> getProductos() {
        return productos;
    }

    public void setProductos(List<DescripcionPedido_TO> productos) {
        this.productos = productos;
    }

    public Barrio_TO getIdBarrioRecogida() {
        return idBarrioRecogida;
    }

    public void setIdBarrioRecogida(Barrio_TO idBarrioRecogida) {
        this.idBarrioRecogida = idBarrioRecogida;
    }

    public Barrio_TO getIdBarrioEntrega() {
        return idBarrioEntrega;
    }

    public void setIdBarrioEntrega(Barrio_TO idBarrioEntrega) {
        this.idBarrioEntrega = idBarrioEntrega;
    }

    @Override
    public String toString() {
        return "Pedido_TO{" +
                "idPedido=" + idPedido +
                ", usuario=" + usuario +
                ", fechaInicio=" + fechaInicio +
                ", fechaInicioString='" + fechaInicioString + '\'' +
                ", fechaEntrega=" + fechaEntrega +
                ", fechaEntregaString='" + fechaEntregaString + '\'' +
                ", fechaRecogida=" + fechaRecogida +
                ", fechaRecogidaString='" + fechaRecogidaString + '\'' +
                ", quienEntrega='" + quienEntrega + '\'' +
                ", quienRecibe='" + quienRecibe + '\'' +
                ", direccionRecogida='" + direccionRecogida + '\'' +
                ", direccionEntrega='" + direccionEntrega + '\'' +
                ", horaInicio=" + horaInicio +
                ", horaFinal=" + horaFinal +
                ", estado=" + estado +
                ", proveedor=" + proveedor +
                ", idBarrioRecogida=" + idBarrioRecogida +
                ", idBarrioEntrega=" + idBarrioEntrega +
                ", productos=" + productos +
                '}';
    }
}
