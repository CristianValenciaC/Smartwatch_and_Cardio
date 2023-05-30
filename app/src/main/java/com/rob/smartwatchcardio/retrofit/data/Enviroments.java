package com.rob.smartwatchcardio.retrofit.data;

import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.TreeMap;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

public class Enviroments {

    private String action;
    private String idUser = "c5089a0c400478a309cfddb4ce557af7456e851fcb4b82b6161d08e453db6f05";
    private String secret = "6f8d92529b8aba30fa86d9cb9df5f827bb4a84af4ee689ca792c4ed5d2f51a5a";
    private int timeStamp = 0;
    private String signatureValue = "";
    private String access_token;
    private String refresh_token;
    private String now_minus_one_day;
    private String responseType;
    private String state;
    private String code;
    private String scope;
    private String redirect_uri = "https://oauth.pstmn.io/v1/browser-callback";

    public Enviroments() throws NoSuchAlgorithmException, InvalidKeyException {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Long timeStampAux = timestamp.getTime() / 1000L;
        this.timeStamp= timeStampAux.intValue();

        Map<String, String> signedParams = new TreeMap<>();
        signedParams.put("action", "activate");
        signedParams.put("client_id", idUser);
        signedParams.put("timestamp", String.valueOf(timestamp));

        String data = String.join(",", signedParams.values());

        signatureValue = generateHmacSHA256(data, secret);
    }


    private static String generateHmacSHA256(String data, String secretKey) {
        try {
            Mac hmacSHA256 = Mac.getInstance("HmacSHA256");
            SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            hmacSHA256.init(keySpec);
            byte[] hashBytes = hmacSHA256.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return bytesToHex(hashBytes);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    public int getTimeStamp() {
        return timeStamp;
    }

    public String getSignatureValue() throws NoSuchAlgorithmException, InvalidKeyException {
        return signatureValue;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(String idUser) {
        this.idUser = idUser;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getResponseType() {
        return responseType;
    }

    public void setResponseType(String responseType) {
        this.responseType = responseType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public String getNow_minus_one_day() {
        return now_minus_one_day;
    }

    public void setNow_minus_one_day(String now_minus_one_day) {
        this.now_minus_one_day = now_minus_one_day;
    }

}
