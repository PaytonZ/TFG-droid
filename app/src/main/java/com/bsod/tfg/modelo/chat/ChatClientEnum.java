package com.bsod.tfg.modelo.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Proudly created by Payton on 14/03/2015.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum ChatClientEnum {

    LOGIN("login"), MESSAGE("message"), ROOM("room");
    private final String value;

    ChatClientEnum(String s) {
        this.value = s;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}