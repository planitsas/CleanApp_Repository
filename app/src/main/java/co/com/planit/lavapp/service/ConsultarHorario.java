package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Horario_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 28/07/2016.
 */
public interface ConsultarHorario {
    @GET("/consultarHorario")
    void consultarHorario(@Query("idHorario") int idhorario, @Query("horario") String horario, Callback<Horario_TO> callback);
}
