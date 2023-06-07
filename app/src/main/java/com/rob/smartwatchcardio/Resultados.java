package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import javax.xml.transform.Result;

public class Resultados extends AppCompatActivity {

    private Button volver;
    private ImageButton inicio;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados);
        inicio = findViewById(R.id.atrasButtonP);
        volver = findViewById(R.id.volverBtn);

        inicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {finish();}
        });

        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {startActivity(new Intent(Resultados.this, Requisitos.class));}
        });
    }
}