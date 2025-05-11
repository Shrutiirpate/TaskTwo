import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import org.json.JSONObject;
import java.util.*;
import java.net.URI;


public class WeatherClient {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city name:");
        String city = scanner.nextLine();
        String apiKey = "8b7ff148fd4716c4a472b03511b646f3";
        String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + apiKey + "&units=metric";

        try {
            //URL url = new URL(urlString);
            URL url = new URI(urlString).toURL();

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;

                while ((line = in.readLine()) != null) {
                    response.append(line);
                }
                in.close();

                // Parse JSON response
                JSONObject json = new JSONObject(response.toString());

                String cityName = json.getString("name");
                JSONObject main = json.getJSONObject("main");
                double temperature = main.getDouble("temp");
                int humidity = main.getInt("humidity");
                JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
                String description = weather.getString("description");

                // Display structured output
                System.out.println("Weather Report for " + cityName);
                System.out.println("-------------------------------");
                System.out.println("Temperature: " + temperature + " °C");
                System.out.println("Humidity: " + humidity + " %");
                System.out.println("Condition: " + description);
            } else {
                System.out.println("API call failed. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
