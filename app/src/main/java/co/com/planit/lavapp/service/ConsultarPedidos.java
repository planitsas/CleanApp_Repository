package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Pedido_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 26/07/2016.
 */
public interface ConsultarPedidos {
    @GET("/consultarPedido/")
    void consultarPedido(@Query("idPedido") int idPedido, Callback<Pedido_TO> callback);
}
