package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.SubProducto_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 03/06/2016.
 */
public interface ConsultarSubProductos {
    @GET("/consultarSubProductosMasCosto/")
    void consultarSubProductos(@Query("idProducto") int idProducto, Callback<List<SubProducto_TO>> callback);
}