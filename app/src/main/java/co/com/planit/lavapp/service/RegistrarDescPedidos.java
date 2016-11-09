package co.com.planit.lavapp.service;

import java.util.Date;

import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
public interface RegistrarDescPedidos {
    @GET("/registrarDescripcionPedido/")
    void registrarDescPedido(@Query("idEstado")int idEstado,
                             @Query("idPedido") int idPedido,
                             @Query("idSubProd")int idSubProd, Callback<DescripcionPedido_TO> callback);
}
