package co.com.planit.lavapp.client;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.Login;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.UserLocalStore;

public class Pago extends AppCompatActivity implements View.OnClickListener {

    UserLocalStore userLocalStore;

    List<String> tipoPago = new ArrayList<String>();
    List<String> tipoTarjeta = new ArrayList<String>();

    EditText etName, etApellido, etTelefono, etMovil, etNTarjeta, etFecha, etCodigo;
    Spinner spPago, spTarjeta;
    Button btnBuscarFecha, bAceptar;

    private int mMes, mAnio, mDia, sMes, sAnio, sDia;
    static final int DATE_ID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pago);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        userLocalStore = new UserLocalStore(this);

        etName = (EditText) findViewById(R.id.etName);
        etApellido = (EditText) findViewById(R.id.etApellido);
        etTelefono = (EditText) findViewById(R.id.etTelefono);
        etMovil = (EditText) findViewById(R.id.etMovil);
//        etNTarjeta = (EditText) findViewById(R.id.etNTarjeta);
//        etFecha = (EditText) findViewById(R.id.etFecha);
//        etCodigo = (EditText) findViewById(R.id.etCodigo);
        spPago = (Spinner) findViewById(R.id.spPago);
//        spTarjeta = (Spinner) findViewById(R.id.spTarjeta);
//        btnBuscarFecha = (Button) findViewById(R.id.btnBuscarFecha);
        bAceptar = (Button) findViewById(R.id.bAceptar);

//        btnBuscarFecha.setOnClickListener(this);
        bAceptar.setOnClickListener(this);

//        Calendar C = Calendar.getInstance();
//        sAnio = C.get(Calendar.YEAR);
//        sMes = C.get(Calendar.MONTH);
//        sDia = C.get(Calendar.DAY_OF_MONTH);

//        etFecha.setText(new StringBuilder().append(sAnio) + "-" + new StringBuilder().append(sMes + 1) + "-" + new StringBuilder().append(sDia));

        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,
                R.array.tipoPago, android.R.layout.simple_spinner_item);

        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spPago.setAdapter(adapter1);

        spPago.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()

        {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {

                String strServicio = (adapterView.getItemAtPosition(position)).toString();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //nothing
            }
        });

//        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,
//                R.array.tipoTarjeta, android.R.layout.simple_spinner_item);
//
//        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//
//        spTarjeta.setAdapter(adapter2);
//
//        spTarjeta.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
//
//        {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
//
//                String strServicio = (adapterView.getItemAtPosition(position)).toString();
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//                //nothing
//            }
//        });

        ValidarSesionIniciada();

    }

    private void ValidarSesionIniciada() {

        if (userLocalStore.getUserLoggedIn() == true) {

            etName.setText(userLocalStore.getLoggedInUser().getNombre());
            etApellido.setText(userLocalStore.getLoggedInUser().getApellido());
            etTelefono.setText(userLocalStore.getLoggedInUser().getTelefono());
            etMovil.setText(userLocalStore.getLoggedInUser().getMovil());

        }
    }

    private void AsignarFecha() {
        etFecha.setText(new StringBuilder().append(mAnio) + "-" + new StringBuilder().append(mMes + 1) + "-" + new StringBuilder().append(mDia));

    }

    private DatePickerDialog.OnDateSetListener mDateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    mAnio = year;
                    mMes = monthOfYear;
                    mDia = dayOfMonth;
                    AsignarFecha();
                }
            };

    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_ID:
                return new DatePickerDialog(this, mDateSetListener, sAnio, sMes, sDia);
        }
        return null;
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBuscarFecha:

                showDialog(DATE_ID);
                break;

            case R.id.bAceptar:

                startActivity(new Intent(this, ConsultarPedido.class));

                break;
        }
    }
}
