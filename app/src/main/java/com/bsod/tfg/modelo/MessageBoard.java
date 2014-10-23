package com.bsod.tfg.modelo;

import java.security.Timestamp;

/**
 * Created by Payton on 25/09/2014.
 */
public class MessageBoard {

    private Integer id;
    private String message;
    private Timestamp creationDate;
    //private User user;
    //private Image ...


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

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
