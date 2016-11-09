package co.com.planit.lavapp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import co.com.planit.lavapp.models.Barrio_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.models.Rol_TO;
import co.com.planit.lavapp.models.Usuario_TO;

/**
 * Created by ContabilidadPC on 24/02/2016.
 */
public class UserLocalStore {
    public static final String SP_NAME = "userDetails";
    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){

        userLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeUserData(Usuario_TO user){
        Log.i("Gustavo Store: ", user.toString());
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("nombre",user.getNombre());
        spEditor.putString("apellido",user.getApellido());
        spEditor.putString("identificacion",user.getIdentificacion());
        spEditor.putString("telefono",user.getTelefono());
        spEditor.putString("movil", user.getMovil());
        spEditor.putInt("idBarrio", user.getBarrio().getIdBarrios());
        spEditor.putString("direccion", user.getDireccion());
        spEditor.putString("genero", user.getGenero());
        spEditor.putString("email",user.getEmail());
        spEditor.putString("password",user.getContrasena());
        spEditor.putInt("idUsuario", user.getIdUsuario());
        spEditor.putInt("idRol", user.getRol().getIdRol());
        spEditor.putInt("idEstado", user.getEstado().getIdEstado());

        spEditor.commit();
    }

    public Usuario_TO getLoggedInUser(){

        String nombre = userLocalDatabase.getString("nombre", "");
        String apellido = userLocalDatabase.getString("apellido","");
        String identificacion = userLocalDatabase.getString("identificacion","");
        String telefono = userLocalDatabase.getString("telefono","");
        String movil = userLocalDatabase.getString("movil","");
        int idBarrio = userLocalDatabase.getInt("idBarrio", 0);
        String direccion = userLocalDatabase.getString("direccion", "");
        String genero = userLocalDatabase.getString("genero", "");
        String email = userLocalDatabase.getString("email","");
        String password = userLocalDatabase.getString("password","");
        int idUsuario = userLocalDatabase.getInt("idUsuario", 0);
        int rol = userLocalDatabase.getInt("idRol", 0);
        int idEstado = userLocalDatabase.getInt("idEstado", 0);



        Usuario_TO storedUser =
                new Usuario_TO(idUsuario, nombre, telefono, new Barrio_TO(idBarrio), new Rol_TO(rol), new Estado_TO(idEstado),
                        email, password, apellido, movil, genero, direccion, identificacion);

        return storedUser;

    }

    public void setUserLoggedIn(boolean loggedIn){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedIn);
        spEditor.commit();
    }

    public boolean getUserLoggedIn(){
        if(userLocalDatabase.getBoolean("loggedIn",false) == true){
            return true;
        }else{
            return false;
        }
    }

    public  void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
