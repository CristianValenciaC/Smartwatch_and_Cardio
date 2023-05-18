package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Paso4 extends AppCompatActivity {

    private Button atrasButton;
    private Button continuarButton;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso4);

        atrasButton=findViewById(R.id.atrasButtonP4);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Paso4.this,Paso3.class));
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP4);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Paso4.this, Paso5.class));
            }
        });
        helpButton=findViewById(R.id.helpButtonp4);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Paso4.this,"Ayuda",Toast.LENGTH_LONG).show();
            }
        });
    }
}