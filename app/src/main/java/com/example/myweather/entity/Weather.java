package com.example.myweather.entity;

import com.example.myweather.R;

public class Weather {

    private String city;
    private int conditionID;
    private String description;
    private double temperature;

    public Weather() {
    }

    public int getWeatherIcon() {

        if(conditionID >= 200 && conditionID <300){
            return R.mipmap.thunderstorm;
        }
        else if (conditionID >= 300 && conditionID <400){
            return R.mipmap.drizzle;
        }
        else if (conditionID >= 500 && conditionID <600){
            return R.mipmap.rain;
        }
        else if (conditionID >= 600 && conditionID <700){
            return R.mipmap.snow;
        }
        else if (conditionID >= 700 && conditionID <800){
            return R.mipmap.mist;
        }
        else if (conditionID == 800){
            return R.mipmap.clear;
        }
        else {
            return R.mipmap.clouds;
        }

    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getConditionID() {
        return conditionID;
    }

    public void setConditionID(int conditionID) {
        this.conditionID = conditionID;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }




}
