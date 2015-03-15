package com.bsod.tfg.modelo.chat;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 14/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChatClientBean implements Serializable {

    @JsonProperty("type")
    private ChatClientEnum type;
    @JsonProperty("token")
    private String token;
    @JsonProperty("room")
    private String room;
    @JsonProperty("message")
    private String message;

    public ChatClientEnum getType() {
        return type;
    }

    public void setType(ChatClientEnum type) {
        this.type = type;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
