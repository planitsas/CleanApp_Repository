package co.com.planit.lavapp.Adviser;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.graphics.Matrix;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.okhttp.OkHttpClient;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.EditarPerfil;
import co.com.planit.lavapp.common.MainActivity;
import co.com.planit.lavapp.config.AsesorLocalStore;
import co.com.planit.lavapp.config.UserLocalStore;
import co.com.planit.lavapp.models.Color_TO;
import co.com.planit.lavapp.models.DescripcionPedido_TO;
import co.com.planit.lavapp.models.Estado_TO;
import co.com.planit.lavapp.service.ConsultarColores;
import co.com.planit.lavapp.service.ConsultarDescripcionPedidoSegunProducto;
import co.com.planit.lavapp.service.ConsultarEstadosProducto;
import co.com.planit.lavapp.service.EditarDescripcionPedidoAsesor;
import co.com.planit.lavapp.service.ServerRequests;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class ConsultarProductoAsesor extends AppCompatActivity implements View.OnClickListener {

    ServerRequests serverRequests = new ServerRequests();
    String ruta = serverRequests.BuscarRuta();

    private ImageView imagen1, imagen2, imagen3;
    Button bFoto1, bFoto2, bFoto3, bGuardar, bScan;

    List<String> color = new ArrayList<String>();
    List<String> estado = new ArrayList<String>();

    TextView tvEstado, tvfoto1, tvfoto2, tvfoto3;
    EditText etName, etObservacion;
    Spinner etColor, etEstado;

    private TextView formatTxt, contentTxt;

    UserLocalStore userLocalStore;
    AsesorLocalStore asesorLocalStore;

    Calendar C = Calendar.getInstance();
    int sAnio = C.get(Calendar.YEAR);
    int sMes = C.get(Calendar.MONTH);

    int sDia = C.get(Calendar.DAY_OF_MONTH);
    String nombreFoto1 = "", nombreFoto2 = "", nombreFoto3 = "", fotoB1 = "", fotoB2 = "", fotoB3 = "", encoded_string = "";
    Bitmap bitmap1, bitmap2, bitmap3;

    int idDescPedido;

    File foto, foto2, foto3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_producto_asesor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        sMes = sMes + 1;
        idDescPedido = getIntent().getExtras().getInt("idDescPedido");

        ConsultarProducto(idDescPedido);

        userLocalStore = new UserLocalStore(this);

        asesorLocalStore = new AsesorLocalStore(this);

        imagen1 = (ImageView) findViewById(R.id.imageView);
        imagen2 = (ImageView) findViewById(R.id.imageView2);
        imagen3 = (ImageView) findViewById(R.id.imageView3);
        etColor = (Spinner) findViewById(R.id.etColor);
        etEstado = (Spinner) findViewById(R.id.etEstado);
        tvEstado = (TextView) findViewById(R.id.tvEstado);
        tvfoto1 = (TextView) findViewById(R.id.tvfoto1);
        tvfoto2 = (TextView) findViewById(R.id.tvfoto2);
        tvfoto3 = (TextView) findViewById(R.id.tvfoto3);
        etObservacion = (EditText) findViewById(R.id.etObservacion);


        bFoto1 = (Button) findViewById(R.id.bFoto1);
        bFoto2 = (Button) findViewById(R.id.bFoto2);
        bFoto3 = (Button) findViewById(R.id.bFoto3);
        bGuardar = (Button) findViewById(R.id.bGuardar);

        bScan = (Button) findViewById(R.id.bScan);
        formatTxt = (TextView) findViewById(R.id.scan_format);
        contentTxt = (TextView) findViewById(R.id.scan_content);

        etName = (EditText) findViewById(R.id.etName);

        bFoto1.setOnClickListener(this);
        bFoto2.setOnClickListener(this);
        bFoto3.setOnClickListener(this);
        bGuardar.setOnClickListener(this);
        bScan.setOnClickListener(this);

        ConsultarColores();
        ConsultarEstados();
//        RellenarInformacion();
        // Rellenar informacion de lo que esta en el asesorLocalStore;

//        etObservacion.setText(idDescPedido + "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFoto1:

                tomarFoto(v, 1);

                break;

            case R.id.bFoto2:

                tomarFoto(v, 2);

                break;

            case R.id.bFoto3:

                tomarFoto(v, 3);

                break;

            case R.id.bGuardar:

                EditarDescripcionPedidoAsesor(v);

                //   Picasso.with(this).load("http://jonsegador.com/wp-content/imagen_externa_android-199x300.png").into(imagen1);
                //    Picasso.with(this).load("http://servidor.com/imagen.jpg").error(R.drawable.error).fit().transform(new RoundedTransformation()).into(imageView);
                break;

            case R.id.bScan:

                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
    }


    public void tomarFoto(final View v, final int valor) {


        if (valor == 1) {
            nombreFoto1 = "Foto" + valor + idDescPedido + userLocalStore.getLoggedInUser().getIdUsuario() + sAnio + sMes + sDia + valor + ".jpg";
            Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            foto = new File(getExternalFilesDir(null), nombreFoto1);
            intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
            startActivity(intento1);

//            recuperarFoto(v, foto, valor);
//            startActivityForResult(intento1, 1);

            asesorLocalStore.setAsesorFoto1(fotoB1, nombreFoto1);
//
//            if(bitmap1 != null) {
            tvfoto1.setText("Foto 1: " + nombreFoto1);
//            }

        }
        if (valor == 2) {
            nombreFoto2 = "Foto" + valor + idDescPedido + userLocalStore.getLoggedInUser().getIdUsuario() + sAnio + sMes + sDia + valor + ".jpg";
            Intent intento2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            foto2 = new File(getExternalFilesDir(null), nombreFoto2);
            intento2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto2));
            startActivity(intento2);
//            recuperarFoto(v, foto2, valor);

//            fotoB2 = BitmapToString(bitmap2);

            asesorLocalStore.setAsesorFoto2(fotoB2, nombreFoto2);

//            if(bitmap2 != null) {
            tvfoto2.setText("Foto 2: " + nombreFoto2);
//            }
        }
        if (valor == 3) {

            nombreFoto3 = "Foto" + valor + idDescPedido + userLocalStore.getLoggedInUser().getIdUsuario() + sAnio + sMes + sDia + valor + ".jpg";
            Intent intento3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            foto3 = new File(getExternalFilesDir(null), nombreFoto3);
            intento3.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto3));
            startActivity(intento3);
//            recuperarFoto(v, foto3, valor);


//            fotoB3 = BitmapToString(bitmap3);

            asesorLocalStore.setAsesorFoto3(fotoB3, nombreFoto3);
//
//            if(bitmap3 != null) {
            tvfoto3.setText("Foto 3: " + nombreFoto3);
//            }
        }

    }

    public void recuperarFoto(final View v, final File path, final int valor) {
//
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        new AlertDialog.Builder(ConsultarProductoAsesor.this)
//                .setTitle("Información")
//                .setMessage("Captura de fotos Lavaapp")
//                .setIcon(android.R.drawable.ic_dialog_alert)
//                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
//
//                            public void onClick(DialogInterface dialog, int whichButton) {

        if (valor == 1)

        {
            bitmap1 = BitmapFactory.decodeFile(path.getAbsolutePath());
            bitmap1 = redimensionarImagenMaximo(bitmap1, 653, 490);

            fotoB1 = BitmapToString(bitmap1);
//            Log.i("Gustavo2 ", path.getAbsolutePath());
//            if (bitmap1 != null) {
//                Log.i("no null-- ", "");
//                imagen1.setImageBitmap(bitmap1);
//            }
//            new Encode_image().execute();


//            bitmap1 = BitmapFactory.decodeFile(foto.getAbsolutePath());
//            bitmap1 = redimensionarImagenMaximo(bitmap1, 653, 490);
//            ByteArrayOutputStream stream = new ByteArrayOutputStream();
//            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
//            bitmap1.recycle();
//
//            byte[] array = stream.toByteArray();
//            encoded_string = Base64.encodeToString(array, 0);
//
//            makeRequest();
        }

        if (valor == 2)

        {
            bitmap2 = BitmapFactory.decodeFile(path.getAbsolutePath());
            bitmap2 = redimensionarImagenMaximo(bitmap2, 653, 490);

            fotoB2 = BitmapToString(bitmap2);
//            Log.i("Gustavo2 ", path.getAbsolutePath());
//            if (imagen2 != null) {
//                Log.i("no null-- ", "");
//                imagen2.setImageBitmap(bitmap2);
//            }
        }

        if (valor == 3)

        {
            bitmap3 = BitmapFactory.decodeFile(path.getAbsolutePath());
            bitmap3 = redimensionarImagenMaximo(bitmap3, 653, 490);

            fotoB3 = BitmapToString(bitmap3);
//            Log.i("Gustavo2 ", path.getAbsolutePath());
//            if (imagen3 != null) {
//                Log.i("no null-- ", "");
//                imagen3.setImageBitmap(bitmap3);
//            }
        }
    }
//                        }
//                )
//                .
//
//                        setNegativeButton(android.R.string.no, null)
//
//                .
//
//                        show();


    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        //Scanner
        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();
            String scanFormat = scanningResult.getFormatName();

            formatTxt.setText(scanFormat);
            contentTxt.setText(scanContent);

        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }
        //


//            if(bitmap1 != null) {
//            recuperarFoto(v, foto, valor);
//
//            fotoB1 = BitmapToByteString(bitmap1);
//
//            asesorLocalStore.setAsesorFoto1(fotoB1, nombreFoto1);
//            tvfoto1.setText("Foto 1: " + nombreFoto1);
//        }


    }

    private class Encode_image extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... voids) {

            bitmap1 = BitmapFactory.decodeFile(foto.getAbsolutePath());
            bitmap1 = redimensionarImagenMaximo(bitmap1, 653, 490);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap1.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            bitmap1.recycle();

            byte[] array = stream.toByteArray();
            encoded_string = Base64.encodeToString(array, 0);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            makeRequest();
        }
    }

    private void makeRequest() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest request = new StringRequest(Request.Method.POST, "http://190.146.144.78/LavaApp/lavapp/phpws.php",
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> map = new HashMap<>();
                map.put("encoded_string", encoded_string);
                map.put("image_name", nombreFoto1);

                return map;
            }
        };
        requestQueue.add(request);
    }

    private void ConsultarProducto(int idDescripcionPedido) {

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarDescripcionPedidoSegunProducto servicio = restAdapter.create(ConsultarDescripcionPedidoSegunProducto.class);

        servicio.consultarDescripcionPedidoSegunProducto(idDescripcionPedido, new Callback<DescripcionPedido_TO>() {
            @Override
            public void success(DescripcionPedido_TO producto, Response response) {

                etName.setText(producto.getSubProducto().getNombre());
                tvEstado.setText("Estado Actual: " + producto.getEstado().getNombre());


            }

            @Override
            public void failure(RetrofitError retrofitError) {

                Log.i("Error:", "error " + retrofitError);
            }
        });

    }

    private void EditarDescripcionPedidoAsesor(final View v) {

        int idColor = 0, idEstado = 0;

        if (!(etColor.getSelectedItem().toString().equals("Seleccione"))) {
            String[] Color = etColor.getSelectedItem().toString().split(" - ");

            idColor = Integer.parseInt(Color[0].toString());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Debe seleccionar un color!", Toast.LENGTH_SHORT);
            toast.show();

        }
        if (!(etEstado.getSelectedItem().toString().equals("Seleccione"))) {
            String[] Estado = etEstado.getSelectedItem().toString().split(" - ");

            idEstado = Integer.parseInt(Estado[0].toString());
        } else {
            Toast toast = Toast.makeText(getApplicationContext(),
                    "Debe seleccionar un Estado!", Toast.LENGTH_SHORT);
            toast.show();

        }

        String observacionAsesor = etObservacion.getText().toString();

        String codigo = contentTxt.getText().toString();

        if (!(nombreFoto1.equals(""))) {
            recuperarFoto(v, foto, 1);
        }

        if (!(nombreFoto2.equals(""))) {
            recuperarFoto(v, foto2, 2);
        }
        if (!(nombreFoto3.equals(""))) {
            recuperarFoto(v, foto3, 3);
        }

//        Log.i("Editar--: ", idColor + "-----" + idEstado + "------" + observacionAsesor + "------" + idColor + "------" + codigo + "------" + nombreFoto1 + "------" + nombreFoto2 + "------" + nombreFoto3 + "------" + fotoB1 + "------" + fotoB2 + "------" + fotoB3 + "------");

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();
        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        EditarDescripcionPedidoAsesor servicio = restAdapter.create(EditarDescripcionPedidoAsesor.class);

        servicio.editarDescripcionPedidoAsesor(idDescPedido, idEstado, observacionAsesor, idColor, fotoB1, fotoB2,
                fotoB3, codigo, nombreFoto1, nombreFoto2, nombreFoto3, new Callback<String>() {
                    @Override
                    public void success(String producto, Response response) {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Registrado satisfactoriamente!", Toast.LENGTH_SHORT);
                        toast.show();

                        startActivity(new Intent(ConsultarProductoAsesor.this, ConsultarDescripPedidoAsesorEntrega.class));


                    }

                    @Override
                    public void failure(RetrofitError retrofitError) {

                        Log.i("error", "error:" + retrofitError);
                    }
                });
    }

    public static String BitmapToString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            bitmap.recycle();
            byte[] b = baos.toByteArray();
            Log.i("binarios: ", b + "");
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (NullPointerException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

//    public static String BitmapToByteString(Bitmap bitmap) {
//        try {
//            ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmap.getByteCount());
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
//            byte[] images = baos.toByteArray();
//
//            String encodedString = new String(images);
////            Log.i("String----",encodedString );
//            return encodedString;
//        } catch (NullPointerException e) {
//            return null;
//        } catch (OutOfMemoryError e) {
//            return null;
//        }
//    }

    public String getBase64ImageString(Bitmap photo) {
        String imgString;
        if (photo != null) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
            byte[] profileImage = outputStream.toByteArray();

            imgString = Base64.encodeToString(profileImage,
                    Base64.NO_WRAP);
        } else {
            imgString = "";
        }

        return imgString;
    }

    /**
     * Redimensionar un Bitmap. By TutorialAndroid.com
     *
     * @return Bitmap
     */
    public static Bitmap redimensionarImagenMaximo(Bitmap mBitmap, float newWidth, float newHeigth) {
        //Redimensionamos
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeigth) / height;
        // create a matrix for the manipulation
        Matrix matrix = new Matrix();
        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);
        // recreate the new Bitmap
        return Bitmap.createBitmap(mBitmap, 0, 0, width, height, matrix, false);
    }


    private void ConsultarColores() {

        color.clear();

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarColores servicio = restAdapter.create(ConsultarColores.class);

        servicio.consultarColores(new Callback<List<Color_TO>>() {
            @Override
            public void success(List<Color_TO> colores, Response response) {

                if (colores.size() != 0 && colores != null) {
                    color.add("Seleccione");
                    for (int i = 0; i < colores.size(); i++) {
                        color.add(colores.get(i).getIdColor() + " - " + colores.get(i).getNombre());
                    }
                    LlenarColores();
                } else {
                    color.clear();
                    color.add("Seleccione");

                    LlenarColores();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {

                color.clear();
                color.add("Seleccione");

                LlenarColores();

                Log.i("mensaje: ", retrofitError.getMessage());

            }
        });

    }

    ;

    private void LlenarColores() {

        Log.i("Gustavo", "llenar Colores: " + color);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, color);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etColor.setAdapter(adapter1);
    }

    private void RellenarInformacion() {

        tvEstado.setText("Estado Actual: " + asesorLocalStore.getLoggedInAsesor().getEstado().getNombre());

    }

    private void ConsultarEstados() {

        estado.clear();

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        ConsultarEstadosProducto servicio = restAdapter.create(ConsultarEstadosProducto.class);

        servicio.consultarEstadosProducto(new Callback<List<Estado_TO>>() {
            @Override
            public void success(List<Estado_TO> estados, Response response) {

                if (estados.size() != 0 && estados != null) {
                    estado.add("Seleccione");
                    for (int i = 0; i < estados.size(); i++) {
                        estado.add(estados.get(i).getIdEstado() + " - " + estados.get(i).getNombre());
                    }
                    LlenarEstados();
                } else {
                    estado.clear();
                    estado.add("Seleccione");

                    LlenarEstados();
                }

            }

            @Override
            public void failure(RetrofitError retrofitError) {

                estado.clear();
                estado.add("Seleccione");

                LlenarEstados();

                Log.i("mensaje: ", retrofitError.getMessage());

            }
        });

    }

    ;

    private void LlenarEstados() {

        Log.i("Gustavo", "llenar Estados: " + estado);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, estado);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        etEstado.setAdapter(adapter2);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        if (userLocalStore.getUserLoggedIn() == true) {
            if (userLocalStore.getLoggedInUser().getRol().getIdRol() == 3) {
                getMenuInflater().inflate(R.menu.menu_asesor, menu);
            } else {
                if (userLocalStore.getLoggedInUser().getRol().getIdRol() == 4) {
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
