package com.mobiddiction.fishsmart.Weather;

import java.util.ArrayList;

/**
 * Created by Archa on 24/05/2016.
 */
public class WeatherModel {

    String id;
    String name;
    String lat;
    String lon;
    String description;
    String dayLength;
    String moonPhase;
    String date;
    String temperature;
    String maximumTemperature;
    String minimumTemperature;
    String humidity;
    String windSpeed;
    String stationPressure;
    String seaLevelPressure;
    String stationAltitude;
    String utcTime;
    String localTime;

    ArrayList<astroModel> astronomyTime = new ArrayList<astroModel>();
    ArrayList<forecastModel> forecastPeriod = new ArrayList<forecastModel>();
    ArrayList<tideModel> tideData = new ArrayList<tideModel>();

    public String getUtcTime(){return utcTime;}
    public String getLocalTime(){return localTime;}
    public void setUtcTime(String utcTime){this.utcTime = utcTime;}
    public void setLocalTime(String localTime){this.localTime = localTime;}

    public String getDescription() {
        return description;
    }

    public void setstationDescription(String description) {
        this.description = description;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getDayLength() {
        return dayLength;
    }

    public void setDayLength(String dayLength) {
        this.dayLength = dayLength;
    }

    public String getMoonPhase() {
        return moonPhase;
    }

    public void setMoonPhase(String moonPhase) {
        this.moonPhase = moonPhase;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(String maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    public String getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(String minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public String getHumidity() {
        return humidity;
    }

    public void setHumidity(String humidity) {
        this.humidity = humidity;
    }

    public String getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(String windSpeed) {
        this.windSpeed = windSpeed;
    }

    public String getStationPressure() {
        return stationPressure;
    }

    public void setStationPressure(String stationPressure) {
        this.stationPressure = stationPressure;
    }

    public String getSeaLevelPressure() {
        return seaLevelPressure;
    }

    public void setSeaLevelPressure(String seaLevelPressure) {
        this.seaLevelPressure = seaLevelPressure;
    }

    public ArrayList<forecastModel> getForecastPeriod() {
        return forecastPeriod;
    }

    public void setForecastPeriod(ArrayList<forecastModel> forecastPeriod) {
        this.forecastPeriod = forecastPeriod;
    }

    public ArrayList<tideModel> getTideData() {
        return tideData;
    }

    public void setTideData(ArrayList<tideModel> tideData) {
        this.tideData = tideData;
    }

    public ArrayList<astroModel> getAstronomyTime() {
        return astronomyTime;
    }

    public void setAstronomyTime(ArrayList<astroModel> astronomyTime) {
        this.astronomyTime = astronomyTime;
    }

    public String getStationAltitude() {
        return stationAltitude;
    }

    public void setStationAltitude(String stationAltitude) {
        this.stationAltitude = stationAltitude;
    }
}
