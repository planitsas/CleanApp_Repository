package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Barrio_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 27/06/2016.
 */
public interface ConsultarCantidadDescripcionPedido {
    @GET("/consultarCantidadDescripcionPedido/")
    void consultarCantidadDescripcionPedido(@Query("idPedido") int idPedido, Callback<List<Barrio_TO>> callback);
}
