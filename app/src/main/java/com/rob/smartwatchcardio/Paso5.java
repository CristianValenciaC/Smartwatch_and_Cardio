package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Paso5 extends AppCompatActivity {

    private Button atrasButton;
    private Button continuarButton;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso5);

        atrasButton=findViewById(R.id.atrasButtonP5);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP5);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Paso5.this, Paso1.class));
            }
        });
        helpButton=findViewById(R.id.helpButtonp5);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Paso5.this,"Ayuda",Toast.LENGTH_LONG).show();
            }
        });
    }
}