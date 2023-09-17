package me.pertzis.mantissa.server.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;

public class ClientMessage extends Message {
    @JsonProperty("success")
    private boolean success;

    @JsonProperty("response")
    private HashMap<String, Object> response;

    public ClientMessage(@JsonProperty("id") String id, @JsonProperty("action") String action,
                         @JsonProperty("parameters") HashMap<String, Object> parameters,
                         @JsonProperty("success") boolean success,
                         @JsonProperty("response") HashMap<String, Object> response) {
        super(action, parameters);
        this.success = success;
        this.response = response;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public HashMap<String, Object> getResponse() {
        return response;
    }

    public void setResponse(HashMap<String, Object> response) {
        this.response = response;
    }
}
