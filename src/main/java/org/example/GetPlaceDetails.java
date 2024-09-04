package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;

public class GetPlaceDetails {
    private static final String API_KEY = "YOUR_API_KEY"; // Reemplaza con tu API Key
    private static final String PLACE_ID = "ChIJN1t_tDeuEmsRUsoyG83frY4"; // Reemplaza con el place_id obtenido

    public static void main(String[] args) {
        try {
            String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + PLACE_ID + "&key=" + API_KEY;

            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }

            in.close();
            conn.disconnect();

            JSONObject json = new JSONObject(content.toString());
            if (json.getString("status").equals("OK")) {
                JSONObject result = json.getJSONObject("result");
                String name = result.getString("name");
                double rating = result.getDouble("rating");

                System.out.println("Nombre del lugar: " + name);
                System.out.println("Puntuación: " + rating);
            } else {
                System.out.println("Error: " + json.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
