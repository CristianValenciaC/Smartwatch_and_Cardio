package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class Requisitos extends AppCompatActivity {

    private ImageButton atrasButton;
    private ImageButton continuarButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisitos);

        atrasButton=findViewById(R.id.atrasButtonR);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                onBackPressed();

            }
        });
        continuarButton=findViewById(R.id.continuarButtonR);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Requisitos.this, Comprobacion.class);

                startActivity(intent);

            }
        });
    }
}