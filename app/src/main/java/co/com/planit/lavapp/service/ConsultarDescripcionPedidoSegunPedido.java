package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
public interface ConsultarDescripcionPedidoSegunPedido {
    @GET("/consultarDescripcionPedidoSegunPedido/")
    void consultarDescripcionPedidoSegunPedido(@Query("idPedido") int idPedido, Callback<List<DescripcionPedido_TO>> callback);
}
