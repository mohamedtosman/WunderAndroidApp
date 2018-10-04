package com.example.tikawy.wunder;

import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;


public class Model {
    private String TAG = Main.class.getSimpleName();
    private ArrayList<HashMap<String, String>> carList;

    public Model(){
        carList = new ArrayList<>();
    }

    /**
     * Uses the passed in jsonStr to populate a JSON object and then loops through
     * it to parse every single car's information. It then creates a single
     * Hashmap for each car and inserts into carList
     * @param jsonStr
     */
    public void populateCarListFromJson(String jsonStr){
        if (jsonStr != null) {
            try {
                // JSON object to be used to store data in provided json feed
                JSONObject jsonObj = new JSONObject(jsonStr);

                // Getting JSON Array node
                JSONArray placemarks = jsonObj.getJSONArray("placemarks");

                // Loop through all placemarks
                for (int i = 0; i < placemarks.length(); i++) {
                    JSONObject jo = placemarks.getJSONObject(i);

                    String address = jo.getString("address");
                    JSONArray coordinates = jo.getJSONArray("coordinates");
                    String lat = Double.toString(coordinates.getDouble(1));
                    String lang = Double.toString(coordinates.getDouble(0));
                    String engineType = jo.getString("engineType");
                    String exterior = jo.getString("exterior");
                    String fuel = Integer.toString(jo.getInt("fuel"));
                    String interior = jo.getString("interior");
                    String name = jo.getString("name");
                    String vin = jo.getString("vin");


                    // Hashmap for each car to be inserted into carList arrayList
                    HashMap<String, String> car = new HashMap<>();

                    // adding each child node to HashMap key => value
                    car.put("address", "Address: " + address);
                    car.put("lat", "Latitude: " + lat);
                    car.put("lang", "Longitude: " + lang);
                    car.put("engineType", "Engine Type: " + engineType);
                    car.put("exterior", "Exterior: " + exterior);
                    car.put("fuel", "Fuel: " + fuel);
                    car.put("interior", "Interior: " + interior);
                    car.put("name", "Name: " + name);
                    car.put("vin", "Vin: " + vin);

                    // Add Hashmap generated above for each car into arrayList
                    carList.add(car);
                }
            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());
            }
        }
        else {
            Log.e(TAG, "Couldn't get json from server.");
        }
    }

    /**
     * Returns private variable carList
     * @return
     */
    public ArrayList<HashMap<String, String>> getCarList(){
        return carList;
    }
}