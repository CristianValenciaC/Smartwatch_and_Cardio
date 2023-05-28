package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Paso3 extends AppCompatActivity {
    private ImageButton atrasButton;
    private ImageButton continuarButton;
    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso3);
        atrasButton=findViewById(R.id.atrasButtonP3);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP3);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Paso3.this, Paso4.class));
            }
        });
    }
}