package co.com.planit.lavapp.service;

import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 30/06/2016.
 */
public interface EliminarDescPedido {
    @GET("/eliminardescPedido/")
    void eliminardescPedido(@Query("idPedido") int idPedido,@Query("idDescPedido") int idDescPedido,
                            Callback<DescripcionPedido_TO> callback);
}
