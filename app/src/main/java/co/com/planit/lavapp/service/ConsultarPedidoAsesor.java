package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Pedido_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
public interface ConsultarPedidoAsesor {
    @GET("/consultarPedidosAsesor/")
    void consultarPedidosAsesor(@Query("idUsuario") int idUsuario, Callback<List<Pedido_TO>> callback);
}
