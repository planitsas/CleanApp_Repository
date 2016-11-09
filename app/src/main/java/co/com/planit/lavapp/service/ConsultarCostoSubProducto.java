package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Barrio_TO;
import co.com.planit.lavapp.models.Costo_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 11/07/2016.
 */
public interface ConsultarCostoSubProducto {
    @GET("/consultarCostoSubProducto/")
    void consultarCostoSubProducto(@Query("idSubProducto") int idSubProducto, Callback<Costo_TO> callback);
}
