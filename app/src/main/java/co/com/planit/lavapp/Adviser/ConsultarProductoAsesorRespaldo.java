package co.com.planit.lavapp.Adviser;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
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

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

public class ConsultarProductoAsesorRespaldo extends AppCompatActivity implements View.OnClickListener {

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
    String nombreFoto1 = "", nombreFoto2 = "", nombreFoto3 = "", fotoB1 = "", fotoB2 = "", fotoB3 = "";
    Bitmap bitmap1, bitmap2, bitmap3;

    int idDescPedido;

    private String SERVER_URL = "http://190.146.144.78/LavaApp/lavapp/upload.php";

    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consultar_producto_asesor);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
        RellenarInformacion();

        etObservacion.setText(idDescPedido + "");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bFoto1:

                try {
                    tomarFoto(v, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.bFoto2:

                try {
                    tomarFoto(v, 2);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.bFoto3:

                try {
                    tomarFoto(v, 3);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case R.id.bGuardar:

                EditarDescripcionPedidoAsesor();

                break;

            case R.id.bScan:

                IntentIntegrator scanIntegrator = new IntentIntegrator(this);
                scanIntegrator.initiateScan();
                break;
        }
    }


    public void tomarFoto(final View v, final int valor) throws IOException {


        if (valor == 1) {
            nombreFoto1 = "Foto" + valor + idDescPedido + userLocalStore.getLoggedInUser().getIdUsuario() + sAnio + sMes + sDia + valor + ".jpg";
            Intent intento1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File foto = new File(getExternalFilesDir(null), nombreFoto1);
            intento1.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto));
//            startActivity(intento1);
            startActivityForResult(intento1, 1);
            recuperarFoto(v, foto, valor);

//            fotoB1 = BitmapToByteString(bitmap1);
//            uploadFoto(foto,nombreFoto1 );
//            asesorLocalStore.setAsesorFoto1(fotoB1, nombreFoto1);

            uploadFile(foto.getAbsolutePath());

            if(bitmap1 != null) {
                tvfoto1.setText("Foto 1: " + nombreFoto1);
            }


        }

        if (valor == 2) {
            nombreFoto2 = "Foto" + valor + idDescPedido + userLocalStore.getLoggedInUser().getIdUsuario() + sAnio + sMes + sDia + valor + ".jpg";
            Intent intento2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File foto2 = new File(getExternalFilesDir(null), nombreFoto2);
            intento2.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto2));
            startActivity(intento2);
            recuperarFoto(v, foto2, valor);

            fotoB2 = BitmapToString(bitmap2);

            asesorLocalStore.setAsesorFoto2(fotoB2, nombreFoto2);

            if(bitmap2 != null) {
                tvfoto2.setText("Foto 2: " + nombreFoto2);
            }
        }
        if (valor == 3) {

            nombreFoto3 = "Foto" + valor + idDescPedido + userLocalStore.getLoggedInUser().getIdUsuario() + sAnio + sMes + sDia + valor + ".jpg";
            Intent intento3 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            File foto3 = new File(getExternalFilesDir(null), nombreFoto3);
            intento3.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(foto3));
            startActivity(intento3);
            recuperarFoto(v, foto3, valor);


            fotoB3 = BitmapToString(bitmap3);

            asesorLocalStore.setAsesorFoto3(fotoB3, nombreFoto3);

            if(bitmap3 != null) {
                tvfoto3.setText("Foto 3: " + nombreFoto3);
            }
        }

    }

    public void recuperarFoto(final View v, final File path, final int valor) {

        if (valor == 1)

        {
            bitmap1 = BitmapFactory.decodeFile(path.getAbsolutePath());
            bitmap1 = redimensionarImagenMaximo(bitmap1, 653, 490);
//            Log.i("Gustavo2 ", path.getAbsolutePath());
//            if (bitmap1 != null) {
//                Log.i("no null-- ", "");
//                imagen1.setImageBitmap(bitmap1);
//            }
        }

        if (valor == 2)

        {
            bitmap2 = BitmapFactory.decodeFile(path.getAbsolutePath());
            bitmap2 = redimensionarImagenMaximo(bitmap2, 653, 490);
        }

        if (valor == 3)

        {
            bitmap3 = BitmapFactory.decodeFile(path.getAbsolutePath());
            bitmap3 = redimensionarImagenMaximo(bitmap3, 653, 490);

        }
    }

//    private void uploadFoto(File file, String imag) throws IOException {
//        HttpClient httpclient = new DefaultHttpClient();
//        httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
//        HttpPost httppost = new HttpPost("http://190.146.144.78/LavaApp/lavapp/upload.php");
//        MultipartEntity mpEntity = new MultipartEntity();
//        ContentBody foto = new FileBody(file, "image/jpeg");
//        mpEntity.addPart("fotoUp", foto);
//        httppost.setEntity(mpEntity);
//            httpclient.execute(httppost);
//            httpclient.getConnectionManager().shutdown();
//    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

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

                Log.i("Error:", "error "+retrofitError);
            }
        });

    }

    private void EditarDescripcionPedidoAsesor() {

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

        Log.i("Editar--: ", idColor + "-----" + idEstado + "------" + observacionAsesor + "------" + idColor + "------" + codigo + "------" + nombreFoto1 + "------" + nombreFoto2 + "------" + nombreFoto3 + "------" + fotoB1 + "------" + fotoB2 + "------" + fotoB3 + "------");

        final RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint(ruta).build();

        EditarDescripcionPedidoAsesor servicio = restAdapter.create(EditarDescripcionPedidoAsesor.class);

        servicio.editarDescripcionPedidoAsesor(idDescPedido, idEstado, observacionAsesor, idColor, fotoB1, fotoB2,
                fotoB3, codigo, nombreFoto1, nombreFoto2, nombreFoto3, new Callback<String>() {
                    @Override
                    public void success(String producto, Response response) {

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Registrado satisfactoriamente!", Toast.LENGTH_SHORT);
                        toast.show();


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
            byte[] b = baos.toByteArray();
            Log.i("binarios: ", b+"");
            String temp = Base64.encodeToString(b, Base64.DEFAULT);
            return temp;
        } catch (NullPointerException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
    }

    public static String BitmapToByteString(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream(bitmap.getByteCount());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, baos);
            byte[] images = baos.toByteArray();

            String encodedString = new String(images);
            Log.i("String----",encodedString );
            return encodedString;
        } catch (NullPointerException e) {
            return null;
        } catch (OutOfMemoryError e) {
            return null;
        }
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


    //////////////////////////////////////////////

    public int uploadFile(final String selectedFilePath){

        int serverResponseCode = 0;

        HttpURLConnection connection;
        DataOutputStream dataOutputStream;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";


        int bytesRead,bytesAvailable,bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        File selectedFile = new File(selectedFilePath);


        String[] parts = selectedFilePath.split("/");
        final String fileName = parts[parts.length-1];

        if (!selectedFile.isFile()){
            dialog.dismiss();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                    tvFileName.setText("Source File Doesn't Exist: " + selectedFilePath);
                }
            });
            return 0;
        }else{
            try{
                FileInputStream fileInputStream = new FileInputStream(selectedFile);
                URL url = new URL(SERVER_URL);
                connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);//Allow Inputs
                connection.setDoOutput(true);//Allow Outputs
                connection.setUseCaches(false);//Don't use a cached Copy
                connection.setRequestMethod("POST");
                connection.setRequestProperty("Connection", "Keep-Alive");
                connection.setRequestProperty("ENCTYPE", "multipart/form-data");
                connection.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                connection.setRequestProperty("uploaded_file",selectedFilePath);

                //creating new dataoutputstream
                dataOutputStream = new DataOutputStream(connection.getOutputStream());

                //writing bytes to data outputstream
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd);
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"uploaded_file\";filename=\""
                        + selectedFilePath + "\"" + lineEnd);

                dataOutputStream.writeBytes(lineEnd);

                //returns no. of bytes present in fileInputStream
                bytesAvailable = fileInputStream.available();
                //selecting the buffer size as minimum of available bytes or 1 MB
                bufferSize = Math.min(bytesAvailable,maxBufferSize);
                //setting the buffer as byte array of size of bufferSize
                buffer = new byte[bufferSize];

                //reads bytes from FileInputStream(from 0th index of buffer to buffersize)
                bytesRead = fileInputStream.read(buffer,0,bufferSize);

                //loop repeats till bytesRead = -1, i.e., no bytes are left to read
                while (bytesRead > 0){
                    //write the bytes read from inputstream
                    dataOutputStream.write(buffer,0,bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable,maxBufferSize);
                    bytesRead = fileInputStream.read(buffer,0,bufferSize);
                }

                dataOutputStream.writeBytes(lineEnd);
                dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);

                serverResponseCode = connection.getResponseCode();
                String serverResponseMessage = connection.getResponseMessage();

                Log.i("Server Response is: ", serverResponseMessage + ": " + serverResponseCode);

                //response code of 200 indicates the server status OK
                if(serverResponseCode == 200){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            tvFileName.setText("File Upload completed.\n\n You can see the uploaded file here: \n\n" + "http://coderefer.com/extras/uploads/"+ fileName);
                        }
                    });
                }

                //closing the input and output streams
                fileInputStream.close();
                dataOutputStream.flush();
                dataOutputStream.close();



            } catch (FileNotFoundException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        Toast.makeText(MainActivity.this,"File Not Found",Toast.LENGTH_SHORT).show();
                    }
                });
            } catch (MalformedURLException e) {
                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "URL error!", Toast.LENGTH_SHORT).show();

            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "Cannot Read/Write File!", Toast.LENGTH_SHORT).show();
            }
            dialog.dismiss();
            return serverResponseCode;
        }

    }

}
