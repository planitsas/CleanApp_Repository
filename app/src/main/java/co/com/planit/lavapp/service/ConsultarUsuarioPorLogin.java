package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Localidad_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */
public interface ConsultarUsuarioPorLogin {
    @GET("/consultarUsuarioPorLogin/")
    void consultarUsuarioPorLogin(@Query("email") String email, Callback<Usuario_TO> callback);
}