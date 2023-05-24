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
                finish();
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP2);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Paso2.this, Paso3.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                finish();
                startActivity(intent);
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