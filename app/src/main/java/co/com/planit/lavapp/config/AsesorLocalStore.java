package co.com.planit.lavapp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import co.com.planit.lavapp.models.Barrio_TO;
import co.com.planit.lavapp.models.Color_TO;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.models.Rol_TO;
import co.com.planit.lavapp.models.Usuario_TO;

/**
 * Created by ContabilidadPC on 24/02/2016.
 */
public class AsesorLocalStore {
    public static final String SP_NAME = "asesorDetails";
    SharedPreferences asesorLocalDatabase;

    public AsesorLocalStore(Context context){

        asesorLocalDatabase = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storeAsesorData(DescripcionPedido_TO des){
        Log.i("Gustavo Store: ", des.toString());
        SharedPreferences.Editor spEditor = asesorLocalDatabase.edit();
        spEditor.putInt("idDescripcion", des.getIdDescripcionPedido());
        spEditor.putInt("idEstado", des.getEstado().getIdEstado());
        spEditor.putString("observacionAsesor",des.getObservacionAsesor());
        spEditor.putInt("idColor", des.getColor().getIdColor());
        spEditor.putString("foto1", des.getFoto1());
        spEditor.putString("foto2", des.getFoto2());
        spEditor.putString("foto3", des.getFoto3());
        spEditor.putString("codigo", des.getCodigo());
        spEditor.putString("nombrefoto1",des.getNombrefoto1());
        spEditor.putString("nombrefoto2",des.getNombrefoto2());
        spEditor.putString("nombrefoto3", des.getNombrefoto3());

        spEditor.commit();
    }

    public void setPedidoData(int idPedido){
        SharedPreferences.Editor spEditor = asesorLocalDatabase.edit();
        spEditor.putInt("idPedido", idPedido);

        spEditor.commit();
    }

    public int getIdPedido(){

        int idPedido = asesorLocalDatabase.getInt("idPedido", 0);

        return idPedido;
    }

    public void setAsesorFoto1(String foto1, String nombreFoto){
        SharedPreferences.Editor spEditor = asesorLocalDatabase.edit();
        spEditor.putString("foto1",foto1);
        spEditor.putString("nombrefoto1",nombreFoto);

        spEditor.commit();
    }

    public void setAsesorFoto2(String foto2, String nombreFoto){
        SharedPreferences.Editor spEditor = asesorLocalDatabase.edit();
        spEditor.putString("foto2",foto2);
        spEditor.putString("nombrefoto2",nombreFoto);

        spEditor.commit();
    }

    public void setAsesorFoto3(String foto3, String nombreFoto){
        SharedPreferences.Editor spEditor = asesorLocalDatabase.edit();
        spEditor.putString("foto3",foto3);
        spEditor.putString("nombrefoto3",nombreFoto);

        spEditor.commit();
    }

    public DescripcionPedido_TO getLoggedInAsesor(){

        int idDescripcion = asesorLocalDatabase.getInt("idDescripcion", 0);
        int idEstado = asesorLocalDatabase.getInt("idEstado", 0);
        String observacionAsesor = asesorLocalDatabase.getString("observacionAsesor", "");
        int idColor = asesorLocalDatabase.getInt("idColor", 0);
        String foto1 = asesorLocalDatabase.getString("foto1", "");
        String foto2 = asesorLocalDatabase.getString("foto2", "");
        String foto3 = asesorLocalDatabase.getString("foto3", "");
        String codigo = asesorLocalDatabase.getString("codigo", "");
        String nombrefoto1 = asesorLocalDatabase.getString("nombrefoto1","");
        String nombrefoto2 = asesorLocalDatabase.getString("nombrefoto2", "");
        String nombrefoto3 = asesorLocalDatabase.getString("nombrefoto3", "");

        DescripcionPedido_TO storedUser = new DescripcionPedido_TO(idDescripcion,new Estado_TO(idEstado), observacionAsesor, foto1, foto2, foto3, new Color_TO(idColor),
                codigo, nombrefoto1, nombrefoto2, nombrefoto3);

        return storedUser;
    }

    public  void clearUserData(){
        SharedPreferences.Editor spEditor = asesorLocalDatabase.edit();
        spEditor.clear();
        spEditor.commit();
    }

}
