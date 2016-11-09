package co.com.planit.lavapp.client;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import co.com.planit.lavapp.R;
import co.com.planit.lavapp.common.Login;

public class RegisterEnd extends AppCompatActivity implements View.OnClickListener  {

    Button bAceptar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_end);

        bAceptar = (Button) findViewById(R.id.bAceptar);

        bAceptar.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){

            case R.id.bAceptar:

                startActivity(new Intent(this, Login.class));

                break;

        }

    }


}
