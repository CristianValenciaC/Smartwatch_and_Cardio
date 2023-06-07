package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.Toast;

public class Paso1 extends AppCompatActivity {
    //variables del paso
    private ImageButton atrasButton;
    private ImageButton continuarButton;
    private ImageButton helpButton;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    //variables del infopopup

    //variables del comprobar popup
    private Button botonComprobar;
    private boolean empezarPrueba = false;
    private Chronometer cronometro;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso1);
        helpButton=findViewById(R.id.infoButton);
        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                infoPop();
            }
        });

        atrasButton=findViewById(R.id.atrasButtonP7);
        atrasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        continuarButton=findViewById(R.id.continuarButtonP7);
        continuarButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               comprobacion();
                //Intent intent = new Intent(Paso1.this, Paso2.class);
                //intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                //finish();
                //startActivity(intent);

            }
        });
    }
    public void comprobacion(){
        //dialogBuilder = new AlertDialog.Builder(Paso1.this);
        final Dialog dialog = new Dialog(Paso1.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_comprobacion);
        final View popup = getLayoutInflater().inflate(R.layout.popup_comprobacion,null);
        ViewGroup.LayoutParams lay =  popup.getLayoutParams();
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                // Realiza alguna acción cuando el diálogo se cierre

                cronometro.stop();
                empezarPrueba=false;
                // ...
            }
        });
        //
        cronometro = dialog.findViewById(R.id.Crono);
        botonComprobar = dialog.findViewById(R.id.button);
        botonComprobar.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("NewApi")
            @Override
            public void onClick(View v) {
                cronometro.start();
                cronometro.setCountDown(true);
                cronometro.setBase(SystemClock.elapsedRealtime()+6*1000);
                cronometro.setFormat("");
                cronometro.start();
                cronometro
                        .setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

                            @Override
                            public void onChronometerTick(Chronometer chronometer) {
                                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                                int h   = (int)(time /3600000);
                                int m = (int)(time - h*3600000)/60000;
                                int s= (int)((time - h*3600000- m*60000)/1000 )*-1;
                                String ss = s < 10 ? ""+s: s+"";
                                chronometer.setText(""+ss);

                                if(ss.equalsIgnoreCase("0")){
                                    cronometro.stop();

                                    if (!empezarPrueba){
                                        empezarPrueba=true;
                                        cronometro.setBase(SystemClock.elapsedRealtime()+31*1000);
                                        cronometro.start();
                                    } else {
                                        Intent intent = new Intent(Paso1.this, Paso2.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                        finish();
                                        startActivity(intent);
                                    }
                                }




                            }
                        });
            }
        });
        //
        dialog.show();

        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setGravity(Gravity.BOTTOM);



        //dialogBuilder.setView(popup);
        //dialog = dialogBuilder.create();
        //dialog.show();
    }

    public void infoPop(){

        final Dialog dialog = new Dialog(Paso1.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.popup_informacion);
        final View popup = getLayoutInflater().inflate(R.layout.popup_informacion,null);
        ViewGroup.LayoutParams lay =  popup.getLayoutParams();



        dialog.show();
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.getWindow().setGravity(Gravity.END);

    }
}