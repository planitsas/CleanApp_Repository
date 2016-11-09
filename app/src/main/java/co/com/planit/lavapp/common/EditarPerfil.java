package co.com.planit.lavapp.common;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
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

import co.com.planit.lavapp.Adviser.AsesorInicio;
import co.com.planit.lavapp.Adviser.ConsultarPedidosAsesor;
import co.com.planit.lavapp.Adviser.ConsultarPedidosAsesorEntrega;
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
import co.com.planit.lavapp.service.ConsultarBarrio;
import co.com.planit.lavapp.service.ConsultarBarrios;
import co.com.planit.lavapp.service.ConsultarCiudades;
import co.com.planit.lavapp.service.ConsultarLocalidades;
import co.com.planit.lavapp.service.ConsultarUsuarioPorLogin;
import co.com.planit.lavapp.service.EditarInformacionUsuario;
import co.com.planit.lavapp.service.RegistrarUsuarios;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class EditarPerfil extends AppCompatActivity implements View.OnClickListener {
    int acceso;
    String generos;
    Spinner etCiudad,etBarrio, etLocalidad;
    Usuario_TO UsuarioP = new Usuario_TO();
    List<String> ciudad = new ArrayList<String>();
    List<String> barrio = new ArrayList<String>();
    List<String> localidad = new ArrayList<String>();
    TextView resultadoTextView, tvBarrio,tvGenero;

    Button bEditar;
    EditText etName, etEmail, etApellido, etTelefono,etMovil, etDireccion, etPassword, etRPassword,etGenero,etCC;

    UserLocalStore userLocalStore;
    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        tvGenero = (TextView) findViewById(R.id.tvGenero);
        tvBarrio = (TextView) findViewById(R.id.tvBarrio);
        etName = (EditText) findViewById(R.id.etName);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etMovil = (EditText) findViewById(R.id.etMovil);
        etCC = (EditText) findViewById(R.id.etCC);
        etBarrio = (Spinner) findViewById(R.id.etBarrio);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etGenero = (EditText) findViewById(R.id.etGenero);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRPassword = (EditText) findViewById(R.id.etRPassword);
        bEditar = (Button) findViewById(R.id.bEditar);
        resultadoTextView = (TextView) findViewById(R.id.resultado);

        userLocalStore = new UserLocalStore(this);

        bEditar.setOnClickListener(this);

        ConsultarBarrios();

        this.ConsultarUsuario();

        etName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                UsuarioP.setNombre(etName.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        etApellido.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                UsuarioP.setApellido(etApellido.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etTelefono.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                UsuarioP.setTelefono(etTelefono.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etMovil.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                UsuarioP.setMovil(etMovil.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etCC.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                UsuarioP.setIdentificacion(etCC.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etDireccion.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                UsuarioP.setDireccion(etDireccion.getText().toString());

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        etBarrio.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

                if (strServicio.equals("Seleccione")) {


                } else {

                    String[] Barrio = etBarrio.getSelectedItem().toString().split(" - ");

                    int idBarrio = Integer.parseInt(Barrio[0].toString());
                    String barrio = Barrio[1].toString();

                    UsuarioP.getBarrio().setIdBarrios(idBarrio);

                    tvBarrio.setText("Barrio actual: " + barrio);
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
            case R.id.bEditar:

                String nombre = etName.getText().toString();
          //      String telefono = etTelefono.getText().toString();
            //    int idrol = 4; // Cliente
           //     int idestado = 1; // Activo
                String email = etEmail.getText().toString();
                String apellido = etApellido.getText().toString();
                String movil = etMovil.getText().toString();
          //      String password = etPassword.getText().toString();
                String direccion = etDireccion.getText().toString();
            //    String identificacion = etCC.getText().toString();
          //      int idCiudad = 1;

                if (!nombre.equals("") && !apellido.equals("") && !movil.equals("") && !direccion.equals("") && !email.equals("")) {


                    EditarPerfil();


                    break;
                }
        }
    }

    private void EditarPerfil(){

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        EditarInformacionUsuario servicio = restAdapter.create(EditarInformacionUsuario.class);

        Log.i("Usuariop: ",UsuarioP.toString() );

        servicio.editarUsuario(UsuarioP.getIdUsuario(),
                UsuarioP.getNombre(),
                UsuarioP.getApellido(),
                UsuarioP.getGenero(),
                UsuarioP.getTelefono(),
                UsuarioP.getBarrio().getIdBarrios(),
                UsuarioP.getMovil(),
                UsuarioP.getDireccion(),
                UsuarioP.getCiudad().getIdCiudad(),
                UsuarioP.getIdentificacion(), UsuarioP.getRutaImagen(),
                new Callback<Usuario_TO>() {

                    @Override
                    public void success(Usuario_TO usuario, Response response) {

                        Toast toast1;
                       toast1 = Toast.makeText(EditarPerfil.this, "Usuario Editado Correctamente", Toast.LENGTH_LONG);
                        toast1.show();

                        // startActivity(new Intent(EditarPerfil.this, RegisterEnd.class));

                        //   EnviarEmail(user);

                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {
                        //     acceso = 0;
                        Log.i("Error: ",retrofitError.getMessage()+"");
                    }
                });

    }

    private void ConsultarUsuario(){

        String email = userLocalStore.getLoggedInUser().getEmail();

        Log.i("gustavo","email: "+email);

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarUsuarioPorLogin servicio = restAdapter.create(ConsultarUsuarioPorLogin.class);

        servicio.consultarUsuarioPorLogin(email, new Callback<Usuario_TO>() {
            @Override
            public void success(Usuario_TO usuario, Response response) {

                UsuarioP = usuario;

                Log.i("gustavo", "usuario retorna: " + UsuarioP.toString());

                etName.setText(usuario.getNombre().toString());
                etApellido.setText(usuario.getApellido().toString());
                etTelefono.setText(usuario.getTelefono().toString());
                etMovil.setText(usuario.getMovil().toString());
                etCC.setText(usuario.getIdentificacion().toString());
          //      tvBarrio.setText("Barrio actual: "+usuario.getCiudad().getNombre());
          //      etCiudad = (Spinner) findViewById(R.id.etCiudad);
            //    etBarrio = (Spinner) findViewById(R.id.etBarrio);
            //    etLocalidad = (Spinner) findViewById(R.id.etLocalidad);
                etDireccion.setText(usuario.getDireccion().toString());
           //     etGenero = (Spinner) findViewById(R.id.etGenero);
                etEmail.setText(usuario.getEmail().toString());

            //    etPassword = (EditText) findViewById(R.id.etPassword);
            //    etRPassword = (EditText) findViewById(R.id.etRPassword);

                ConsultarBarrio(usuario.getBarrio().getIdBarrios());

                if (usuario.getGenero().equals("M")) {

                    etGenero.setText("Masculino");


                } else {

                    etGenero.setText("Femenino");
                }


            }

            @Override
            public void failure(RetrofitError retrofitError) {
                //     acceso = 0;
                resultadoTextView.setText("Error: " + retrofitError.getMessage());
            }
        });

    }

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


    private void ConsultarBarrio(int idBarrio) {

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarBarrio servicio = restAdapter.create(ConsultarBarrio.class);

        servicio.consultarBarrio(idBarrio,"", new Callback<Barrio_TO>() {
            @Override
            public void success(Barrio_TO barrio, Response response) {

                tvBarrio.setText("Barrio actual: "+barrio.getNombre());

            }

            @Override
            public void failure(RetrofitError retrofitError) {

            }
        });

    } ;

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

        if (id == R.id.consultar_pedidos_asesor) {

            startActivity(new Intent(this, ConsultarPedidosAsesor.class));
        }

        if (id == R.id.consultar_pedidos_entrega_asesor) {

            startActivity(new Intent(this, ConsultarPedidosAsesorEntrega.class));
        }

        if (id == R.id.consultar_pedidos) {

            startActivity(new Intent(this, ConsultarPedido.class));
        }

        if (id == R.id.editar_perfil) {

            startActivity(new Intent(this, EditarPerfil.class));
        }

        if (userLocalStore.getUserLoggedIn() == true) {
            if(userLocalStore.getLoggedInUser().getRol().getIdRol()==3){
                if (id == R.id.inicio) {

                    startActivity(new Intent(this, AsesorInicio.class));
                }
            }else{
                if(userLocalStore.getLoggedInUser().getRol().getIdRol()==4){
                    if (id == R.id.inicio) {

                        startActivity(new Intent(this, Inicio.class));
                    }
                }
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
