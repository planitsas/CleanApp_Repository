package co.com.planit.lavapp.client;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
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
import co.com.planit.lavapp.config.PedidoLocalStore;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.Color_TO;
import co.com.planit.lavapp.models.Costo_TO;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.models.SubProducto_TO;
import co.com.planit.lavapp.service.ServerRequests;

public class PedidoParte1_respaldo extends AppCompatActivity implements View.OnClickListener  {

    UserLocalStore userLocalStore;

    Button bOrden;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    PedidoLocalStore pedidoLocalStore;

    ListViewAdapter adapter1;

    List<String> IdProducto = new ArrayList<String>();
    List<String> Nombre = new ArrayList<String>();
    List<String> Precio = new ArrayList<String>();
    List<String> Descripcion = new ArrayList<String>();
    List<Integer> Imagenes = new ArrayList<Integer>();

    Pedido_TO pedido = new Pedido_TO(); // se guardara el pedido completo que proviene del inicio
    Pedido_TO pedidoDescripcion = new Pedido_TO(); //
    List<DescripcionPedido_TO> productos = new ArrayList<DescripcionPedido_TO>(); // se almacenan la lista de productos que proviene del inicio
    List<DescripcionPedido_TO> productos2 = new ArrayList<DescripcionPedido_TO>();
    List<DescripcionPedido_TO> productosCat = new ArrayList<DescripcionPedido_TO>();
    List<List<DescripcionPedido_TO>> productosCatOrd = new ArrayList<List<DescripcionPedido_TO>>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_parte1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);
        pedidoLocalStore = new PedidoLocalStore(this);

        pedido = (Pedido_TO)getIntent().getExtras().getSerializable("pedido");
        productos = (List<DescripcionPedido_TO>)getIntent().getExtras().getSerializable("productos");

        bOrden = (Button) findViewById(R.id.bOrden);

        intercambio();

        bOrden.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bOrden:

                Intent intent = new Intent(PedidoParte1_respaldo.this, PedidoParte2.class);
                intent.putExtra("pedido", pedido);
                intent.putExtra("productos", (Serializable) productos);
                startActivity(intent);

                break;
        }

    }

    public class ListViewAdapter extends BaseAdapter {
        // Declare Variables
        Context context;
        List<String> idProducto;
        List<String> nombre;
        List<String> precio;
        List<String> descripcion;
        List<Integer> imagenes;
        LayoutInflater inflater;

        public ListViewAdapter(Context context, List<String> idProducto ,  List<String> nombre, List<String> precio, List<String> descripcion, List<Integer> imagenes) {
            this.context = context;
            this.idProducto = idProducto;
            this.nombre = nombre;
            this.precio = precio;
            this.descripcion = descripcion;
            this.imagenes = imagenes;
        }
        public ListViewAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return idProducto.size();
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
            TextView txtIdPProducto;
            TextView txtNombre;
            TextView txtPrecio;
            TextView txtDescripcion;
            ImageView imgImg;

            //http://developer.android.com/intl/es/reference/android/view/LayoutInflater.html
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View itemView = inflater.inflate(R.layout.list_row, parent, false);

            // Locate the TextViews in listview_item.xml
            txtIdPProducto = (TextView) itemView.findViewById(R.id.tv_titulo_single_post_circuito);
            txtNombre = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito);
            txtPrecio = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito2);
            txtDescripcion = (TextView) itemView.findViewById(R.id.tv_contenido_single_post_circuito3);
            imgImg = (ImageView) itemView.findViewById(R.id.imagen_single_post_circuito);

            // Capture position and set to the TextViews
            txtIdPProducto.setText(IdProducto.get(position));
            txtNombre.setText(Nombre.get(position));
            txtPrecio.setText(Precio.get(position));
            txtDescripcion.setText(Descripcion.get(position));
            imgImg.setImageResource(Imagenes.get(position));
            //    txtSubtitle.setText(FechaInicio.get(position));

            return itemView;
        }
    }

/*    private void ConsultarDescripPedidos() {

        final ListView lista1 = (ListView) findViewById(R.id.listView1);

                Log.i("pedidos: ", pedido + "");

                IdProducto.clear();

                Nombre.clear();

                Precio.clear();

                Descripcion.clear();

                Imagenes.clear();


                for (int i = 0; i < productos.size(); i++) {
                    Log.i("pedidoPrueba: ",i+" - "+pedido.getProductos().get(i).getSubProducto().getCosto().getValor());
                    IdProducto.add(i + "");
                    Nombre.add(productos.get(i).getSubProducto().getNombre());
                    Precio.add("Precio: "+ productos.get(i).getSubProducto().getCosto().getValor());
                    Descripcion.add("");
                    Imagenes.add(R.drawable.jeans);
                }

                adapter1 = new ListViewAdapter(getApplicationContext(), IdProducto, Nombre, Precio, Descripcion, Imagenes);


                lista1.setAdapter(null);
                lista1.setAdapter(adapter1);


                lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView adapterView, View view, int posicion, long l) {

                        //    if (userLocalStore.getUserLoggedIn() == false) {


                        final int idDesPed = Integer.parseInt(IdProducto.get(posicion));


                        new AlertDialog.Builder(PedidoParte1.this)
                                .setTitle("Eliminar Producto")
                                .setMessage("¿Deseas Eliminar Este Producto?")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // EliminarProducto(idDesPed);

                                        productos.remove(idDesPed);
                                        pedido.setProductos(productos);
                                        ConsultarDescripPedidos();
                                        Toast toast1 =
                                                Toast.makeText(PedidoParte1.this, "Producto Eliminado Correctamente", Toast.LENGTH_LONG);

                                        toast1.show();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    }
                });

    }*/

    public void OrdenarProductosPorCategoria(){

        productosCat = new ArrayList<DescripcionPedido_TO>();
        productosCatOrd = new ArrayList<List<DescripcionPedido_TO>>();

        int Variable = productos.get(0).getSubProducto().getIdSubProducto();

        Log.i("proceso1: ", "cantidad de productos: " + productos.size() + "");

        int a = 0;

        for(int i = 0; i < productos.size(); i++){

            Log.i("i: ", "valor: " + i);

            if (productos.get(i).getSubProducto().getIdSubProducto()== Variable) {

                productosCat.add(new DescripcionPedido_TO(new SubProducto_TO(productos.get(i).getSubProducto().getIdSubProducto(), productos.get(i).getSubProducto().getNombre(),new Costo_TO(productos.get(i).getSubProducto().getCosto().getValor()))));

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
                productosCat.add(new DescripcionPedido_TO(new SubProducto_TO(productos.get(i).getSubProducto().getIdSubProducto(), productos.get(i).getSubProducto().getNombre(),new Costo_TO(productos.get(i).getSubProducto().getCosto().getValor()))));
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

        IdProducto.clear();

        Nombre.clear();

        Precio.clear();

        Descripcion.clear();

        Imagenes.clear();

        String cadena ="";
        Log.i("proceso10: ", "Llenar productos por categoria: " + productosCatOrd);
        for(int i = 0; i < productosCatOrd.size();i++){
            Log.i("proceso11: ", "tamaño: " + productosCatOrd.size());
            for (int j = 0; j < productosCatOrd.get(i).size();j++){
                Log.i("proceso12: ", "tamaño: " + productosCatOrd.size());
                Log.i("proceso11: ", "tamañoMatriz: " + productosCatOrd.get(i).size());


            };

            IdProducto.add(i + "");
            Precio.add("Precio: "+ productosCatOrd.get(i).get(0).getSubProducto().getCosto().getValor());
            Descripcion.add("");
            Imagenes.add(R.drawable.jeans);
            String[] prod = productosCatOrd.get(i).get(0).getSubProducto().getNombre().toString().split(": ");

            String pro = prod[1].toString();
            Nombre.add(pro + " x " + productosCatOrd.get(i).size());

        }

        adapter1 = new ListViewAdapter(getApplicationContext(), IdProducto, Nombre, Precio, Descripcion, Imagenes);

        lista1.setAdapter(null);
        lista1.setAdapter(adapter1);

        lista1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView adapterView, View view, int posicion, long l) {

                //    if (userLocalStore.getUserLoggedIn() == false) {


                final int idDesPed = Integer.parseInt(IdProducto.get(posicion));


                new AlertDialog.Builder(PedidoParte1_respaldo.this)
                        .setTitle("Eliminar Producto")
                        .setMessage("¿Deseas Eliminar Este Producto?")
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        // EliminarProducto(idDesPed);


                                        Log.i("ProductosCatOrd: ", "ProductosCartOrd antes: " + productosCatOrd);
                                        productosCatOrd.get(idDesPed).remove(0);
                                        productos2 = new ArrayList<DescripcionPedido_TO>();

                                        Log.i("ProductosCatOrd: ", "ProductosCartOrd despues: " + productosCatOrd);

                                        int idEstado = pedido.getEstado().getIdEstado();
                                        String desc = "";
                                        int idColor = 1;

                                        for (int i = 0; i < productosCatOrd.size(); i++) {

                                            for (int j = 0; j < productosCatOrd.get(i).size(); j++) {
                                                productos2.add(new DescripcionPedido_TO(new Estado_TO(idEstado), desc, new Color_TO(idColor), new SubProducto_TO(pedido.getProductos().get(i).getSubProducto().getIdSubProducto(), pedido.getProductos().get(i).getSubProducto().getNombre(), new Costo_TO(pedido.getProductos().get(i).getSubProducto().getCosto().getValor()))));

                                            }


                                    /*    productos.clear();
                                        for (int i = 0; i < productosCatOrd.size(); i++) {
                                            for (int j = 0; j < productosCatOrd.get(i).size(); j++) {
                                                productos.add(new DescripcionPedido_TO(new Estado_TO(idEstado), desc, new Color_TO(idColor), new SubProducto_TO(pedido.getProductos().get(i).getSubProducto().getIdSubProducto(), pedido.getProductos().get(i).getSubProducto().getNombre(), new Costo_TO(pedido.getProductos().get(i).getSubProducto().getCosto().getValor()))));

                                            }
                                        }*/

                                            //    pedido.setProductos(productos);
                                            //        intercambio();

                                       //     productos.clear();
                                            productos = new ArrayList<DescripcionPedido_TO>();
                                            productos = productos2;
                                            pedido.setProductos(productos);
                                            intercambio();
                                            Toast toast1 =
                                                    Toast.makeText(PedidoParte1_respaldo.this, "Producto Eliminado Correctamente", Toast.LENGTH_LONG);
                                            toast1.show();
                                        }
                                    }
                                }

                        ).

                            setNegativeButton(android.R.string.no, null)

                            .

                            show();
                        }
            });
    }


    public void intercambio(){

        Collections.sort(productos);

        Log.i("ListaOrdenada", productos.toString());

      //  ConsultarDescripPedidos();
        OrdenarProductosPorCategoria();
    }

/*    private void EliminarProducto(final int idPedido, int idDescripcionpedido){

        final int idUsuario = userLocalStore.getLoggedInUser().getIdUsuario();

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        EliminarDescPedido servicio = restAdapter.create(EliminarDescPedido.class);

        servicio.eliminardescPedido(idPedido,idDescripcionpedido ,
                new Callback<DescripcionPedido_TO>() {
                    @Override
                    public void success(DescripcionPedido_TO descPedido, Response response) {

                        ConsultarDescripPedidos();

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {

                        Log.i("gustavo", "Error: " + retrofitError.getMessage());

                    }


                });
    }*/

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
