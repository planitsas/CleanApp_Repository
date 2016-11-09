package co.com.planit.lavapp.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Historico_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.service.ConsultarDescripcionPedidoSegunProducto;
import co.com.planit.lavapp.service.ConsultarHistoricoDescripcion;
import co.com.planit.lavapp.service.ConsultarPedidos;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Historico extends AppCompatActivity {

    UserLocalStore userLocalStore;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    TextView tvNombreProducto, tvEstado;

    List<String> IdEstado = new ArrayList<String>();
    List<String> Descripcion = new ArrayList<String>();

    SingleListViewAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historico);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        int idProducto = getIntent().getExtras().getInt("idProd");

        ConsultarProducto(idProducto);

        tvNombreProducto = (TextView) findViewById(R.id.tvNombreProducto);
        tvEstado = (TextView) findViewById(R.id.tvEstado);

        ConsultarEstados(idProducto);


    }

    public class SingleListViewAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        List<String> idEstado;
        List<String> descripcion;
        LayoutInflater inflater;

        public SingleListViewAdapter(Context context, List<String> idEstado, List<String> descripcion) {
            this.context = context;
            this.idEstado = idEstado;
            this.descripcion = descripcion;
        }

        public SingleListViewAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return idEstado.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public View getView(int position, View convertView, ViewGroup parent) {

            // Declare Variables
            TextView txtIdEstado;
            TextView txtDescripcion;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.single_list_row, parent, false);

            // Locate the TextViews in listview_item.xml
            txtIdEstado = (TextView) itemView.findViewById(R.id.single_post_tv_id);
            txtDescripcion = (TextView) itemView.findViewById(R.id.single_post_tv_nombre);

            // Capture position and set to the TextViews
            txtIdEstado.setText(IdEstado.get(position));
            txtDescripcion.setText(Descripcion.get(position));

            return itemView;
        }
    }

    private void ConsultarEstados(int idProducto) {

        final ListView lista1 = (ListView) findViewById(R.id.listView1);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarHistoricoDescripcion servicio = restAdapter.create(ConsultarHistoricoDescripcion.class);

        servicio.consultarHistoricoDescripcion(idProducto, new Callback<List<Historico_TO>>() {
            @Override
            public void success(List<Historico_TO> estados, Response response) {

                Log.i("consultarEstados: ",estados+"" );

                IdEstado.clear();

                Descripcion.clear();

                for (int i = 0; i < estados.size(); i++) {
                    IdEstado.add(estados.get(i).getEstado().getIdEstado() + "");
                    Descripcion.add("Estado: " + estados.get(i).getEstado().getNombre() + "(" + estados.get(i).getFechaString()+")");

                }

                adapter1 = new SingleListViewAdapter(getApplicationContext(), IdEstado, Descripcion);


                lista1.setAdapter(null);
                lista1.setAdapter(adapter1);

            }

            @Override
            public void failure(RetrofitError retrofitError) {

                Log.i("Error: ", retrofitError.toString());
            }
        });

    }

    private void ConsultarProducto(int idProducto) {

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarDescripcionPedidoSegunProducto servicio = restAdapter.create(ConsultarDescripcionPedidoSegunProducto.class);

        servicio.consultarDescripcionPedidoSegunProducto(idProducto, new Callback<DescripcionPedido_TO>() {
            @Override
            public void success(DescripcionPedido_TO producto, Response response) {

                Log.i("consultarProducto: ", producto + "");

                tvNombreProducto.setText("Nombre Producto: " + producto.getSubProducto().getNombre().toString());
                tvEstado.setText("Estado Actual: " + producto.getEstado().getNombre());

            }

            @Override
            public void failure(RetrofitError retrofitError) {

                Log.i("Error: ", retrofitError.toString());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (userLocalStore.getUserLoggedIn() == true) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        }else{
            getMenuInflater().inflate(R.menu.menu_main2, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.cerrar_Sesion) {
            userLocalStore.clearUserData();
            userLocalStore.setUserLoggedIn(false);
            startActivity(new Intent(this, MainActivity.class));
        }

        if (id == R.id.iniciar_sesion) {

            startActivity(new Intent(this, Login.class));
        }

        if (id == R.id.inicio) {

            startActivity(new Intent(this, Inicio.class));
        }

        if (id == R.id.consultar_pedidos) {

            startActivity(new Intent(this, ConsultarPedido.class));
        }

        if (id == R.id.editar_perfil) {

            startActivity(new Intent(this, EditarPerfil.class));
        }

        return super.onOptionsItemSelected(item);
    }

}
