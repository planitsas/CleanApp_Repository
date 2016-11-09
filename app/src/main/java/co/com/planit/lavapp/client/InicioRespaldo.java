package co.com.planit.lavapp.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.Color_TO;
import co.com.planit.lavapp.models.Costo_TO;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.models.SubProducto_TO;
import co.com.planit.lavapp.service.ConsultarSubProductos;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class InicioRespaldo extends AppCompatActivity {

    Button bCamisas, bTrajes, bFaldas, bChaqueta, bPantalones, bSaco, bAccesorios, bToalla, bCama, bHogar;
    int ultimoPedido;
    int statusPedido = 0;
    private AlertDialog.Builder dialogBuilder;
    Pedido_TO pedido = new Pedido_TO();
    List<DescripcionPedido_TO> productos = new ArrayList<DescripcionPedido_TO>();

    UserLocalStore userLocalStore;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    ListViewAdapter adapter1;

    FloatingActionButton fab;

    List<String> IdProducto = new ArrayList<String>();
    List<String> Nombre = new ArrayList<String>();
    List<String> FechaInicio = new ArrayList<String>();
    List<String> HoraInicio = new ArrayList<String>();
    List<Integer> Imagenes = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(productos.size()>0){ // si no existen productos seleccionados muestra una advertencia de lo contrario entra al ciclo
                if(!(pedido.getEstado()==null)) {

                    intercambio();

                    Log.i("TotalProductos: ",productos.toString());

                }else{
                    Toast.makeText(InicioRespaldo.this, "No tiene ningun Producto Seleccionado", Toast.LENGTH_SHORT);
                }
                Log.i("Objeto Pedido: ",pedido.toString());
                }else{
                    Toast.makeText(InicioRespaldo.this, "No tiene ningun Producto Seleccionado", Toast.LENGTH_SHORT);
                }
            }
        });


          userLocalStore = new UserLocalStore(this);

        bCamisas = (Button) findViewById(R.id.bCamisas);
        bTrajes = (Button) findViewById(R.id.bTrajes);
        bFaldas = (Button) findViewById(R.id.bFaldas);
        bChaqueta = (Button) findViewById(R.id.bChaqueta);
        bPantalones = (Button) findViewById(R.id.bPantalones);
        bSaco = (Button) findViewById(R.id.bSaco);
        bAccesorios = (Button) findViewById(R.id.bAccesorios);
        bCama = (Button) findViewById(R.id.bCama);
        bHogar = (Button) findViewById(R.id.bHogar);

        bCamisas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(1);
                            }
        });

        bTrajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(2);

            }
        });
        bFaldas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(3);

            }
        });
        bChaqueta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(4);

            }
        });

        bPantalones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(5);
            }
        });

        bSaco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(6);

            }
        });

        bAccesorios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(7);

            }
        });

        bCama.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(8);

            }
        });

        bHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConsultarSubProductos(9);

            }
        });

        cantidadProducto();

    }

    public void intercambio(){

        Collections.sort(productos);

        // Se envian a la primera parte del pedido, el objeto completo de pedidos y la lista de productos
        Intent intent = new Intent(InicioRespaldo.this, PedidoParte1.class);
        intent.putExtra("pedido", pedido);
        intent.putExtra("productos", (Serializable) productos);
        startActivity(intent);
    }


    public class ListViewAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        List<String> idProducto;
        List<String> nombre;
        List<String> fechaInicio;
        List<String> horaInicio;
        List<Integer> imagenes;
        LayoutInflater inflater;

        public ListViewAdapter(Context context, List<String> idProducto ,  List<String> nombre, List<String> fechaInicio, List<String> horaInicio, List<Integer> imagenes) {
            this.context = context;
            this.idProducto = idProducto;
            this.nombre = nombre;
            this.horaInicio = horaInicio;
            this.fechaInicio = fechaInicio;
            this.imagenes = imagenes;
        }
        public ListViewAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return IdProducto.size();
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
            TextView txtIdProducto;
            TextView txtNombre;
            TextView txtFechaInicio;
            TextView txtHoraInicio;
            ImageView imgImg;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.list_row, parent, false);

            // Locate the TextViews in listview_item.xml
            txtIdProducto = (TextView) itemView.findViewById(R.id.tv_titulo_single_post_circuito);
            txtNombre = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito);
            txtFechaInicio = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito2);
            txtHoraInicio = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito3);
            imgImg = (ImageView) itemView.findViewById(R.id.imagen_single_post_circuito);

            // Capture position and set to the TextViews
            txtIdProducto.setText(IdProducto.get(position));
            txtNombre.setText(Nombre.get(position));
            txtFechaInicio.setText(FechaInicio.get(position));
            txtHoraInicio.setText(HoraInicio.get(position));
            imgImg.setImageResource(Imagenes.get(position));
            //    txtSubtitle.setText(FechaInicio.get(position));

            return itemView;
        }
    }

    private void ConsultarSubProductos(int idProd) {

        final ListView lista1 = (ListView) findViewById(R.id.listView1);

        Log.i("Gustavo", "funciona");

        Log.i("Gustavo", "funciona-for: " + idProd);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarSubProductos servicio = restAdapter.create(ConsultarSubProductos.class);

            final int fIdProducto = idProd;
            servicio.consultarSubProductos(idProd, new Callback<List<SubProducto_TO>>() {
                @Override
                public void success(List<SubProducto_TO> subProductos, Response response) {

                    Log.i("gustavo reservacion:---", subProductos + "");

                    IdProducto.clear();

                    Nombre.clear();

                    FechaInicio.clear();

                    HoraInicio.clear();

                    Imagenes.clear();


                    for (int i = 0; i < subProductos.size(); i++) {
                        IdProducto.add(subProductos.get(i).getIdSubProducto() + "");
                        Nombre.add("Nombre: " + subProductos.get(i).getNombre());
                        FechaInicio.add("");
                        HoraInicio.add("Precio: " + subProductos.get(i).getCosto().getValor());
//                        Imagenes.add("http://190.146.144.78/LavaApp"+subProductos.get(i).getRutaImagen());
                        Imagenes.add(R.drawable.jeans);
                    }


                    adapter1 = new ListViewAdapter(getApplicationContext(), IdProducto, Nombre, FechaInicio, HoraInicio, Imagenes);


                    lista1.setAdapter(null);
                    lista1.setAdapter(adapter1);


                    lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView adapterView, View view, int posicion, long l) {

                            int idProd = Integer.parseInt(IdProducto.get(posicion));
                            String nombre = Nombre.get(posicion).toString();
                            String costo = HoraInicio.get(posicion).toString();

                            cantidadDePrendas(idProd, nombre,costo);

                        }
                    });
//
                }

                @Override
                public void failure(RetrofitError retrofitError) {
                    Log.i("Error", "mensaje" + retrofitError.getMessage());

                }
            });

    }


    private void cantidadDePrendas(final int idProd, final String nombre, String costos){

        final int idEstado = 3;
        final String desc = "";
        final int idColor = 1;

        String[] Costo = costos.split(": ");

        final int costo = Integer.parseInt(Costo[1].toString());

        //variables
        dialogBuilder = new AlertDialog.Builder(this);
        final Spinner spCantidad = new Spinner(this);

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Cantidad, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spCantidad.setAdapter(adapter1);


        //Proccess
        dialogBuilder.setTitle("Servicio");
        dialogBuilder.setMessage("¿Cuantas prendas de " + nombre + " lavara?");
        dialogBuilder.setView(spCantidad);
        dialogBuilder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                int cant = Integer.parseInt(spCantidad.getSelectedItem().toString());
                if (statusPedido == 0) {

                    Log.i("RegistroPedido0: ", statusPedido + "");
                   // RegistrarPedido(cant,idEstado, idProd);
                    pedido.setEstado(new Estado_TO(idEstado));

                    statusPedido++;

                    for(int i = 1; i <= cant; i++) {

                        productos.add(new DescripcionPedido_TO(new Estado_TO(idEstado), desc, new Color_TO(idColor), new SubProducto_TO(idProd, nombre, new Costo_TO(costo))));

                    }

                    cantidadProducto();

                    pedido.setProductos(productos);

                } else {

                        Log.i("RegistroPedido1: ", statusPedido + "");

                    for(int i = 1; i <= cant; i++) {

                        productos.add(new DescripcionPedido_TO(new Estado_TO(idEstado), desc, new Color_TO(idColor), new SubProducto_TO(idProd, nombre, new Costo_TO(costo))));

                    }
                    cantidadProducto();
                    pedido.setProductos(productos);

                  //  RegistrarDescPedidos(cant, ultimoPedido, idEstado, idProd);
                }
                Toast.makeText(getApplicationContext(), "Cantidad seleccionada", Toast.LENGTH_SHORT);
            }
        });
        dialogBuilder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getApplicationContext(), "Cantidad no seleccionada", Toast.LENGTH_SHORT).show();
            }
        });

        //Output
        AlertDialog dialogCantidadPrendas = dialogBuilder.create();
        dialogCantidadPrendas.show();
    }

/*    private void RegistrarDescPedidos(int cant, int idPedido, int idEstado, int idSubProd){

        Log.i("RegistrarDescPe: ", "cant: "+cant+ "-"+ idPedido+"-"+idEstado+"-"+idSubProd);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        Log.i("RegistrarDescPe2: ", "idEstado: "+idEstado+ " idPedido "+ idPedido+" idSubProd: "+idSubProd);
        RegistrarDescPedidos servicio = restAdapter.create(RegistrarDescPedidos.class);

        for(int i = 1; i <= cant; i++) {
            String desc = "";
            servicio.registrarDescPedido(idEstado, desc, 1, idPedido, idSubProd,
                    new Callback<DescripcionPedido_TO>() {

                        @Override
                        public void success(DescripcionPedido_TO descpedido, Response response) {

                            Log.i("respuestaDesc: ",descpedido+""  );


                        }

                        @Override
                        public void failure(RetrofitError retrofitError) {
                            //     acceso = 0;
                            Log.i("Error", retrofitError.getMessage());
                        }
                    });
        }

    }*/

/*    private void RegistrarPedido(final int cant, final int idUsuario, final int idEstado, final int idProd){

        Log.i("RegistrarPedido: ", idUsuario+" 2016-07-01 "+idEstado+"-"+idProd);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        RegistrarPedido servicio = restAdapter.create(RegistrarPedido.class);


        servicio.registrarPedido(idUsuario,
                "2016-07-01", 2, 2, idEstado, new Callback<Pedido_TO>() {

                    @Override
                    public void success(Pedido_TO pedido, Response response) {

                        ConsultarUltimoPedido(cant, idUsuario, idEstado, idProd);

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        //     acceso = 0;
                        Log.i("Error", retrofitError.getMessage());
                    }
                });

    }*/

/*    private void ConsultarUltimoPedido(final int cant,int idUsuario, final int idEstado, final int idProd) {

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarUltimoPedido servicio = restAdapter.create(ConsultarUltimoPedido.class);

        servicio.consultarUltimoPedido(idUsuario, new Callback<Pedido_TO>() {
            @Override
            public void success(Pedido_TO pedido, Response response) {

                ultimoPedido = pedido.getIdPedido();

                Log.i("RegistrarPedido: ", "Funciona: " + ultimoPedido);

          //      RegistrarDescPedidos(cant, ultimoPedido, idEstado, idProd);

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("Error: " ,"mensaje: " + retrofitError.getMessage());

            }
        });

    }*/

    public void cantidadProducto(){

        if(productos.size()==1) {

            fab.setImageResource(R.drawable.ic_filter_1_white_48dp);
        }else{
            if(productos.size()==2) {

                fab.setImageResource(R.drawable.ic_filter_2_white_48dp);
            }else{
                if(productos.size()==3) {

                    fab.setImageResource(R.drawable.ic_filter_3_white_48dp);
                }else{
                    if(productos.size()==4) {

                        fab.setImageResource(R.drawable.ic_filter_4_white_48dp);
                    }else{
                        if(productos.size()==5) {

                            fab.setImageResource(R.drawable.ic_filter_5_white_48dp);
                        }else{
                            if(productos.size()==6) {

                                fab.setImageResource(R.drawable.ic_filter_6_white_48dp);
                            }else{
                                if(productos.size()==7) {

                                    fab.setImageResource(R.drawable.ic_filter_7_white_48dp);
                                }else{
                                    if(productos.size()==8) {

                                        fab.setImageResource(R.drawable.ic_filter_8_white_48dp);
                                    }else{
                                        if(productos.size()==9) {

                                            fab.setImageResource(R.drawable.ic_filter_9_white_48dp);
                                        }else{
                                            if(productos.size()>9) {

                                                fab.setImageResource(R.drawable.ic_filter_9_plus_white_48dp);
                                            }else{
                                                fab.setImageResource(R.drawable.ic_exposure_zero_white_48dp);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
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

            startActivity(new Intent(this, InicioRespaldo.class));
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