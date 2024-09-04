package org.example.All.in.one;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONArray;
import org.json.JSONObject;

public class GooglePlacesExample {

    private static final String API_KEY = "YOUR_API_KEY";  // Reemplaza con tu API Key

    public static void main(String[] args) {
        try {
            // Nombre del lugar que deseas buscar
            String placeName = "Volkswagen Argentina";  // Reemplaza con el nombre del lugar

            // 1. Obtener el place_id
            String placeId = getPlaceId(placeName);
            if (placeId != null) {
                System.out.println("Place ID encontrado: " + placeId);

                // 2. Obtener los detalles del lugar usando el place_id
                getPlaceDetails(placeId);
            } else {
                System.out.println("No se pudo obtener el Place ID.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método para obtener el place_id de un lugar
    public static String getPlaceId(String input) throws Exception {
        String url = "https://maps.googleapis.com/maps/api/place/findplacefromtext/json?input="
                + input.replace(" ", "%20")
                + "&inputtype=textquery&fields=place_id&key=" + API_KEY;

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
            return candidates.getJSONObject(0).getString("place_id");
        } else {
            System.out.println("Error al obtener place_id: " + json.getString("status"));
            return null;
        }
    }

    // Método para obtener los detalles del lugar (nombre y puntuación) usando el place_id
    public static void getPlaceDetails(String placeId) throws Exception {
        String url = "https://maps.googleapis.com/maps/api/place/details/json?place_id=" + placeId + "&key=" + API_KEY;

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
            double rating = result.has("rating") ? result.getDouble("rating") : 0.0;

            System.out.println("Nombre del lugar: " + name);
            System.out.println("Puntuación: " + rating);
        } else {
            System.out.println("Error al obtener detalles del lugar: " + json.getString("status"));
        }
    }
}

