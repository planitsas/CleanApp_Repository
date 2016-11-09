package co.com.planit.lavapp.service;

import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 22/07/2016.
 */
public interface EditarInformacionUsuario {
    @GET("/editarUsuario/")
    void editarUsuario(@Query("idUsuario") int idUsuario,
                       @Query("nombre") String nombre,
                       @Query("apellido") String apellido,
                       @Query("genero") String genero,
                       @Query("telefono") String telefono,
                       @Query("idbarrios") int idbarrios,
                       @Query("movil") String movil,
                       @Query("direccion") String direccion,
                       @Query("idciudad") int idciudad,
                       @Query("identificacion") String identificacion,
                       @Query("rutaImagen") String rutaImagen,
                            Callback<Usuario_TO> callback);
}