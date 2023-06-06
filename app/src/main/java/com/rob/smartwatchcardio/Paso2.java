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

public class Paso2 extends AppCompatActivity {
    private ImageButton atrasButton;
    private ImageButton continuarButton;
    private Button helpButton;

    //seguro increiblemente necesario

    private Button regresar,cerrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso2);
        atrasButton=findViewById(R.id.atrasButtonP2);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
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
    }
    public void seguroVolver(){
        final Dialog dialog = new Dialog(Paso2.this);
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