package co.com.planit.lavapp.service;

import java.util.List;

import co.com.planit.lavapp.models.Localidad_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by VaioDevelopment on 20/06/2016.
 */
public interface RegistrarUsuarios {
    @GET("/registrarUsuarios/")
    void registrarUsuarios(@Query("nombre") String nombre,
                           @Query("telefono") String telefono, @Query("idbarrios") int idbarrios,
                           @Query("idrol") int idrol, @Query("idestado") int idestado, @Query("usuario") String login,
                           @Query("contrasena") String contrasena, @Query("apellido") String apellido, @Query("genero") String genero,
                           @Query("movil") String movil, @Query("direccion") String direccion, @Query("idCiudad") int idCiudad, @Query("identificacion") String identificacion,
                           @Query("rutaImagen") String rutaImagen,   Callback<Usuario_TO> callback);


}
