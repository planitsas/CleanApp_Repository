package co.com.planit.lavapp.service;

import java.util.Date;

import co.com.planit.lavapp.models.Pedido_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
public interface RegistrarPedido {
    @GET("/registrarPedidoCompleto/")
    void registrarPedido(@Query("idUsuario") int idUsuario,
                         @Query("fechaInicio") String fechaInicio,
                         @Query("idHoraInicio") int idHoraInicio,
                         @Query("idHoraFinal") int idHoraFinal,
                         @Query("idEstado") int idEstado,
                         @Query("fechaEntrega") String fechaEntrega,
                         @Query("direccionEntrega") String direccionEntrega,
                         @Query("direccionRecogida") String direccionRecogida,
                         @Query("fechaRecogida") String fechaRecogida,
                         @Query("quienEntrega") String quienEntrega,
                         @Query("quienRecibe") String quienRecibe,
                         @Query("idBarrioRecogida") int idBarrioRecogida,
                         @Query("idBarrioEntrega") int idBarrioEntrega,
                         @Query("idFormaPago") int idFormaPago,
                         @Query("idEstadoPago") int idEstadoPago,Callback<Pedido_TO> callback);
}
