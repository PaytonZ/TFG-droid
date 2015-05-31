package com.bsod.tfg.modelo.archivos.estadisticas;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

/**
 * Proudly created by Payton on 21/03/2015.
 */
public class EstadisticasGlobalTest implements Serializable {

    @JsonProperty("per_correct")
    private float averageCorrect;
    @JsonProperty("per_failed")
    private float averageFailed;
    @JsonProperty("per_not_answered")
    private float averageNotAnswered;
    @JsonProperty("avg_mark")
    private float averageMark;
    @JsonProperty("avg_time")
    private float averageTime;
    @JsonProperty("tests")
    private EstadisticasTest[] estadisticasTests;


    public float getAverageCorrect() {
        return averageCorrect;
    }

    public void setAverageCorrect(float averageCorrect) {
        this.averageCorrect = averageCorrect;
    }

    public float getAverageFailed() {
        return averageFailed;
    }

    public void setAverageFailed(float averageFailed) {
        this.averageFailed = averageFailed;
    }

    public float getAverageNotAnswered() {
        return averageNotAnswered;
    }

    public void setAverageNotAnswered(float averageNotAnswered) {
        this.averageNotAnswered = averageNotAnswered;
    }

    public float getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(float averageMark) {
        this.averageMark = averageMark;
    }

    public float getAverageTime() {
        return averageTime;
    }

    public void setAverageTime(float averageTime) {
        this.averageTime = averageTime;
    }

    public EstadisticasTest[] getEstadisticasTests() {
        return estadisticasTests;
    }

    public void setEstadisticasTests(EstadisticasTest[] estadisticasTests) {
        this.estadisticasTests = estadisticasTests;
    }
}
