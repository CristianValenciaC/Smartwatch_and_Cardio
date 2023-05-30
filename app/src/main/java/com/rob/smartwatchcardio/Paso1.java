package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.app.AlertDialog;
import android.app.Dialog;
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

    private ImageButton atrasButton;
    private ImageButton continuarButton;

    private Button helpButton;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private  Chronometer simpleChronometer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paso1);

        atrasButton=findViewById(R.id.atrasButtonP7);
        simpleChronometer = (Chronometer) findViewById(R.id.simpleChronometer);
        simpleChronometer.start();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            simpleChronometer.setCountDown(true);
            simpleChronometer.setBase(SystemClock.elapsedRealtime()+6*1000);
            simpleChronometer.start();
        }
        simpleChronometer
                .setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {

                    @Override
                    public void onChronometerTick(Chronometer chronometer) {
                        if( chronometer.getText().toString().equalsIgnoreCase("00:00"))
                            simpleChronometer.stop();

                    }
                });

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
                simpleChronometer.stop();
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

        dialog.show();
        //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        dialog.getWindow().setGravity(Gravity.BOTTOM);



        //dialogBuilder.setView(popup);
        //dialog = dialogBuilder.create();
        //dialog.show();
    }


}