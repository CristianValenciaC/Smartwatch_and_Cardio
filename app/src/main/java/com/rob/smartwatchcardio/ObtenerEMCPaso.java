package com.rob.smartwatchcardio;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rob.smartwatchcardio.retrofit.APIRequest;
import com.rob.smartwatchcardio.retrofit.RetrofitInstance;
import com.rob.smartwatchcardio.retrofit.data.Environments;
import com.rob.smartwatchcardio.retrofit.request.ObtainRequest;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ObtenerEMCPaso extends AppCompatActivity {

    private int paso;

    private List<Integer> result;

    private Environments globalVariable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obtener_emcpaso);

        paso = getIntent().getExtras().getInt("paso");
        try {
            globalVariable = new Environments();
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }

        getEMC();
    }

    private void getEMC(){
        Retrofit retrofit = RetrofitInstance.getRetrofit();

        APIRequest apiInterface = retrofit.create(APIRequest.class);
        globalVariable.setAction("list");

        Call<ObtainRequest> lista = apiInterface.getECM("Bearer " + globalVariable.getAccess_token(), globalVariable.getAction());

        lista.enqueue(new Callback<ObtainRequest>() {
            @Override
            public void onResponse(Call<ObtainRequest> call, Response<ObtainRequest> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus() == 0) {
                        JsonObject array = response.body().getBody();
                        JsonArray arraySerie = array.getAsJsonArray("series");
                        JsonObject serie1 = arraySerie.get(0).getAsJsonObject();
                        JsonObject ECG = serie1.get("ecg").getAsJsonObject();
                        int signalid = ECG.get("signalid").getAsInt();

                        Retrofit retrofit = RetrofitInstance.getRetrofit();

                        APIRequest apiInterface = retrofit.create(APIRequest.class);
                        globalVariable.setAction("get");
                        Call<ObtainRequest> listaID = apiInterface.getSignalIdData("Bearer " + globalVariable.getAccess_token(), globalVariable.getAction(), signalid);

                        getSignalId(listaID);

                        switch (paso) {
                            case 1:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso2.class));
                            case 2:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso3.class));
                            case 3:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso4.class));
                            case 4:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso5.class));
                            case 5:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso6.class));
                            case 6:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso7.class));
                            case 7:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso8.class));
                            case 8:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso9.class));
                            case 9:
                                startActivity(new Intent(ObtenerEMCPaso.this, Resultados.class));

                        }
                    }
                }

                //text.append("Respuesta: " + response.body().getBody() + "\n");
                //text.append("Status:  " + response.body().getStatus() + "\n" );
            }

            @Override
            public void onFailure(Call<ObtainRequest> call, Throwable t) {

            }
        });
    }

    private void getSignalId(Call<ObtainRequest> listaID){
        listaID.enqueue(new Callback<ObtainRequest>() {
            @Override
            public void onResponse(Call<ObtainRequest> call, Response<ObtainRequest> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus() == 0) {
                        JsonObject array = response.body().getBody();
                        JsonArray array1 = array.get("signal").getAsJsonArray();
                        List<Integer> signal = null;
                        for(int i = 0; i < array1.size(); i++){
                            signal.add(i);
                        }
                        result = signal;
                    }
                }
            }

            @Override
            public void onFailure(Call<ObtainRequest> call, Throwable t) {

            }
        });
    }
}