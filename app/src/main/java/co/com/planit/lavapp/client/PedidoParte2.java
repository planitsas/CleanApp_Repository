package co.com.planit.lavapp.client;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.sax.StartElementListener;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.Barrio_TO;
import co.com.planit.lavapp.models.Ciudad_TO;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Horario_TO;
import co.com.planit.lavapp.models.Pedido_TO;
import co.com.planit.lavapp.service.ConsultarBarrios;
import co.com.planit.lavapp.service.ConsultarCiudades;
import co.com.planit.lavapp.service.ConsultarHorarios;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class PedidoParte2 extends AppCompatActivity implements View.OnClickListener {

    UserLocalStore userLocalStore;

    EditText etFecha, etDireccion, etEntrega, etFecha2, etDireccion2, etRecibir;
    Button btnBuscarFecha, btnBuscarFecha2, btContinuar;
    Spinner spRangoHoras, spRangoHoras2;
    AutoCompleteTextView acBarrio, acBarrio2;

    List<String> barrio = new ArrayList<String>();
    List<String> horario = new ArrayList<String>();
    Pedido_TO pedido = new Pedido_TO();
    List<DescripcionPedido_TO> productos = new ArrayList<DescripcionPedido_TO>();

    private int mMes, mAnio, mDia, sMes, sAnio, sDia;
    static final int DATE_ID = 0;
    static int vFecha = 0;

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pedido_parte2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        pedido = (Pedido_TO) getIntent().getExtras().getSerializable("pedido");
        productos = (List<DescripcionPedido_TO>) getIntent().getExtras().getSerializable("productos");

        etFecha = (EditText) findViewById(R.id.etFecha);
        etDireccion = (EditText) findViewById(R.id.etDireccion);
        etEntrega = (EditText) findViewById(R.id.etEntrega);
        etFecha2 = (EditText) findViewById(R.id.etFecha2);
        etDireccion2 = (EditText) findViewById(R.id.etDireccion2);
        etRecibir = (EditText) findViewById(R.id.etRecibir);
        btnBuscarFecha = (Button) findViewById(R.id.btnBuscarFecha);
        btnBuscarFecha2 = (Button) findViewById(R.id.btnBuscarFecha2);
        btContinuar = (Button) findViewById(R.id.btContinuar);
        spRangoHoras = (Spinner) findViewById(R.id.spRangoHoras);
        acBarrio = (AutoCompleteTextView) findViewById(R.id.acBarrio);
        spRangoHoras2 = (Spinner) findViewById(R.id.spRangoHoras2);
        acBarrio2 = (AutoCompleteTextView) findViewById(R.id.acBarrio2);

        btnBuscarFecha.setOnClickListener(this);
        btnBuscarFecha2.setOnClickListener(this);
        btContinuar.setOnClickListener(this);

        Calendar C = Calendar.getInstance();
        sAnio = C.get(Calendar.YEAR);
        sMes = C.get(Calendar.MONTH);
        sDia = C.get(Calendar.DAY_OF_MONTH);

        etFecha.setText(new StringBuilder().append(sAnio) + "-" + new StringBuilder().append(sMes + 1) + "-" + new StringBuilder().append(sDia));

        etFecha2.setText(new StringBuilder().append(sAnio) + "-" + new StringBuilder().append(sMes + 1) + "-" + new StringBuilder().append(sDia + 1));

        pedido.setFechaInicioString(etFecha.getText().toString());

        this.ConsultarBarrios();

        this.ConsultarHorarios();

        spRangoHoras.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

                if (!strServicio.equals("Seleccione")) {

                    String[] Hora = strServicio.split(" - ");

                    int idHora = Integer.parseInt(Hora[0].toString());
                    String HoraInicio = Hora[1].toString();

                    pedido.setHoraInicio(new Horario_TO(idHora, HoraInicio, ""));

                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });

        spRangoHoras2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

                if (!strServicio.equals("Seleccione")) {

                    String[] Hora2 = strServicio.split(" - ");

                    int idHora = Integer.parseInt(Hora2[0].toString());

                    pedido.setHoraFinal(new Horario_TO(idHora));

                    String HoraFinal = Hora2[1].toString();

                    pedido.setHoraFinal(new Horario_TO(idHora, "", HoraFinal));
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });

        if (userLocalStore.getUserLoggedIn() == true) {

            this.AgregarInformacion();
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscarFecha:

                vFecha = 0;
                showDialog(DATE_ID);
                break;

            case R.id.btnBuscarFecha2:

                vFecha = 1;
                showDialog(DATE_ID);
                break;

            case R.id.btContinuar:

                int idBarrio = 0;

                AsignarFechaEntrega();
                pedido.setDireccionEntrega(etDireccion.getText().toString());
                pedido.setDireccionRecogida(etDireccion2.getText().toString());
                pedido.setQuienEntrega(etEntrega.getText().toString());
                pedido.setQuienRecibe(etRecibir.getText().toString());

                if (!etDireccion.getText().toString().equals("") && !etEntrega.getText().toString().equals("") && !etDireccion2.getText().toString().equals("") && !etRecibir.getText().toString().equals("")) {

                    if (!spRangoHoras.getSelectedItem().toString().equals("Seleccione")) {

                        if (!spRangoHoras2.getSelectedItem().toString().equals("Seleccione")) {

                            if (!acBarrio.getText().toString().equals("")) {

                                if (acBarrio.getText().toString().indexOf(" - ") > 0) {
                                    String[] Barrio = acBarrio.getText().toString().split(" - ");

                                    Log.i("Barrio: ", "------------------- " + Barrio.length);
                                    idBarrio = Integer.parseInt(Barrio[0].toString());

                                    pedido.setIdBarrioEntrega(new Barrio_TO(idBarrio));


                                    if (!acBarrio2.getText().toString().equals("")) {

                                        if (acBarrio2.getText().toString().indexOf(" - ") > 0) {
                                            String[] Barrio2 = acBarrio2.getText().toString().split(" - ");

                                            int idBarrio2 = Integer.parseInt(Barrio2[0].toString());

                                            pedido.setIdBarrioRecogida(new Barrio_TO(idBarrio2));


                                            String[] fechaReg = etFecha2.getText().toString().split("-");
                                            String[] fechaEnt = etFecha.getText().toString().split("-");

                                            int anioE = Integer.parseInt(fechaEnt[0].toString());
                                            int mesE = Integer.parseInt(fechaEnt[1].toString());
                                            int diaE = Integer.parseInt(fechaEnt[2].toString());

                                            int anioR = Integer.parseInt(fechaReg[0].toString());
                                            int mesR = Integer.parseInt(fechaReg[1].toString());
                                            int diaR = Integer.parseInt(fechaReg[2].toString());

                                            if (anioE <= anioR) {

                                                if (mesE <= mesR) {
                                                    if (diaE < diaR) {
                                                        Intent intent = new Intent(PedidoParte2.this, DetalleDeOrden.class);
                                                        intent.putExtra("pedido", pedido);
                                                        intent.putExtra("productos", (Serializable) productos);
                                                        startActivity(intent);
                                                    } else {
                                                        Toast toast1 =
                                                                Toast.makeText(this, "El día para recibir el pedido no puede ser igual o menor a la de entrega", Toast.LENGTH_LONG);

                                                        toast1.show();
                                                    }
                                                } else {
                                                    Toast toast1 =
                                                            Toast.makeText(this, "El mes para recibir el pedido no puede ser menor a la de entrega", Toast.LENGTH_LONG);

                                                    toast1.show();
                                                }
                                            } else {
                                                Toast toast1 =
                                                        Toast.makeText(this, "La año para recibir el pedido no puede ser menor a la de entrega", Toast.LENGTH_LONG);

                                                toast1.show();
                                            }


                                        } else {
                                            Toast toast1 =
                                                    Toast.makeText(this, "El barrio para Recibir no se ha seleccionado de manera correcta", Toast.LENGTH_LONG);

                                            toast1.show();
                                        }

                                    } else {
                                        Toast toast1 =
                                                Toast.makeText(this, "Debe Seleccionar un Barrio para Recibir", Toast.LENGTH_LONG);

                                        toast1.show();
                                    }


                                } else {
                                    Toast toast1 =
                                            Toast.makeText(this, "El barrio de entrega no se ha seleccionado de manera correcta", Toast.LENGTH_LONG);

                                    toast1.show();
                                }

                            } else {
                                Toast toast1 =
                                        Toast.makeText(this, "Debe Seleccionar un Barrio para la entrega", Toast.LENGTH_LONG);

                                toast1.show();
                            }

                        } else {
                            Toast toast1 =
                                    Toast.makeText(this, "Debe Seleccionar un horario para Recibir", Toast.LENGTH_LONG);

                            toast1.show();
                        }

                    } else {
                        Toast toast1 =
                                Toast.makeText(this, "Debe Seleccionar un horario para la entrega", Toast.LENGTH_LONG);

                        toast1.show();
                    }

                } else {
                    Toast toast1 =
                            Toast.makeText(this, "No puede tener campos Vacios", Toast.LENGTH_LONG);

                    toast1.show();
                }
                break;


        }

    }

    private void AsignarFecha() {
        etFecha.setText(new StringBuilder().append(mAnio) + "-" + new StringBuilder().append(mMes + 1) + "-" + new StringBuilder().append(mDia));
        etFecha2.setText(new StringBuilder().append(mAnio) + "-" + new StringBuilder().append(mMes + 1) + "-" + new StringBuilder().append(mDia + 1));

        AsignarFechaEntrega();

    }

    private void AsignarFecha2() {
        etFecha2.setText(new StringBuilder().append(mAnio) + "-" + new StringBuilder().append(mMes + 1) + "-" + new StringBuilder().append(mDia));

        AsignarFechaRecogida();
    }

    private void AsignarFechaEntrega() {

        pedido.setFechaEntregaString(etFecha.getText().toString());
        pedido.setFechaRecogidaString(etFecha2.getText().toString());

    }

    private void AsignarFechaRecogida() {

        pedido.setFechaRecogidaString(etFecha2.getText().toString());

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mAnio = year;
                    mMes = monthOfYear;
                    mDia = dayOfMonth;

                    if (vFecha == 0) {
                        AsignarFecha();
                    } else {
                        AsignarFecha2();
                    }

                }
            };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sAnio, sMes, sDia);

        }
        return null;
    }

    private void ConsultarBarrios() {


        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarBarrios servicio = restAdapter.create(ConsultarBarrios.class);

        servicio.consultarBarrios(new Callback<List<Barrio_TO>>() {
            @Override
            public void success(List<Barrio_TO> barrios, Response response) {

                barrio.add("Seleccione");

                for (int i = 0; i < barrios.size(); i++) {
                    barrio.add(barrios.get(i).getIdBarrios() + " - " + barrios.get(i).getNombre().toString());
                }

                LlenarBarrios();

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("mensaje: ", retrofitError.getMessage());

            }
        });

    }

    private void LlenarBarrios() {

        acBarrio.setEnabled(true);

        acBarrio.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, barrio));

        acBarrio2.setEnabled(true);

        acBarrio2.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, barrio));
    }

    public void onTextChanged(CharSequence s, int start, int before,
                              int count) {
        //  selection.setText(edit.getText());
    }

    public void beforeTextChanged(CharSequence s, int start,
                                  int count, int after) {
        //
    }

    public void afterTextChanged(Editable s) {

    }

    private void ConsultarHorarios() {


        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarHorarios servicio = restAdapter.create(ConsultarHorarios.class);

        servicio.consultarHorarios(new Callback<List<Horario_TO>>() {
            @Override
            public void success(List<Horario_TO> horarios, Response response) {

                horario.add("Seleccione");

                for (int i = 0; i < horarios.size(); i++) {
                    horario.add(horarios.get(i).getIdHorario() + " - " + horarios.get(i).getHorario().toString());
                }

                LlenarHorarios();

            }

            @Override
            public void failure(RetrofitError retrofitError) {
                Log.i("mensaje: ", retrofitError.getMessage());

            }
        });

    }

    private void LlenarHorarios() {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, horario);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spRangoHoras.setAdapter(adapter);
        spRangoHoras2.setAdapter(adapter);

    }

    private void AgregarInformacion() {


        etEntrega.setText(userLocalStore.getLoggedInUser().getNombre());
        etRecibir.setText(userLocalStore.getLoggedInUser().getNombre());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (userLocalStore.getUserLoggedIn() == true) {
            getMenuInflater().inflate(R.menu.menu_main, menu);
        } else {
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
