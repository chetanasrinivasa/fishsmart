package com.mobiddiction.fishsmart.Weather;

/**
 * Created by Archa on 24/05/2016.
 */
public class forecastModel  {

    String startTimeLocal;
    String endTimeLocal;
    String startUtcLocal;
    String endUtcLocal;
    String forecast;

    public String getStartTimeLocal() {
        return startTimeLocal;
    }

    public void setStartTimeLocal(String startTimeLocal) {
        this.startTimeLocal = startTimeLocal;
    }

    public String getEndTimeLocal() {
        return endTimeLocal;
    }

    public void setEndTimeLocal(String endTimeLocal) {
        this.endTimeLocal = endTimeLocal;
    }

    public String getStartUtcLocal() {
        return startUtcLocal;
    }

    public void setStartUtcLocal(String startUtcLocal) {
        this.startUtcLocal = startUtcLocal;
    }

    public String getEndUtcLocal() {
        return endUtcLocal;
    }

    public void setEndUtcLocal(String endUtcLocal) {
        this.endUtcLocal = endUtcLocal;
    }

    public String getForecast() {
        return forecast;
    }

    public void setForecast(String forecast) {
        this.forecast = forecast;
    }
}
