package com.bsod.tfg.modelo.estadisticas;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Proudly created by Payton on 21/04/2015.
 */
@DatabaseTable(tableName = "EstadisticasBean")
public class EstadisticasBean implements Serializable {

    @DatabaseField(generatedId = true)
    private long id_db;

    @DatabaseField
    private String tag;

    @DatabaseField
    private long seconds;

    @DatabaseField(version = true)
    private java.util.Date time;

    @DatabaseField
    private String other;

    public long getId_db() {
        return id_db;
    }

    public void setId_db(long id_db) {
        this.id_db = id_db;
    }

    public long getSeconds() {
        return seconds;
    }

    public void setSeconds(long seconds) {
        this.seconds = seconds;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public java.util.Date getTime() {
        return time;
    }

    public void setTime(java.util.Date time) {
        this.time = time;
    }
}
