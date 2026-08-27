package org.launchcode.weather_dashboard.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// This class represents the properties we want from the "weather" key of
// the JSON payload received from OpenWeatherMap.

@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {

    // TODO #1
    //  Take a look at the expected JSON payload on the response for
    //  a call to the current weather data at
    //  https://openweathermap.org/api/current?collection=current_forecast.
    //  Of the four properties in the "weather" object, we are interested
    //  only in "main" and "description".
    //  Create these two fields and add getters and setters for both. No need
    //  to define a constructor; the default will be fine.
    private String main;
    private String description;

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}