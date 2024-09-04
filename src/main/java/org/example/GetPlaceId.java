package org.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GetPlaceId {
    private static final String API_KEY = "YOUR_API_KEY"; // Reemplaza con tu API Key

    public static void main(String[] args) {
        try {
            String input = "Volkswagen Argentina"; // Reemplaza con el nombre de la ubicación
            String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="
                    + input.replace(" ", "%20") + "&inputtype=textquery&fields=place_id&key=" + API_KEY;

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
                JSONArray candidates = json.getJSONArray("candidates");
                String placeId = candidates.getJSONObject(0).getString("place_id");
                System.out.println("place_id: " + placeId);
            } else {
                System.out.println("Error: " + json.getString("status"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

