package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.DescripcionPedido_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 26/07/2016.
 */
public interface ConsultarDescripcionPedidoSegunProducto {
    @GET("/consultarDescripcionPedidoSegunProducto/")
    void consultarDescripcionPedidoSegunProducto(@Query("idDescripcionPedido") int idDescripcionPedido, Callback<DescripcionPedido_TO> callback);
}
