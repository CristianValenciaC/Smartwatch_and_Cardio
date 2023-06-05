package com.rob.smartwatchcardio.authorizationwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.rob.smartwatchcardio.R;

public class AuthorizationFalse extends AppCompatActivity {

    private Button btnAutorizacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_false);

        btnAutorizacion = findViewById(R.id.button_repeat_authorization);

        btnAutorizacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AuthorizationFalse.this, Authorization.class));
            }
        });
    }
}