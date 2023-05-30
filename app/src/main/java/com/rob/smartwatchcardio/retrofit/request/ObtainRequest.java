package com.rob.smartwatchcardio.retrofit.request;

import com.google.gson.JsonObject;

public class ObtainRequest {

    private int status;
    private JsonObject body;

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

}
