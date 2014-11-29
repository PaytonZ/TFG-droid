package com.bsod.tfg.modelo.tablon;


import com.bsod.tfg.modelo.sesion.User;

import org.codehaus.jackson.annotate.JsonProperty;

import java.io.Serializable;
import java.util.Date;

/**
 * Proudly created by Payton on 25/09/2014.
 */
public class MessageBoard implements Serializable {

    @JsonProperty("pk")
    private Integer id;
    @JsonProperty("texto")
    private String message;
    @JsonProperty("fecha_creacion")
    private long creationDateUnix;
    private Date creationDate;
    @JsonProperty("num_fav")
    private int numOfFavs;
    @JsonProperty("user_favorited")
    private boolean userFavorited;
    @JsonProperty("owner")
    private boolean owner;

    //private User user;
    //private Image ...
    @JsonProperty("usuario")
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


}
