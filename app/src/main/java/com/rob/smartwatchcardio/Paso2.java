package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Paso2 extends AppCompatActivity {
    private Button atrasButton;
    private Button continuarButton;
    private Button helpButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso2);
        atrasButton=findViewById(R.id.atrasButtonP2);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Paso2.this,Paso1.class));
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP2);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Paso2.this, Paso3.class));
            }
        });
        helpButton=findViewById(R.id.helpButtonP2);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Paso2.this,"Ayuda",Toast.LENGTH_LONG).show();
            }
        });
    }
}