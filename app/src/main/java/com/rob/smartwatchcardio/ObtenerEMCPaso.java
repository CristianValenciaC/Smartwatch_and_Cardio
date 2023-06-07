package com.rob.smartwatchcardio;

import static android.os.Environment.getExternalStorageDirectory;
import static android.os.Environment.getExternalStoragePublicDirectory;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.rob.smartwatchcardio.retrofit.APIRequest;
import com.rob.smartwatchcardio.retrofit.RetrofitInstance;
import com.rob.smartwatchcardio.retrofit.data.Environments;
import com.rob.smartwatchcardio.retrofit.request.ObtainRequest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ObtenerEMCPaso extends AppCompatActivity {

    private int paso;
    private int media_lpm;
    private int signalid;
    private List<Integer> result;
    private FirebaseUser currentUser;

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
        SharedPreferences pref = getSharedPreferences(currentUser.getUid(), Context.MODE_PRIVATE);
        globalVariable.setAction("list");
        globalVariable.setAccess_token(pref.getString("access_token", ""));

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
                        media_lpm = serie1.get("heart_rate").getAsInt();
                        signalid = ECG.get("signalid").getAsInt();
                        getSignalId();
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

    private void getSignalId(){

        Retrofit retrofit = RetrofitInstance.getRetrofit();

        APIRequest apiInterface = retrofit.create(APIRequest.class);
        globalVariable.setAction("get");
        Call<ObtainRequest> listaID = apiInterface.getSignalIdData("Bearer " + globalVariable.getAccess_token(), globalVariable.getAction(), signalid);

        Log.i("Conseguido", "prueba");

        listaID.enqueue(new Callback<ObtainRequest>() {
            @Override
            public void onResponse(Call<ObtainRequest> call, Response<ObtainRequest> response) {
                if(response.isSuccessful()){
                    if(response.body().getStatus() == 0) {
                        JsonObject array = response.body().getBody();
                        JsonArray array1 = array.get("signal").getAsJsonArray();
                        List<Integer> signal = new ArrayList<>();
                        for(int i = 0; i < array1.size(); i++){
                            signal.add(array1.get(i).getAsInt());
                        }
                        result = signal;

                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            String currentPath = Paths.get("").toAbsolutePath().normalize().toString();
                            File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), "datafiles");


                            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-M-dd_HH-mm");
                            LocalDateTime now = LocalDateTime.now();
                            System.out.println(dtf.format(now));
                            String fileName = "Reporte_" + paso +dtf.format(now) + ".csv";

                            File statText = new File(folder, fileName);

                            FileOutputStream is = null;
                            try {
                                folder.mkdirs();
                                is = new FileOutputStream(statText);
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e); //Falla apertura del archivo .csv
                            }
                            OutputStreamWriter osw = new OutputStreamWriter(is);
                            Writer w = new BufferedWriter(osw);

                            try {
                                w.write("Dato de la grafica del paso: " + paso + "\n");
                                w.write("Media latidos del corazon por minuto: " + media_lpm + "\n");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }

                            for(int i = 0; i < result.size(); i++){
                                try {
                                    w.write(result.get(i) + ",");
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }

                            try {
                                w.close();
                                Log.i("Conseguido", "prueba");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        switch (paso) {
                            case 1:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso2.class));
                                finish();
                                break;
                            case 2:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso3.class));
                                finish();
                                break;
                            case 3:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso4.class));
                                finish();
                                break;
                            case 4:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso5.class));
                                finish();
                                break;
                            case 5:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso6.class));
                                finish();
                                break;
                            case 6:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso7.class));
                                finish();
                                break;
                            case 7:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso8.class));
                                finish();
                                break;
                            case 8:
                                startActivity(new Intent(ObtenerEMCPaso.this, Paso9.class));
                                finish();
                                break;
                            case 9:
                                startActivity(new Intent(ObtenerEMCPaso.this, Resultados.class));
                                finish();
                                break;

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<ObtainRequest> call, Throwable t) {
                t.getCause();
            }
        });
    }
}