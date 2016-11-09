package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Barrio_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 22/07/2016.
 */
public interface ConsultarBarrio {
    @GET("/consultarBarrio/")
    void consultarBarrio(@Query("idBarrios") int idBarrios, @Query("nombre") String nombre, Callback<Barrio_TO> callback);
}