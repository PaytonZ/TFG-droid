package com.bsod.tfg.modelo.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 04/03/2015.
 */

public class MessageChat implements Serializable {
    @JsonProperty("message")
    private String message;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("name")
    private String userName;
    @JsonProperty("room")
    private String room;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }
}
