package co.com.planit.lavapp.common;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import co.com.planit.lavapp.Adviser.AsesorInicio;
import co.com.planit.lavapp.R;
import co.com.planit.lavapp.client.Inicio;
import co.com.planit.lavapp.client.Pago;
import co.com.planit.lavapp.config.UserLocalStore;

public class MainActivity extends AppCompatActivity {

    Button bIniciar, bContinuar;

    UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bIniciar = (Button) findViewById(R.id.bIniciar);
        bContinuar = (Button) findViewById(R.id.bContinuar);

        bContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Inicio.class);
                startActivity(intent);
            }
        });

        bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });

        userLocalStore = new UserLocalStore(this);

        SesionIniciada();
    }


    private void SesionIniciada(){
        if (userLocalStore.getUserLoggedIn()==true){
            if(userLocalStore.getLoggedInUser().getRol().getIdRol()==3){

                    startActivity(new Intent(this, AsesorInicio.class));

            }else{
                if(userLocalStore.getLoggedInUser().getRol().getIdRol()==4){

                        startActivity(new Intent(this, Inicio.class));
                }
            }
        }
    }

}
