package co.com.planit.lavapp.client;

import android.app.ProgressDialog;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.config.ValidarCorreo;
import co.com.planit.lavapp.models.Barrio_TO;
import co.com.planit.lavapp.models.Ciudad_TO;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.models.Localidad_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.models.Rol_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import co.com.planit.lavapp.service.ConsultarBarrios;
import co.com.planit.lavapp.service.ConsultarCiudades;
import co.com.planit.lavapp.service.ConsultarLocalidades;
import co.com.planit.lavapp.service.ConsultarUltimoPedido;
import co.com.planit.lavapp.service.ConsultarUsuarioPorLogin;
import co.com.planit.lavapp.service.RegistrarDescPedidos;
import co.com.planit.lavapp.service.RegistrarPedido;
import co.com.planit.lavapp.service.RegistrarUsuarios;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.http.Query;

public class PedidoParte3 extends AppCompatActivity implements View.OnClickListener {

    UserLocalStore userLocalStore;
    int acceso;
    String generos;
    Spinner etBarrio, etGenero;
    List<String> barrio = new ArrayList<String>();
    TextView resultadoTextView;

    Pedido_TO pedido = new Pedido_TO();
    List<DescripcionPedido_TO> productos = new ArrayList<DescripcionPedido_TO>();

    Button bRegister;
    EditText etName, etEmail, etApellido, etTelefono,etMovil, etDireccion,etCC;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_parte3);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pedido = (Pedido_TO)getIntent().getExtras().getSerializable("pedido");
        productos = (List<DescripcionPedido_TO>)getIntent().getExtras().getSerializable("productos");

        etName = (EditText) findViewById(R.id.etName);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etCC = (EditText) findViewById(R.id.etCC);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etMovil = (EditText) findViewById(R.id.etMovil);
        etBarrio = (Spinner) findViewById(R.id.etBarrio);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etGenero = (Spinner) findViewById(R.id.etGenero);
        etEmail = (EditText) findViewById(R.id.etEmail);
        bRegister = (Button) findViewById(R.id.bRegister);
        resultadoTextView = (TextView) findViewById(R.id.resultado);

        userLocalStore = new UserLocalStore(this);

        bRegister.setOnClickListener(this);

        this.ConsultarBarrios();

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.Genero, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etGenero.setAdapter(adapter1);

        etGenero.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

                if (strServicio.equals("Masculino")) {

                    generos = "M";


                } else {

                    generos = "F";
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bRegister:

                int idBarrio = 0;

                String nombre = etName.getText().toString();
                String telefono = etTelefono.getText().toString();
                int idrol = 4; // Cliente
                int idestado = 1; // Activo
                String email = etEmail.getText().toString();
                String apellido = etApellido.getText().toString();
                String movil = etMovil.getText().toString();
                String direccion = etDireccion.getText().toString();
                String identificacion = etCC.getText().toString();
                String contrasena = "123123";
                int idCiudad = 1;

                if(!nombre.equals("")&&!apellido.equals("")&&!movil.equals("")&&!direccion.equals("")&&!email.equals("")&&!identificacion.equals("")){

                if(!etBarrio.getSelectedItem().toString().equals("Seleccione")) {

                    if(etBarrio.getSelectedItem().toString().indexOf(" - ")>0) {
                    String[] Barrio = etBarrio.getSelectedItem().toString().split(" - ");

                    idBarrio = Integer.parseInt(Barrio[0].toString());


                Usuario_TO user = new Usuario_TO(nombre, telefono, new Barrio_TO(idBarrio), new Rol_TO(idrol), new Estado_TO(idestado),
                        email, contrasena, apellido, generos, movil, direccion,new Ciudad_TO(idCiudad),identificacion );

                            boolean VEmail = ValidarCorreo.validarEmail(email);

                            if (VEmail == true) {

                                this.ConsultarEmailExistente(user);

                            } else {
                                Toast.makeText(this, "El correo no es válido", Toast.LENGTH_LONG).show();
                            }

                    }else{
                        Toast toast1 =
                                Toast.makeText(this, "El barrio no se ha seleccionado de manera correcta", Toast.LENGTH_LONG);

                        toast1.show();
                    }

                }else{
                    Toast.makeText(this, "Debe seleccionar un Barrio", Toast.LENGTH_LONG).show();
                }

                }else{
                    Toast.makeText(this, "No puede tener campos vacios", Toast.LENGTH_LONG).show();
                }



                break;
        }
    }

    private void Registrar(final Usuario_TO user){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        RegistrarUsuarios servicio = restAdapter.create(RegistrarUsuarios.class);

        servicio.registrarUsuarios(user.getNombre(), user.getTelefono(),
                user.getBarrio().getIdBarrios(), user.getRol().getIdRol(),
                user.getEstado().getIdEstado(), user.getEmail(),
                user.getContrasena(), user.getApellido(),
                user.getGenero(), user.getMovil(),
                user.getDireccion(),user.getCiudad().getIdCiudad(), user.getIdentificacion(),"",
                new Callback<Usuario_TO>() {

                    @Override
                    public void success(Usuario_TO usuario, Response response) {

               //         startActivity(new Intent(PedidoParte3.this, RegisterEnd.class));

                        ConsultarUsuarioPorLogin(user.getEmail().toString());

                        //   EnviarEmail(user);

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        //     acceso = 0;
                        resultadoTextView.setText("Error: " + retrofitError.getMessage());
                    }
                });

    }

    private void ConsultarEmailExistente(final Usuario_TO user){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarUsuarioPorLogin servicio = restAdapter.create(ConsultarUsuarioPorLogin.class);

        servicio.consultarUsuarioPorLogin(user.getEmail(), new Callback<Usuario_TO>() {
            @Override
            public void success(Usuario_TO usuario, Response response) {

                if (usuario.getIdUsuario() > 0) {
                    Toast.makeText(getApplicationContext(), "El Usuario Registrado ya existe", Toast.LENGTH_LONG).show();

                } else {

                    Registrar(user);
                }
            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //     acceso = 0;
                resultadoTextView.setText("Error: " + retrofitError.getMessage());
            }
        });

    }

    //   private void EnviarEmail(Usuario_TO user){

    //      final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

    //      EmailClienteBienvenido servicio = restAdapter.create(EmailClienteBienvenido.class);

    //    Log.i("Gustavo", user.getEmail() + "-" + user.getNombre() + "-" + user.getPassword());

    //   servicio.emailClienteBienvenido(user.getEmail(), user.getNombre(), user.getPassword(), new Callback<Usuario_TO>() {
    //       @Override
    //       public void success(Usuario_TO usuario, Response response) {

    //
    //     }

    //       @Override
    //       public void failure(RetrofitError retrofitError) {
    //     acceso = 0;
    //            resultadoTextView.setText("Error: " + retrofitError.getMessage());
    //        }
    //    });

    //  }

    private void ConsultarBarrios() {

        barrio.clear();

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarBarrios servicio = restAdapter.create(ConsultarBarrios.class);

        servicio.consultarBarrios(new Callback<List<Barrio_TO>>() {
            @Override
            public void success(List<Barrio_TO> barrios, Response response) {

                if (barrios.size() != 0 && barrios != null) {
                    barrio.add("Seleccione");
                    for (int i = 0; i < barrios.size(); i++) {
                        barrio.add(barrios.get(i).getIdBarrios() + " - " + barrios.get(i).getNombre());
                    }
                    LlenarBarrios();
                } else {
                    barrio.clear();
                    barrio.add("Seleccione");

                    LlenarBarrios();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {

                barrio.clear();
                barrio.add("Seleccione");

                LlenarBarrios();

                resultadoTextView.setText("mensaje" + retrofitError.getMessage());

            }
        });

    } ;

    private void LlenarBarrios(){

        Log.i("Gustavo","llenar Barrios: " + barrio);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, barrio);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etBarrio.setAdapter(adapter2);
    }

    private void ConsultarUsuarioPorLogin(String Login){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarUsuarioPorLogin servicio = restAdapter.create(ConsultarUsuarioPorLogin.class);

        servicio.consultarUsuarioPorLogin(Login, new Callback<Usuario_TO>() {
            @Override
            public void success(Usuario_TO usuario, Response response) {

                userLocalStore.setUserLoggedIn(true);

                userLocalStore.storeUserData(usuario);

                int idUsuario = usuario.getIdUsuario();

                RegistrarPedido(idUsuario);

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

                        startActivity(new Intent(PedidoParte3.this, Pago.class));

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        //     acceso = 0;
                        resultadoTextView.setText("Error: " + retrofitError.getMessage());
                    }
                });

    }

    private void RegistrarDescripcionPedido(int idPedido, int idSubProd){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        RegistrarDescPedidos servicio = restAdapter.create(RegistrarDescPedidos.class);

        int idEstado = pedido.getEstado().getIdEstado();

        servicio.registrarDescPedido(idEstado,idPedido ,idSubProd, new Callback<DescripcionPedido_TO>() {
                    @Override
                    public void success(DescripcionPedido_TO descripcionPedido, Response response) {


                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        //     acceso = 0;
                        resultadoTextView.setText("Error: " + retrofitError.getMessage());
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
