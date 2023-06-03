package com.rob.smartwatchcardio.authorizationwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.rob.smartwatchcardio.IniciarSesionActivity;
import com.rob.smartwatchcardio.InicioPrincipal;
import com.rob.smartwatchcardio.R;

public class AuthorizationTrue extends AppCompatActivity {

    private Button btnContinuar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_true);

        btnContinuar = findViewById(R.id.button_continue);

        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthorizationTrue.this, InicioPrincipal.class));
            }
        });
    }
}