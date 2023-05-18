package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class RegistroCuenta extends AppCompatActivity {

    private Spinner sexosSpinner;
    private Button usuarioCreadoButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cuenta);

        sexosSpinner =findViewById(R.id.sexosSpinner);
         ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.sexos, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);

        sexosSpinner.setAdapter(adapter);


        usuarioCreadoButton=findViewById(R.id.crearButton);
        usuarioCreadoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RegistroCuenta.this,Requisitos.class);
                startActivity(i);
            }
        });

    }
}