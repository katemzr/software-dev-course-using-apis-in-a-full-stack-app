package org.launchcode.weather_dashboard.runner;

import org.launchcode.weather_dashboard.model.WeatherData;
import org.launchcode.weather_dashboard.service.WeatherService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Scanner;

// This class is responsible for running the console input and output.

// TODO #5
//  Complete the code below so that your console app is fully functional.
//  Run the main() method in WeatherDashboardApplication.java to test
//  your app.

@Component
public class ConsoleRunner implements CommandLineRunner {

    private final WeatherService weatherService;

    public ConsoleRunner(WeatherService weatherService) {
        this.weatherService = weatherService;
    }

    @Override
    public void run(String... args) {
        Scanner scanner = new Scanner(System.in);

        boolean running = true;

        System.out.println("️ Welcome to Weather Dashboard!");
        System.out.println("===================================");

        while (running) {
            showMenu();
            int choice = getChoice(scanner);

            switch (choice) {
                case 1:
                    // TODO #5E: Call checkWeather and pass along "London" and the scanner object
                    checkWeather("London", scanner);
                    break;
                case 2:
                    // TODO #5F: Call checkWeather and pass along "Paris" and the scanner object
                    checkWeather("Paris", scanner);
                    break;
                case 3:
                    // TODO #5G: Call checkWeather and pass along "Tokyo" and the scanner object
                    checkWeather("Tokyo", scanner);
                    break;
                case 4:
                    // TODO #5H: Call checkCustomCity and pass along the scanner object
                    checkCustomCity(scanner);
                    break;
                case 5:
                    System.out.println("\nThanks for using Weather Dashboard! ☀️");
                    running = false;
                    break;
                default:
                    System.out.println("❌ Invalid choice. Please try again.");

            }
        }
        scanner.close();
    }

    private void showMenu() {
        System.out.println("\n=== Weather Dashboard ===");
        System.out.println("1. Check London Weather");
        System.out.println("2. Check Paris Weather");
        System.out.println("3. Check Tokyo Weather");
        System.out.println("4. Check Custom City");
        System.out.println("5. Exit");
        System.out.print("\nChoice: ");
    }

    private int getChoice(Scanner scanner) {
        try {
            return Integer.parseInt(scanner.nextLine().trim());
        } catch (
                NumberFormatException e) {
            return -1;
        }
    }

    private void checkWeather(String city, Scanner scanner) {
        try {
            System.out.println("\n🔍 Fetching weather for " + city + "...");
            // TODO #5A: Replace null with a call to your weather service method
            //  and pass along the city.
            WeatherData weather = weatherService.getCurrentWeather(city);

            // TODO #5C: Call displayWeather and pass in the weather object.
            displayWeather(weather);

        } catch (Exception e) {
            System.out.println("❌ Error getting weather for " + city + ": " + e.getMessage());
        }
        waitForEnter(scanner);
    }

    private void checkCustomCity(Scanner scanner) {
        System.out.print("\nEnter city name: ");
        String city = scanner.nextLine().trim();
        if (!city.isEmpty()) {
            // TODO #5D: Call checkWeather and pass along the city and the scanner object
            checkWeather(city, scanner);
        } else {
            System.out.println("❌ Please enter a valid city name.");
            waitForEnter(scanner);
        }

    }

    // TODO #5B: Un-comment the method below. Double-check that all the getters for Main,
    //  Weather, and WeatherData match the ones you defined in those models.
  private void displayWeather(WeatherData weather) {
       System.out.println("\n️Current Weather in " + weather.getName() + ":");
       System.out.println("Temperature: " + weather.getMain().getTemp() + "°C");
       System.out.println("Feels like: " + weather.getMain().getFeelsLike() + "°C");
       System.out.println("Conditions: " + weather.getWeather()[0].getDescription());
       System.out.println("Humidity: " + weather.getMain().getHumidity() + "%");
       System.out.println("Pressure: " + weather.getMain().getPressure() + " hPa");

       // Add weather emoji based on conditions
       String emoji = getWeatherEmoji(weather.getWeather()[0].getMain());
       System.out.println("Status: " + emoji + " " + weather.getWeather()[0].getMain());
   }

    private String getWeatherEmoji(String condition) {
        switch (condition.toLowerCase()) {
            case "clear":
                return "☀️";
            case "clouds":
                return "☁️";
            case "rain":
                return "🌧️";
            case "snow":
                return "❄️";
            case "thunderstorm":
                return "⛈️";
            case "drizzle":
                return "️🌦️";
            case "mist":
            case "fog":
                return "️🌫️";
            default:
                return "️";
        }
    }

    private void waitForEnter(Scanner scanner) {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }
}
