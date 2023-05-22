package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Paso1 extends AppCompatActivity {

    private Button atrasButton;
    private Button continuarButton;

    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso1);

        atrasButton=findViewById(R.id.atrasButtonP1);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Paso1.this,Requisitos.class));
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP1);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Paso1.this, Paso2.class));
            }
        });
        helpButton=findViewById(R.id.helpButtonP1);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Paso1.this,"Ayuda",Toast.LENGTH_LONG).show();
            }
        });
    }
}