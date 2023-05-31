package com.rob.smartwatchcardio.retrofit.request;

import com.google.gson.JsonObject;

public class ObtainRequest {

    private int status;
    private JsonObject body;

    private String error;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JsonObject getBody() {
        return body;
    }

    public void setBody(JsonObject body) {
        this.body = body;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
