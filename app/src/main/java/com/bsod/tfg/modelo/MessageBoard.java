package com.bsod.tfg.modelo;

import org.codehaus.jackson.annotate.JsonProperty;

import java.util.Date;

/**
 * Created by Payton on 25/09/2014.
 */

public class MessageBoard {

    @JsonProperty("pk")
    private Integer id;
    @JsonProperty("texto")
    private String message;
    @JsonProperty("fecha_creacion")
    private long creationDateUnix;
    private Date creationDate;
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
}
