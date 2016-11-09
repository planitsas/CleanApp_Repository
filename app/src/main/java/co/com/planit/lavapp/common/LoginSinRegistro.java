package co.com.planit.lavapp.common;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.client.ConsultarPedido;
import co.com.planit.lavapp.client.Inicio;
import co.com.planit.lavapp.client.Pago;
import co.com.planit.lavapp.client.PedidoParte2;
import co.com.planit.lavapp.client.PedidoParte3;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.config.md5;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import co.com.planit.lavapp.service.ConsultarUltimoPedido;
import co.com.planit.lavapp.service.ConsultarUsuarioPorLogin;
import co.com.planit.lavapp.service.RegistrarDescPedidos;
import co.com.planit.lavapp.service.RegistrarPedido;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class LoginSinRegistro extends AppCompatActivity implements View.OnClickListener {

    TextView resultadoTextView;
    Button bLogin, bContinuar;
    EditText etEmail, etPassword;
    UserLocalStore userLocalStore;
    ServerRequests serverRequests = new  ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    Pedido_TO pedido = new Pedido_TO();
    List<DescripcionPedido_TO> productos = new ArrayList<DescripcionPedido_TO>();


    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bLogin:

                String email = etEmail.getText().toString();
                String contrasena = etPassword.getText().toString();

                Usuario_TO usuario = new Usuario_TO(email,contrasena);


                if(!email.equals("")&&!contrasena.equals("")){



                    this.IniciarSersion(usuario);



                }else{
                    Toast.makeText(this, "Debe introducir Usuario y Contraseña", Toast.LENGTH_LONG).show();
                }

                break;

            case R.id.bContinuar:

                Intent intent = new Intent(LoginSinRegistro.this, PedidoParte3.class);
                intent.putExtra("pedido", pedido);
                intent.putExtra("productos", (Serializable) productos);
                startActivity(intent);
                break;
        }

    }

    private void showErrorMessage(){
        AlertDialog.Builder dialogBiulder = new AlertDialog.Builder(LoginSinRegistro.this);
        dialogBiulder.setMessage("incorrect user details");
        dialogBiulder.setPositiveButton("ok", null);
        dialogBiulder.show();

    }

    private void logUserIn(){

        userLocalStore.setUserLoggedIn(true);

        startActivity(new Intent(this, Pago.class));

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_sin_registro);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pedido = (Pedido_TO)getIntent().getExtras().getSerializable("pedido");
        productos = (List<DescripcionPedido_TO>)getIntent().getExtras().getSerializable("productos");

        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        bLogin = (Button) findViewById(R.id.bLogin);
        bContinuar = (Button) findViewById(R.id.bContinuar);

        bLogin.setOnClickListener(this);
        bContinuar.setOnClickListener(this);

        userLocalStore = new UserLocalStore(this);

        resultadoTextView = (TextView) findViewById(R.id.resultado);

        //   final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.0.128:8084/SMS_rentas_servicio/resources").build();

        //    ConsultarDatosSesion servicio = restAdapter.create(ConsultarDatosSesion.class);

        //     servicio.consultarDatosSesionAsync("administradorLogin", "123456", new Callback<Usuario_TO>() {
        //         @Override
        //         public void success(Usuario_TO usuario, Response response) {
        //    resultadoTextView.setText(usuario.toString());
        //       }

        //        @Override
        //       public void failure(RetrofitError retrofitError) {
        //           resultadoTextView.setText(retrofitError.getMessage());
        //        }
        //     });

        //----------------------------

        //     ConsultarClienteService servicio = restAdapter.create(ConsultarClienteService.class);

        //      servicio.consultarClientes(new Callback<List<Usuario_TO>>() {
        //          @Override
        //          public void success(List<Usuario_TO> usuarios, Response response) {

        //              resultadoTextView.setText(usuarios.toString());

        //          }

        //         @Override
        ///          public void failure(RetrofitError retrofitError) {
        //              resultadoTextView.setText(retrofitError.getMessage());
        //          }
        //       });

    }

    private void IniciarSersion(Usuario_TO user){

        userLocalStore = new UserLocalStore(this);

        Log.i("Datos Sesion: ", user.getEmail() + "--- " + user.getContrasena() + "");


        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarUsuarioPorLogin servicio = restAdapter.create(ConsultarUsuarioPorLogin.class);

        servicio.consultarUsuarioPorLogin(user.getEmail(), new Callback<Usuario_TO>() {
            @Override
            public void success(Usuario_TO usuario, Response response) {

                if (usuario.getIdUsuario() > 0) {

                    String Contrasena = etPassword.getText().toString();

                    md5 md5 = new md5();

                    Contrasena = md5.getMD5(Contrasena);

                    if ( usuario.getContrasena().toString().equals(Contrasena)){

                        userLocalStore.setUserLoggedIn(true);

                        userLocalStore.storeUserData(usuario);

                        resultadoTextView.setText("Usuario correcto");

                        int idUsuario = usuario.getIdUsuario();

                        RegistrarPedido(idUsuario);

//                        Toast.makeText(getApplicationContext(), "Iniciando Sesion, Por favor, Espere", Toast.LENGTH_LONG).show();
//
//
//                        logUserIn();

                    }

                } else {

                    Toast.makeText(getApplicationContext(), "Usuario o contraseña invalida", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //     acceso = 0;
                resultadoTextView.setText("Error: " + retrofitError.getMessage());
            }
        });

    }

    private void RegistrarPedido(final int idUsuario){

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
                        resultadoTextView.setText("Error: " + retrofitError.getMessage());
                    }
                });

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

                Toast.makeText(getApplicationContext(), "Iniciando Sesion, Por favor, Espere", Toast.LENGTH_LONG).show();
//
//
                logUserIn();

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



    //            userLocalStore.setUserLoggedIn(true);
//
    //           userLocalStore.storeUserData(usuario);

    //    resultadoTextView.setText(usuario.getMensaje());

/*                if (usuario.getMensaje().equals("Usuario correcto")) {

                    Toast.makeText(getApplicationContext(), "Iniciando Sesion, Por favor, Espere", Toast.LENGTH_LONG).show();

                    int idUsuario = usuario.getIdUsuario();
                    int idEstado = 1;

                    EditarEstadoConductores(idEstado, idUsuario);




                }*/

    //           logUserIn();
    //        }

    //       @Override
    //       public void failure(RetrofitError retrofitError) {
    //           resultadoTextView.setText(retrofitError.getMessage());
    //        }
    //       });



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
