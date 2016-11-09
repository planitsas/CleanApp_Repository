package co.com.planit.lavapp.service;

import co.com.planit.lavapp.models.Pedido_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 30/06/2016.
 */
public interface ConsultarUltimoPedido {
    @GET("/consultarUltimoPedido/")
    void consultarUltimoPedido(@Query("idUsuario") int idUsuario, Callback<Pedido_TO> callback);
    }