package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Paso6 extends AppCompatActivity {

    private ImageButton atrasButton;
    private ImageButton continuarButton;

    private Button helpButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso6);

        atrasButton=findViewById(R.id.atrasButtonP6);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP6);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Paso6.this, Paso7.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                finish();
                startActivity(intent);

            }
        });
    }

}