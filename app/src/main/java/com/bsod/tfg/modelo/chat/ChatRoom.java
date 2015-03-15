package com.bsod.tfg.modelo.chat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Proudly created by Payton on 14/03/2015.
 */
public class ChatRoom implements Serializable {

    private String name;
    private String idRoom;
    private ArrayList<MessageChat> listofMessages = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<MessageChat> getListofMessages() {
        return listofMessages;
    }

    public void setListofMessages(ArrayList<MessageChat> listofMessages) {
        this.listofMessages = listofMessages;
    }

    public String getIdRoom() {
        return idRoom;
    }

    public void setIdRoom(String id) {
        this.idRoom = id;
    }
}
