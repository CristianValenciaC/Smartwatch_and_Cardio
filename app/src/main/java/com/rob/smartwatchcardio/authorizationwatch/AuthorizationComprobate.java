package com.rob.smartwatchcardio.authorizationwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonObject;
import com.rob.smartwatchcardio.IniciarSesionActivity;
import com.rob.smartwatchcardio.InicioPrincipal;
import com.rob.smartwatchcardio.R;
import com.rob.smartwatchcardio.SplashActivity;
import com.rob.smartwatchcardio.retrofit.APIRequest;
import com.rob.smartwatchcardio.retrofit.RetrofitInstance;
import com.rob.smartwatchcardio.retrofit.data.Environments;
import com.rob.smartwatchcardio.retrofit.request.ObtainRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthorizationComprobate extends AppCompatActivity {

    private Environments globalVariable;
    private int stage;

    private FirebaseUser currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_comprobate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                currentUser = FirebaseAuth.getInstance().getCurrentUser();
                stage = getIntent().getExtras().getInt("stage", 0);
                try {
                    globalVariable = new Environments();
                    if(stage != 2){
                        refreshAccessToken();
                    }else{
                        globalVariable.setCode(getIntent().getExtras().getString("code",""));
                        getAccessToken();
                    }
                } catch (NoSuchAlgorithmException | InvalidKeyException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 600);
    }

    private void refreshAccessToken(){
        Retrofit info = RetrofitInstance.getRetrofit();

        APIRequest apiInterface = info.create(APIRequest.class);
        SharedPreferences pref = getSharedPreferences(currentUser.getUid(), Context.MODE_PRIVATE);
        globalVariable.setAction("requesttoken");
        globalVariable.setAccess_token(pref.getString("refresh_token"," "));

        Call<ObtainRequest> accessTokenCall = apiInterface.refreshAccessToken(globalVariable.getAction(),
                globalVariable.getIdUser(), globalVariable.getSecret(),
                "refresh_token", globalVariable.getRefresh_token());

        accessTokenCall.enqueue(new Callback<ObtainRequest>() {
            @Override
            public void onResponse(Call<ObtainRequest> call, Response<ObtainRequest> response) {

                if(response.body().getStatus() == 0){
                    JsonObject array = response.body().getBody();

                    SharedPreferences pref = getSharedPreferences(currentUser.getUid(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("access_token", array.get("access_token").getAsString());
                    editor.putString("refresh_token", array.get("refresh_token").getAsString());
                    editor.apply();

                    globalVariable.setAccess_token(array.get("access_token").getAsString());
                    globalVariable.setRefresh_token(array.get("refresh_token").getAsString());
                    if(stage == 0){
                        startActivity(new Intent(AuthorizationComprobate.this, InicioPrincipal.class));
                    }
                }else{
                    //TODO CAMBIAR A ACTIVIDAD FALLIDA
                    startActivity(new Intent(AuthorizationComprobate.this, InicioPrincipal.class));
                }
            }

            @Override
            public void onFailure(Call<ObtainRequest> call, Throwable t) {

            }
        });

    }

    private void getAccessToken(){
        Retrofit info = RetrofitInstance.getRetrofit();

        APIRequest apiInterface = info.create(APIRequest.class);
        globalVariable.setAction("requesttoken");

        Call<ObtainRequest> accessTokenCall = apiInterface.getAccessToken(globalVariable.getAction(),
                globalVariable.getIdUser(), globalVariable.getSecret(),
                "authorization_code",
                globalVariable.getCode(), globalVariable.getRedirect_uri());

        accessTokenCall.enqueue(new Callback<ObtainRequest>() {
            @Override
            public void onResponse(Call<ObtainRequest> call, Response<ObtainRequest> response) {
                if(response.body().getStatus() == 0){
                    JsonObject array = response.body().getBody();
                    SharedPreferences pref = getSharedPreferences(currentUser.getUid(), Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("access_token", array.get("access_token").getAsString());
                    editor.putString("refresh_token", array.get("refresh_token").getAsString());
                    editor.apply();
                    globalVariable.setAccess_token(array.get("access_token").getAsString());
                    globalVariable.setRefresh_token(array.get("refresh_token").getAsString());
                    startActivity(new Intent(AuthorizationComprobate.this, AuthorizationTrue.class));
                }else{
                    startActivity(new Intent(AuthorizationComprobate.this, AuthorizationFalse.class));
                }
            }

            @Override
            public void onFailure(Call<ObtainRequest> call, Throwable t) {

            }
        });

    }
}