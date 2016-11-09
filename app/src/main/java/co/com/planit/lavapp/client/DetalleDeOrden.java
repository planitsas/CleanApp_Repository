package co.com.planit.lavapp.client;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.LoginSinRegistro;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.PedidoLocalStore;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.models.SubProducto_TO;
import co.com.planit.lavapp.service.ConsultarSubProductos;
import co.com.planit.lavapp.service.ConsultarUltimoPedido;
import co.com.planit.lavapp.service.RegistrarDescPedidos;
import co.com.planit.lavapp.service.RegistrarPedido;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class DetalleDeOrden extends AppCompatActivity implements View.OnClickListener   {

    Button bOrden;
    List<String> Producto = new ArrayList<String>();
    ListViewAdapter adapter1;

    UserLocalStore userLocalStore;
    PedidoLocalStore pedidoLocalStore;

    Pedido_TO pedido = new Pedido_TO();
    Pedido_TO pedidoDescripcion = new Pedido_TO();
    List<DescripcionPedido_TO> productos = new ArrayList<DescripcionPedido_TO>();
    List<DescripcionPedido_TO> productosCat = new ArrayList<DescripcionPedido_TO>();
    List<List<DescripcionPedido_TO>> productosCatOrd = new ArrayList<List<DescripcionPedido_TO>>();

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    EditText etFechaEntrega,etDireccionEntrega,etNombreEntrega,etFechaRecogida,etDireccionRecogida,etNombreRecibe, etHora, etHora2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_de_orden);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pedido = (Pedido_TO)getIntent().getExtras().getSerializable("pedido");
        productos = (List<DescripcionPedido_TO>)getIntent().getExtras().getSerializable("productos");

        final ListView lista1 = (ListView) findViewById(R.id.listView1);

        bOrden = (Button) findViewById(R.id.bOrden);
        etFechaEntrega = (EditText) findViewById(R.id.etFechaEntrega);
        etDireccionEntrega = (EditText) findViewById(R.id.etDireccionEntrega);
        etNombreEntrega = (EditText) findViewById(R.id.etNombreEntrega);
        etFechaRecogida = (EditText) findViewById(R.id.etFechaRecogida);
        etDireccionRecogida = (EditText) findViewById(R.id.etDireccionRecogida);
        etNombreRecibe = (EditText) findViewById(R.id.etNombreRecibe);
        etHora = (EditText) findViewById(R.id.etHora);
        etHora2 = (EditText) findViewById(R.id.etHora2);

        userLocalStore = new UserLocalStore(this);
        pedidoLocalStore = new PedidoLocalStore(this);
        bOrden.setOnClickListener(this);


        intercambio();

        llenarFormulario();

        OrdenarProductosPorCategoria();

    }

    public void llenarFormulario(){


        etFechaEntrega.setText(pedido.getFechaEntregaString());
        etDireccionEntrega.setText(pedido.getDireccionEntrega());
        etNombreEntrega.setText(pedido.getQuienEntrega());
        etFechaRecogida.setText(pedido.getFechaRecogidaString());
        etDireccionRecogida.setText(pedido.getDireccionRecogida());
        etNombreRecibe.setText(pedido.getQuienRecibe());
        etHora.setText(pedido.getHoraInicio().getHoraInicio().toString());
        etHora2.setText(pedido.getHoraFinal().getHoraFinal().toString());
    }

    public void OrdenarProductosPorCategoria(){

        int Variable = productos.get(0).getSubProducto().getIdSubProducto();

        Log.i("proceso1: ", "cantidad de productos: " + productos.size() + "");

        int a = 0;

        for(int i = 0; i < productos.size(); i++){

            Log.i("i: ", "valor: " + i);

            if (productos.get(i).getSubProducto().getIdSubProducto()== Variable) {

                productosCat.add(new DescripcionPedido_TO(new SubProducto_TO(productos.get(i).getSubProducto().getIdSubProducto(), productos.get(i).getSubProducto().getNombre())));

                a= i+1;
                if(a == productos.size() ){
                    pedidoDescripcion.setProductos(productosCat);
                    String jsonPedido = pedidoDescripcion.serializePedido();
                    pedidoLocalStore.storePedidoData(jsonPedido);

                    Pedido_TO pedidoDescripcion2 = Pedido_TO.createPedidoFromJson((jsonPedido));

                    productosCatOrd.add(pedidoDescripcion2.getProductos());
                    Log.i("proceso1: ", "1: " + productosCatOrd);
                }else{
                    a = i+1;
                }

            }else{

                pedidoDescripcion.setProductos(productosCat);
                String jsonPedido = pedidoDescripcion.serializePedido();
                pedidoLocalStore.storePedidoData(jsonPedido);

                Pedido_TO pedidoDescripcion2 = Pedido_TO.createPedidoFromJson((jsonPedido));

                productosCatOrd.add(pedidoDescripcion2.getProductos());
                Log.i("proceso2: ", "2: " + productosCatOrd);
               // pedidoDescripcion.getProductos().clear();
                productosCat.clear();
                Variable = productos.get(i).getSubProducto().getIdSubProducto();
                productosCat.add(new DescripcionPedido_TO(new SubProducto_TO(productos.get(i).getSubProducto().getIdSubProducto(), productos.get(i).getSubProducto().getNombre())));
                pedidoDescripcion.setProductos(productosCat);
                a= i+1;
                if(a == productos.size() ){

                    pedidoDescripcion.setProductos(productosCat);
                    jsonPedido = pedidoDescripcion.serializePedido();
                    pedidoLocalStore.storePedidoData(jsonPedido);

                    pedidoDescripcion2 = Pedido_TO.createPedidoFromJson((jsonPedido));

                    productosCatOrd.add(pedidoDescripcion2.getProductos());
                    Log.i("proceso3: ", "3: " + productosCatOrd);
                }else{
                    a = i+1;
                }

            }



        }

        LlenarProductosPorCategoria();

    }

    public void LlenarProductosPorCategoria(){

        final ListView lista1 = (ListView) findViewById(R.id.listView1);

        String cadena ="";
        Log.i("proceso10: ", "Llenar productos por categoria: " + productosCatOrd);
        for(int i = 0; i < productosCatOrd.size();i++){
            Log.i("proceso11: ", "tamaño: " + productosCatOrd.size());
            for (int j = 0; j < productosCatOrd.get(i).size();j++){
                Log.i("proceso12: ", "tamaño: " + productosCatOrd.size());
                Log.i("proceso11: ", "tamañoMatriz: " + productosCatOrd.get(i).size());


            };

            String[] prod = productosCatOrd.get(i).get(0).getSubProducto().getNombre().toString().split(": ");

           String pro = prod[1].toString();
            Producto.add(pro + " x " + productosCatOrd.get(i).size());

        }

        adapter1 = new ListViewAdapter(getApplicationContext(), Producto);

        LayoutParams list = (LayoutParams) lista1.getLayoutParams();

        int a = 100 * productosCatOrd.size();
            list.height = a ;

        lista1.setLayoutParams(list);

        //    lista1.setAdapter(null);
        lista1.setAdapter(adapter1);

        Log.i("Prueba: ", " - " + Producto.toString());
    }


    public void intercambio(){

        Collections.sort(productos);

        Log.i("ListaOrdenada",productos.toString());
    }

    public class ListViewAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        List<String> producto;
        LayoutInflater inflater;

        public ListViewAdapter(Context context, List<String> producto) {
            this.context = context;
            this.producto = producto;
        }
        public ListViewAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return Producto.size();
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
            TextView txtProducto;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.productos, parent, false);

            // Locate the TextViews in listview_item.xml
            txtProducto = (TextView) itemView.findViewById(R.id.textView);

            // Capture position and set to the TextViews
            txtProducto.setText(Producto.get(position));

            //    txtSubtitle.setText(FechaInicio.get(position));

            return itemView;
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bOrden:

                if (userLocalStore.getUserLoggedIn()==true){
                    RegistrarPedidoSesionIniciada();

                }else {

                    Log.i("PedidoCompleto: ", pedido.toString());

                    Intent intent = new Intent(DetalleDeOrden.this, LoginSinRegistro.class);
                    intent.putExtra("pedido", pedido);
                    intent.putExtra("productos", (Serializable) productos);
                    startActivity(intent);
                }



                break;
        }

    }

    private void RegistrarPedidoSesionIniciada(){

        if (userLocalStore.getUserLoggedIn()==true) {

            final int idUsuario = userLocalStore.getLoggedInUser().getIdUsuario();

            final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

            RegistrarPedido servicio = restAdapter.create(RegistrarPedido.class);

            String fechaInicio = pedido.getFechaInicioString();
            int idHoraInicio = pedido.getHoraInicio().getIdHorario();
            int idHoraFinal = pedido.getHoraFinal().getIdHorario();
            int idEstado = pedido.getEstado().getIdEstado();
            String fechaEntrega = pedido.getFechaEntregaString();
            String direccionEntrega = pedido.getDireccionEntrega();
            String direccionRecogida = pedido.getDireccionRecogida();
            String fechaRecogida = pedido.getFechaRecogidaString();
            String quienEntrega = pedido.getQuienEntrega();
            String quienRecibe = pedido.getQuienRecibe();
            int idBarrioRecogida = pedido.getIdBarrioRecogida().getIdBarrios();
            int idBarrioEntrega = pedido.getIdBarrioEntrega().getIdBarrios();
            int idFormaPago = 1; //efectivo
            int idEstadoPago = 2; //no pago


            servicio.registrarPedido(idUsuario, fechaInicio, idHoraInicio, idHoraFinal, idEstado,
                    fechaEntrega, direccionEntrega, direccionRecogida, fechaRecogida, quienEntrega,
                    quienRecibe, idBarrioRecogida, idBarrioEntrega,idFormaPago, idEstadoPago,  new Callback<Pedido_TO>() {
                        @Override
                        public void success(Pedido_TO pedido, Response response) {

                            ConsultarUltimoPedido(idUsuario);
                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //     acceso = 0;
                            Log.i("Error: ", retrofitError.getMessage());
                        }
                    });


        }
    }

    private void ConsultarUltimoPedido(int idUsuario){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarUltimoPedido servicio = restAdapter.create(ConsultarUltimoPedido.class);

        servicio.consultarUltimoPedido(idUsuario , new Callback<Pedido_TO>() {
            @Override
            public void success(Pedido_TO pedido, Response response) {

                int idPedido = pedido.getIdPedido();

                for (int i = 0; i < productos.size(); i++) {
                    RegistrarDescripcionPedido(idPedido, productos.get(i).getSubProducto().getIdSubProducto());
                }

                startActivity(new Intent(DetalleDeOrden.this, Pago.class));

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //     acceso = 0;
                Log.i("Error: ", retrofitError.getMessage());
            }
        });

    }

    private void RegistrarDescripcionPedido(int idPedido, int idSubProd){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        RegistrarDescPedidos servicio = restAdapter.create(RegistrarDescPedidos.class);

        int idEstado = pedido.getEstado().getIdEstado();

        servicio.registrarDescPedido(idEstado, idPedido, idSubProd, new Callback<DescripcionPedido_TO>() {
            @Override
            public void success(DescripcionPedido_TO descripcionPedido, Response response) {

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //     acceso = 0;
                Log.i("Error: ", retrofitError.getMessage());
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
