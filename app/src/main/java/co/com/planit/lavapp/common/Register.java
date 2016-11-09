package co.com.planit.lavapp.common;

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
import co.com.planit.lavapp.client.ConsultarPedido;
import co.com.planit.lavapp.client.Inicio;
import co.com.planit.lavapp.client.RegisterEnd;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.config.ValidarCorreo;
import co.com.planit.lavapp.models.Barrio_TO;
import co.com.planit.lavapp.models.Ciudad_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.models.Localidad_TO;
import co.com.planit.lavapp.models.Rol_TO;
import co.com.planit.lavapp.models.Usuario_TO;
import co.com.planit.lavapp.service.ConsultarBarrios;
import co.com.planit.lavapp.service.ConsultarCiudades;
import co.com.planit.lavapp.service.ConsultarLocalidades;
import co.com.planit.lavapp.service.ConsultarUsuarioPorLogin;
import co.com.planit.lavapp.service.RegistrarUsuarios;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class Register extends AppCompatActivity implements View.OnClickListener {

    UserLocalStore userLocalStore;
    int acceso;
    String generos;
    Spinner etCiudad,etBarrio, etGenero, etLocalidad;
    List<String> ciudad = new ArrayList<String>();
    List<String> barrio = new ArrayList<String>();
    List<String> localidad = new ArrayList<String>();
    TextView resultadoTextView;

    Button bRegister;
    EditText etName, etEmail, etApellido, etTelefono,etMovil, etDireccion, etPassword, etRPassword, etCC;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etName = (EditText) findViewById(R.id.etName);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etMovil = (EditText) findViewById(R.id.etMovil);
        etCC = (EditText) findViewById(R.id.etCC);
        etBarrio = (Spinner) findViewById(R.id.etBarrio);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etGenero = (Spinner) findViewById(R.id.etGenero);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRPassword = (EditText) findViewById(R.id.etRPassword);
        bRegister = (Button) findViewById(R.id.bRegister);
        resultadoTextView = (TextView) findViewById(R.id.resultado);

        userLocalStore = new UserLocalStore(this);

        bRegister.setOnClickListener(this);

/*        this.ConsultarCiudad();

        etCiudad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

                if (strServicio.equals("Seleccione")) {

                    ConsultarLocalidad(0);


                } else {

                    String[] Ciudad = etCiudad.getSelectedItem().toString().split(" - ");

                    int idCiudad = Integer.parseInt(Ciudad[0].toString());

                    ConsultarLocalidad(idCiudad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });*/


/*        etLocalidad.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

                if (strServicio.equals("Seleccione")) {

                    ConsultarBarrios(0);


                } else {

                    String[] Localidad = etLocalidad.getSelectedItem().toString().split(" - ");

                    int idLocalidad = Integer.parseInt(Localidad[0].toString());

                    ConsultarBarrios(idLocalidad);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });*/

        ConsultarBarrios();

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

             /*   String[] Ciudad = etCiudad.getSelectedItem().toString().split(" - ");

                int idCiudad = Integer.parseInt(Ciudad[0].toString());

                String[] Localidad = etLocalidad.getSelectedItem().toString().split(" - ");

                int idLocalidad = Integer.parseInt(Localidad[0].toString());*/


                String nombre = etName.getText().toString();
                String telefono = etTelefono.getText().toString();
                int idrol = 4; // Cliente
                int idestado = 1; // Activo
                String email = etEmail.getText().toString();
                String apellido = etApellido.getText().toString();
                String movil = etMovil.getText().toString();
                String password = etPassword.getText().toString();
                String direccion = etDireccion.getText().toString();
                String identificacion = etCC.getText().toString();
                int idCiudad = 1;

                if(!nombre.equals("")&&!apellido.equals("")&&!movil.equals("")&&!direccion.equals("")&&!email.equals("")&&!password.equals("")){

                    if(!etBarrio.getSelectedItem().toString().equals("Seleccione")) {

                        String[] Barrio = etBarrio.getSelectedItem().toString().split(" - ");

                        idBarrio = Integer.parseInt(Barrio[0].toString());

                    } else{
                        Toast.makeText(this, "Debe Seleccionar un Barrio", Toast.LENGTH_LONG).show();
                    }



                    Usuario_TO user = new Usuario_TO(nombre, telefono, new Barrio_TO(idBarrio), new Rol_TO(idrol), new Estado_TO(idestado),
                        email, password, apellido, generos, movil, direccion,new Ciudad_TO(idCiudad),identificacion );


                    Log.i("contrasena", password+ " - "+ etRPassword.getText().toString());

                        if (password.equals(etRPassword.getText().toString())) {


                            if (password.length() < 6) {

                                Toast.makeText(this, "Las contraseñas deben ser mayor o igual a 6 digitos", Toast.LENGTH_LONG).show();


                            } else {

                                boolean VEmail = ValidarCorreo.validarEmail(email);

                                if (VEmail == true) {

                                    this.ConsultarEmailExistente(user);

                                } else {
                                   Toast.makeText(this, "El correo no es válido", Toast.LENGTH_LONG).show();
                               }

                            }

                        } else {
                            Toast.makeText(this, "Las contraseñas no son iguales", Toast.LENGTH_LONG).show();
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
                user.getDireccion(),user.getCiudad().getIdCiudad(), user.getIdentificacion(), "",
                new Callback<Usuario_TO>() {

            @Override
            public void success(Usuario_TO usuario, Response response) {

                ProgressDialog dialog = ProgressDialog.show(Register.this, "",
                        "Resgistrando. Por favor espere...", true);

                startActivity(new Intent(Register.this, RegisterEnd.class));

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

/*    private void ConsultarCiudad() {


        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarCiudades servicio = restAdapter.create(ConsultarCiudades.class);

        servicio.consultarCiudades(new Callback<List<Ciudad_TO>>() {
            @Override
            public void success(List<Ciudad_TO> ciudades, Response response) {

                ciudad.add("Seleccione");

                for (int i = 0; i < ciudades.size(); i++) {
                    ciudad.add(ciudades.get(i).getIdCiudad() + " - " + ciudades.get(i).getNombre().toString());
                }

                LlenarCiudad();

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                resultadoTextView.setText("mensaje: " + retrofitError.getMessage());

            }
        });

    } ;*/


/*    private void LlenarCiudad(){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, ciudad);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etCiudad.setAdapter(adapter);

    }*/

/*    private void ConsultarLocalidad(int idCiudad) {

        localidad.clear();

        Log.i("Gustavo","consultar localidad id ciudad: " + idCiudad);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarLocalidades servicio = restAdapter.create(ConsultarLocalidades.class);

        servicio.consultarLocalidades(idCiudad, new Callback<List<Localidad_TO>>() {
            @Override
            public void success(List<Localidad_TO> localidades, Response response) {


                if (localidades.size() != 0 && localidades != null) {

                    localidad.add("Seleccione");
                    Log.i("pruebaLocalidad: ", "Si " + localidades.toString());

                    for (int i = 0; i < localidades.size(); i++) {

                        localidad.add(localidades.get(i).getIdLocalidad() + " - " + localidades.get(i).getNombre());

                    }

                    LlenarLocalidad();

                } else {
                    localidad.add("Seleccione");
                    Log.i("pruebaLocalidad: ", "No " + localidades.toString());

                    LlenarLocalidad();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("pruebaLocalidad: ", "No no ");
                localidad.add("Seleccione");

                LlenarLocalidad();
                resultadoTextView.setText("mensaje" + retrofitError.getMessage());

            }
        });

    } ;

    private void LlenarLocalidad(){

        Log.i("Gustavo","llenar localidad: " + localidad);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, localidad);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etLocalidad.setAdapter(adapter3);
    }*/

    private void ConsultarBarrios() {

        barrio.clear();

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarBarrios servicio = restAdapter.create(ConsultarBarrios.class);

        servicio.consultarBarrios( new Callback<List<Barrio_TO>>() {
            @Override
            public void success(List<Barrio_TO> barrios, Response response) {

                if (barrios.size() != 0 && barrios != null) {
                    barrio.add("Seleccione");
                    for (int i = 0; i < barrios.size(); i++) {
                        barrio.add(barrios.get(i).getIdBarrios() + " - " + barrios.get(i).getNombre());
                    }
                    Log.i("Gustavo","llenar Barrios1: " + barrio);
                    LlenarBarrios();
                } else {
                    barrio.clear();
                    barrio.add("Seleccione");

                    Log.i("Gustavo", "llenar Barrios2: " + barrio);
                    LlenarBarrios();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {

                barrio.clear();
                barrio.add("Seleccione");

                Log.i("Gustavo", "llenar Barrios3: " + barrio);
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
