package me.pertzis.mantissa.server.messaging;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashMap;
import java.util.UUID;

public class Message {
    @JsonProperty("id")
    private String id = UUID.randomUUID().toString();

    @JsonProperty("action")
    private String action;

    @JsonProperty("parameters")
    private HashMap<String, Object> parameters;

    public Message(String id, String action, HashMap<String, Object> parameters) {
        this.id = id;
        this.action = action;
        this.parameters = parameters;
    }
    public Message(String action, HashMap<String, Object> parameters) {
        this.action = action;
        this.parameters = parameters;
    }
    public Message(String action) {
        this.action = action;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public HashMap<String, Object> getParameters() {
        return parameters;
    }

    public void setParameters(HashMap<String, Object> parameters) {
        this.parameters = parameters;
    }
}
