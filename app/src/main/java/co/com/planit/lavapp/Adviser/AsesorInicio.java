package co.com.planit.lavapp.Adviser;

import android.content.Context;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.client.ConsultarPedido;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.service.ConsultarPedidoAsesorEntregar;
import co.com.planit.lavapp.service.ConsultarPedidoPorDiaAsesor;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AsesorInicio extends AppCompatActivity {

    UserLocalStore userLocalStore;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    ListViewAdapter adapter1;

    TextView tvBienvenido, tvFecha;

    List<String> IdPedido = new ArrayList<String>();
    List<String> Fecha = new ArrayList<String>();
    List<String> FechaInicio = new ArrayList<String>();
    List<String> HoraInicio = new ArrayList<String>();
    List<Integer> Imagenes = new ArrayList<Integer>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asesor_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        final ListView lista1 = (ListView) findViewById(R.id.listView1);
        tvBienvenido = (TextView) findViewById(R.id.tvBienvenido);
        tvFecha = (TextView) findViewById(R.id.tvFecha);

        Calendar C = Calendar.getInstance();
        int sAnio = C.get(Calendar.YEAR);
        int sMes = C.get(Calendar.MONTH);
        sMes = sMes + 1;
        int sDia = C.get(Calendar.DAY_OF_MONTH);
        String hoy = sAnio + "-" + sMes + "-" + sDia;

        tvBienvenido.setText("Bienvenido: "+userLocalStore.getLoggedInUser().getNombre().toString());
        tvFecha.setText("Fecha: "+hoy);

        ConsultarPedidos();

    }

    public class ListViewAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        List<String> idPedido;
        List<String> fecha;
        List<String> fechaInicio;
        List<String> horaInicio;
        List<Integer> imagenes;
        LayoutInflater inflater;

        public ListViewAdapter(Context context, List<String> idPedido ,  List<String> fecha, List<String> fechaInicio, List<String> horaInicio, List<Integer> imagenes) {
            this.context = context;
            this.idPedido = idPedido;
            this.fecha = fecha;
            this.horaInicio = horaInicio;
            this.fechaInicio = fechaInicio;
            this.imagenes = imagenes;
        }
        public ListViewAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return idPedido.size();
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
            TextView txtIdPedido;
            TextView txtFecha;
            TextView txtFechaInicio;
            TextView txtHoraInicio;
            ImageView imgImg;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.list_row, parent, false);

            // Locate the TextViews in listview_item.xml
            txtIdPedido = (TextView) itemView.findViewById(R.id.tv_titulo_single_post_circuito);
            txtFecha = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito);
            txtFechaInicio = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito2);
            txtHoraInicio = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito3);
            imgImg = (ImageView) itemView.findViewById(R.id.imagen_single_post_circuito);

            // Capture position and set to the TextViews
            txtIdPedido.setText(IdPedido.get(position));
            txtFecha.setText(Fecha.get(position));
            txtFechaInicio.setText(FechaInicio.get(position));
            txtHoraInicio.setText(HoraInicio.get(position));
            imgImg.setImageResource(Imagenes.get(position));
            //    txtSubtitle.setText(FechaInicio.get(position));

            return itemView;
        }
    }

    private void ConsultarPedidos() {

        final ListView lista1 = (ListView) findViewById(R.id.listView1);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarPedidoPorDiaAsesor servicio = restAdapter.create(ConsultarPedidoPorDiaAsesor.class);

        final int idUsuario = userLocalStore.getLoggedInUser().getIdUsuario();

        Log.i("Gustavo", "funciona " + idUsuario);
        servicio.consultarPedidosPorDiaAsesor(idUsuario, new Callback<List<Pedido_TO>>() {
            @Override
            public void success(final List<Pedido_TO> pedidos, Response response) {

                Log.i("pedidos: ", pedidos + "");

                IdPedido.clear();

                Fecha.clear();

                FechaInicio.clear();

                HoraInicio.clear();

                Imagenes.clear();


                for (int i = 0; i < pedidos.size(); i++) {
                    IdPedido.add(pedidos.get(i).getIdPedido() + "");
                    if(pedidos.get(i).getEstado().getIdEstado()==3) {
                        Fecha.add("Entrega: "+pedidos.get(i).getQuienEntrega());
                    }else{
                        Fecha.add("Recibe: "+pedidos.get(i).getQuienRecibe());
                    }
                    if(pedidos.get(i).getEstado().getIdEstado()==3) {
                        FechaInicio.add("Recogida: "+pedidos.get(i).getDireccionRecogida());
                    }else{
                        FechaInicio.add("Entrega: "+pedidos.get(i).getDireccionEntrega());
                    }
                    HoraInicio.add("Estatus: " + pedidos.get(i).getEstado().getNombre());
                    Imagenes.add(R.drawable.packages);
                }

                adapter1 = new ListViewAdapter(getApplicationContext(), IdPedido, Fecha, FechaInicio, HoraInicio, Imagenes);


                lista1.setAdapter(null);
                lista1.setAdapter(adapter1);


                lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView adapterView, View view, int posicion, long l) {

                        //    if (userLocalStore.getUserLoggedIn() == false) {


                        int idPed = Integer.parseInt(IdPedido.get(posicion));

                        if(pedidos.get(posicion).getEstado().getIdEstado()==3) {
                            Intent intent = new Intent(AsesorInicio.this, ConsultarDescripPedidosAsesor.class);
                            intent.putExtra("idPed", idPed);
                            startActivity(intent);
                        }else{
                            Intent intent = new Intent(AsesorInicio.this, ConsultarDescripPedidoAsesorEntrega.class);
                            intent.putExtra("idPed", idPed);
                            startActivity(intent);
                        }



                        //    }else {

                        //          int idReserva = Integer.parseInt(IdReserva.get(posicion));

                        //          Log.i("Gustavo idreserva: ", idReserva + "");

                        //        reservaLocalStore.storeIdReserva(idReserva);
//
                        //         startActivity(new Intent(getActivity(), ConsultarReservaC.class));
                        //     }
                    }
                });
//
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("Error", "mensaje " + retrofitError.getMessage());

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (userLocalStore.getUserLoggedIn() == true) {
            if(userLocalStore.getLoggedInUser().getRol().getIdRol()==3){
                getMenuInflater().inflate(R.menu.menu_asesor, menu);
            }else{
                if(userLocalStore.getLoggedInUser().getRol().getIdRol()==4){
                    getMenuInflater().inflate(R.menu.menu_main, menu);
                }
            }
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

        if (id == R.id.inicio) {

            startActivity(new Intent(this, AsesorInicio.class));
        }

        if (id == R.id.consultar_pedidos_asesor) {

            startActivity(new Intent(this, ConsultarPedidosAsesor.class));
        }

        if (id == R.id.consultar_pedidos_entrega_asesor) {

            startActivity(new Intent(this, ConsultarPedidosAsesorEntrega.class));
        }

        if (id == R.id.editar_perfil) {

            startActivity(new Intent(this, EditarPerfil.class));
        }

        return super.onOptionsItemSelected(item);
    }

}