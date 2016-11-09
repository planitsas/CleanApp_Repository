package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Ciudad_TO;
import co.com.planit.lavapp.models.Historico_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */
public interface ConsultarHistoricoDescripcion {
    @GET("/consultarHistoricoDescripcion/")
    void consultarHistoricoDescripcion(@Query("idDescripcionPedido") int idDescripcionPedido, Callback<List<Historico_TO>> callback);
}
