package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.rob.smartwatchcardio.authorizationwatch.AuthorizationComprobate;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

                if(currentUser == null){
                    startActivity(new Intent(SplashActivity.this, IniciarSesionActivity.class));
                }else{
                    startActivity(new Intent(SplashActivity.this, AuthorizationComprobate.class).putExtra("stage", 0));
                }
                finish();
            }
        }, 600);
    }
}