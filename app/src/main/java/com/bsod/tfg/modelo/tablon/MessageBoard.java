package com.bsod.tfg.modelo.tablon;


import com.bsod.tfg.modelo.sesion.User;
import com.bsod.tfg.utils.MessageBoardDeserializer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Date;

/**
 * Proudly created by Payton on 25/09/2014.
 */
@JsonDeserialize(using = MessageBoardDeserializer.class)
@DatabaseTable(tableName = "MessageBoard")
public class MessageBoard implements Serializable {

    @DatabaseField(generatedId = true)
    protected long id_db;

    @JsonProperty("pk")
    @DatabaseField(columnName = "id", index = true)
    private Integer id;

    @DatabaseField(columnName = "message")
    @JsonProperty("texto")
    private String message;

    @DatabaseField(columnName = "creationDateUnix")
    @JsonProperty("fecha_creacion")
    private long creationDateUnix;

    @DatabaseField(columnName = "creationDate")
    private Date creationDate;

    @DatabaseField(columnName = "numOfFavs")
    @JsonProperty("num_fav")
    private int numOfFavs;

    @DatabaseField(columnName = "userFavorited")
    @JsonProperty("user_favorited")
    private boolean userFavorited;

    @DatabaseField(columnName = "owner")
    @JsonProperty("owner")
    private boolean owner;

    @JsonIgnore
    private String humanReadableDate;

    @JsonProperty("usuario")
    @DatabaseField(foreign = true, foreignAutoRefresh = true, foreignAutoCreate = true, columnName = "usuario")
    private User user;

    public MessageBoard() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


    public long getCreationDateUnix() {
        return creationDateUnix;
    }

    public void setCreationDateUnix(long creationDateUnix) {
        this.creationDateUnix = creationDateUnix;


    }

    public Date getCreationDate() {
        return (creationDate == null) ? creationDate = new Date(creationDateUnix * 1000L) : creationDate; // *1000 is to convert seconds to milliseconds ;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumOfFavs() {
        return numOfFavs;
    }

    public void setNumOfFavs(int numOfFavs) {
        this.numOfFavs = numOfFavs;
    }

    public boolean isUserFavorited() {
        return userFavorited;
    }

    public void setUserFavorited(boolean userFavorited) {
        this.userFavorited = userFavorited;
    }

    public boolean isOwner() {
        return owner;
    }

    public void setOwner(boolean owner) {
        this.owner = owner;
    }


    public String getHumanReadableDate() {


        return humanReadableDate;
    }

    public void setHumanReadableDate(String humanReadableDate) {
        this.humanReadableDate = humanReadableDate;
    }
}
