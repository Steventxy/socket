package com.hunau.domain;

/**
 * @author TanXY
 * @create 2020/6/20 - 21:36
 */
@lombok.Data
public class Data {
    private int dataId;

    private double temperature;

    private double humidity;

    private String collTime;

    public Data(){

    }

    public Data(double temperature, double humidity) {
        this.temperature = temperature;
        this.humidity = humidity;
    }
}
