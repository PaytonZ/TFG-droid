package com.bsod.tfg.modelo.chat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Proudly created by Payton on 14/03/2015.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@DatabaseTable(tableName = "ChatServerBean")
public class ChatServerBean implements Serializable {

    @JsonIgnore
    @DatabaseField(generatedId = true)
    protected long id_db;

    @DatabaseField
    @JsonProperty("type")
    private ChatServerEnum type;

    @DatabaseField
    @JsonProperty("name")
    private String name;

    @DatabaseField
    @JsonProperty("user_id")
    private int userId;

    @DatabaseField
    @JsonProperty("room")
    private String room;

    @DatabaseField
    @JsonProperty("message")
    private String message;

    @DatabaseField
    @JsonProperty("time")
    private Long time;

    public ChatServerEnum getType() {
        return type;
    }

    public void setType(ChatServerEnum type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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


    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}
