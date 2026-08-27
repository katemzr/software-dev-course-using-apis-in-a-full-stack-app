package org.launchcode.weather_dashboard.service;

import org.launchcode.weather_dashboard.dto.GeocodeResult;
import org.launchcode.weather_dashboard.model.WeatherData;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

// This class is responsible for network communication with the public APIs
// at https://openweathermap.org/api. This uses both the Geocoding API
// and the Current Weather Data from the Weather API.

// TODO #4
//  Complete the service below using what you know about RestTemplate.
//  Refer to the documentation for both APIs to understand how the endpoints are constructed:
//  https://openweathermap.org/api/geocoding-api?collection=other
//  https://openweathermap.org/api/current?collection=current_forecast

// NOTE: The API key below is registered to Lead Instructor Carrie. You can get your own,
// but it may take an hour or two to be activated. Use Carrie's to test your app while
// you are waiting and then switch it out with your own.

@Service
public class WeatherService {

    // TODO #4A: Create a new instance of RestTemplate
    RestTemplate restTemplate = new RestTemplate();
    private static final String API_KEY = ""; // see note above
    private static final String GEO_URL = "http://api.openweathermap.org/geo/1.0/direct?q={city}&limit=1&appid={key}";
    private static final String WEATHER_URL = "https://api.openweathermap.org/data/2.5/weather?lat={lat}&lon={lon}&appid={key}&units=metric";

    public WeatherData getCurrentWeather(String city) {
        try {
            // Step 1: Call Geocoding API to get coordinates using city name
            // TODO #4B: replace null with the necessary use of your RestTemplate instance
            //  and pass in all necessary arguments for the endpoint, class, and the placeholders in GEO_URL
            GeocodeResult[] geoResults = restTemplate.getForObject(
                    GEO_URL,
                    GeocodeResult[].class,
                    city,
                    API_KEY
            );

            if (geoResults == null || geoResults.length == 0) {
                throw new RuntimeException("City not found: " + city);
            }

            double lat = geoResults[0].lat();
            double lon = geoResults[0].lon();

            // Step 2: Call Weather API using lat/lon
            // TODO #4C: replace null with the necessary use of your RestTemplate instance
            //  and pass in all necessary arguments for the endpoint, class, and the placeholders in WEATHER_URL
            WeatherData weather = restTemplate.getForObject(
                    WEATHER_URL,
                    WeatherData.class,
                    lat,
                    lon,
                    API_KEY
            );



            if (weather == null) {
                throw new RuntimeException("No weather data received for coordinates (" + lat + ", " + lon + ")");
            }

            return weather;

        } catch (
                HttpClientErrorException e) {
            // Catch HTTP status errors (404, 401, 500, etc.) from RestTemplate
            if (e.getStatusCode().value() == 404) {
                throw new RuntimeException("Location or weather resource not found: " + city);
            } else {
                throw new RuntimeException("API Error: " + e.getMessage());
            }
        } catch (RuntimeException e) {
            // Re-throw custom runtime exceptions from the try block so their messages don't
            // get combined with the general Exception catch below.
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get weather data: " + e.getMessage());
        }
    }
}
