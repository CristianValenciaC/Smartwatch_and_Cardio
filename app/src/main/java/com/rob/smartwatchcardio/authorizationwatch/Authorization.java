package com.rob.smartwatchcardio.authorizationwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;

import com.rob.smartwatchcardio.R;
import com.rob.smartwatchcardio.retrofit.data.Environments;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class Authorization extends AppCompatActivity {

    private EditText link;
    private Button navegador;
    private Button authorization;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);

        navegador = findViewById(R.id.button_open_browse);
        authorization = findViewById(R.id.button_code);
        link = findViewById(R.id.text_line_code);

        navegador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirNavegador();
            }
        });

        authorization.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comprobacion();
            }
        });

    }

    private void abrirNavegador(){
        try {
            Environments globalVariable = new Environments();
            globalVariable.setState("TmosVHhcGNF_Lis");
            globalVariable.setScope("user.metrics,user.activity");

            /*
            Uri webpage = Uri.parse("https://account.withings.com/oauth2_user/authorize2?response_type=code&client_id="+globalVariable.getIdUser() + "&redirect_uri="+ globalVariable.getRedirect_uri() + "&state="+ globalVariable.getState() +"&scope="+ globalVariable.getScope());
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            }*/

            WebView pagina = new WebView(this);
            //pagina.setWebViewClient(new WebViewClient());
            pagina.getSettings().setJavaScriptEnabled(true);
            //pagina.loadUrl("https://account.withings.com/oauth2_user/authorize2");

            pagina.loadUrl("https://account.withings.com/oauth2_user/authorize2?response_type=code&client_id="+globalVariable.getIdUser() + "&redirect_uri="+ globalVariable.getRedirect_uri() + "&state="+ globalVariable.getState() +"&scope="+ globalVariable.getScope());
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private void comprobacion(){
        String url = link.getText().toString();
        String code = url.substring(url.indexOf("=")+1, url.indexOf("&"));
        Intent i = new Intent(Authorization.this, AuthorizationComprobate.class);
        i.putExtra("stage", 2);
        i.putExtra("code", code);
        startActivity(i);
    }
}