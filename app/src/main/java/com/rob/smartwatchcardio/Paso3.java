package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

public class Paso3 extends AppCompatActivity {
    private ImageButton atrasButton;
    private ImageButton continuarButton;
    private Button helpButton;

    //seguro increiblemente necesario

    private Button regresar,cerrar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso3);
        atrasButton=findViewById(R.id.atrasButtonP3);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    public void seguroVolver(){
        final Dialog dialog = new Dialog(Paso3.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_seguro_cerrado);
        final View popup = getLayoutInflater().inflate(R.layout.popup_seguro_cerrado,null);
        ViewGroup.LayoutParams lay =  popup.getLayoutParams();
        regresar=  dialog.findViewById(R.id.regresarbtn);
        cerrar =dialog.findViewById(R.id.cerrarBtn);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

    }

    @Override
    public void onBackPressed() {

        seguroVolver();

    }
}