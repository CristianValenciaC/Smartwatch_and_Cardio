package com.rob.smartwatchcardio.retrofit;

import com.rob.smartwatchcardio.retrofit.request.ObtainRequest;

import retrofit2.Call;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface APIRequest {

    @POST("v2/oauth2")
    Call<ObtainRequest> getAccessToken(@Query("action")String getNonce,
                                       @Query("client_id")String idApp,
                                       @Query("client_secret")String secret,
                                       @Query("grant_type")String authorization_code,
                                       @Query("code")String code,
                                       @Query("redirect_uri") String url);

    @POST("/v2/heart")
    Call<ObtainRequest> getECM(@Header("Authorization") String access_tokenBeard,
                           @Query("action")String action);

    @POST("v2/oauth2")
    Call<ObtainRequest> refreshAccessToken(@Query("action")String getNonce,
                                           @Query("client_id")String idApp,
                                           @Query("client_secret")String secret,
                                           @Query("refresh_token")String authorization_code,
                                           @Query("redirect_uri") String url);

}
