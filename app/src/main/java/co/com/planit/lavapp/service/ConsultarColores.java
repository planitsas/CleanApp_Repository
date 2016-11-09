package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Color_TO;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by VaioDevelopment on 28/07/2016.
 */
public interface ConsultarColores {
    @GET("/consultarColores")
    void consultarColores( Callback<List<Color_TO>> callback);
}
