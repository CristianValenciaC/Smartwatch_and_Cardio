package com.rob.smartwatchcardio.authorizationwatch;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonObject;
import com.rob.smartwatchcardio.InicioPrincipal;
import com.rob.smartwatchcardio.R;
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

    private RetrofitInstance retrofitInstance;
    private Environments globalVariable;
    private int stage;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization_comprobate);

        retrofitInstance = new RetrofitInstance();

        stage = getIntent().getExtras().getInt("stage", 0);
        try {
            globalVariable = new Environments();
            if(stage != 2){
                refreshAccessToken();
            }else{
                globalVariable.setCode(getIntent().getExtras().getString("code",""));
                getAccessToken();
            }
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }

    private void refreshAccessToken(){
        Retrofit info = retrofitInstance.getRetrofit();

        APIRequest apiInterface = info.create(APIRequest.class);
        globalVariable.setAction("requesttoken");

        Call<ObtainRequest> accessTokenCall = apiInterface.refreshAccessToken(globalVariable.getAction(),
                globalVariable.getIdUser(), globalVariable.getSecret(),
                "refresh_token", globalVariable.getRedirect_uri());

        accessTokenCall.enqueue(new Callback<ObtainRequest>() {
            @Override
            public void onResponse(Call<ObtainRequest> call, Response<ObtainRequest> response) {

                if(response.body().getStatus() == 0){
                    JsonObject array = response.body().getBody();
                    globalVariable.setAccess_token(array.get("access_token").getAsString());
                    globalVariable.setRefresh_token(array.get("refresh_token").getAsString());
                    if(stage == 0){
                        startActivity(new Intent(AuthorizationComprobate.this, InicioPrincipal.class));
                    }
                }else{
                    startActivity(new Intent(AuthorizationComprobate.this, AuthorizationFalse.class));
                }
            }

            @Override
            public void onFailure(Call<ObtainRequest> call, Throwable t) {

            }
        });

    }

    private void getAccessToken(){
        Retrofit info = retrofitInstance.getRetrofit();

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