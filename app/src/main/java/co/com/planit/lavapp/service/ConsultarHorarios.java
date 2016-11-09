package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Ciudad_TO;
import co.com.planit.lavapp.models.Horario_TO;
import retrofit.Callback;
import retrofit.http.GET;

/**
 * Created by VaioDevelopment on 13/07/2016.
 */
public interface ConsultarHorarios {
    @GET("/consultarHorarios")
    void consultarHorarios( Callback<List<Horario_TO>> callback);
}
