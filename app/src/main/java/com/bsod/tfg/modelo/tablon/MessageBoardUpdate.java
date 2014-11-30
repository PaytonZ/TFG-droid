package com.bsod.tfg.modelo.tablon;


import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Proudly created by Payton on 25/11/2014.
 */
public class MessageBoardUpdate {
    @JsonProperty("pk")
    private Integer id;
    @JsonProperty("num_fav")
    private int numOfFavs;
    @JsonProperty("user_favorited")
    private boolean userFavorited;
    @JsonProperty("borrado")
    private boolean borrado;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public boolean isBorrado() {
        return borrado;
    }

    public void setBorrado(boolean borrado) {
        this.borrado = borrado;
    }
}
