package co.com.planit.lavapp.config;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import co.com.planit.lavapp.models.Usuario_TO;

/**
 * Created by VaioDevelopment on 18/07/2016.
 */
public class PedidoLocalStore {
    public static String SP_NAME = "pedidoDetails";
    SharedPreferences pedidoLocalStore;


    public PedidoLocalStore(Context context){

        pedidoLocalStore = context.getSharedPreferences(SP_NAME, 0);
    }

    public void storePedidoData(String jsonPedido){
        Log.i("Gustavo Store: ", jsonPedido.toString());
        SharedPreferences.Editor spEditor = pedidoLocalStore.edit();
        spEditor.putString("jsonPedido", jsonPedido);
        spEditor.commit();
    }

    public String getPedidoData(){

        String jsonPedido = pedidoLocalStore.getString("jsonPedido", "");

        return jsonPedido;

    }

    public void storeAcumData(int valor){
        SharedPreferences.Editor spEditor = pedidoLocalStore.edit();
        spEditor.putInt("acumulado",valor);

        spEditor.commit();
    }

    public int getAcumData(){

        int valor = pedidoLocalStore.getInt("valor", 0);

        return valor;

    }
}
