package com.example.tikawy.wunder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main extends AppCompatActivity implements MainActivityPresenter.View {

    private MainActivityPresenter presenter;
    private ListView lv;
    private String TAG = Main.class.getSimpleName();
    private ProgressDialog pDialog;
    // URL to get cars from JSON
    private static String url = "https://s3-us-west-2.amazonaws.com/wunderbucket/locations.json";
    Model model = new Model();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainActivityPresenter(this);

        lv = (ListView) findViewById(R.id.list);

        new GetCars().execute();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                displayCarLocation();
            }
        });
    }

    /**
     * Populates the listadapter by all the ids of the list item and their strings
     */
    @Override
    public void displayListView() {
        /*
         * Updating parsed JSON data into ListView
         */
        ListAdapter adapter = new SimpleAdapter(
                Main.this, model.getCarList(),
                R.layout.list_item, new String[]{"address",
                "lat", "lang", "engineType", "exterior",
                "fuel", "interior", "name", "vin"},
                new int[]{R.id.address,
                        R.id.lat, R.id.longit,
                        R.id.engineType, R.id.exterior,
                        R.id.fuel, R.id.interior, R.id.name,
                        R.id.vin});

        lv.setAdapter(adapter);
    }

    /**
     * Gets text view of latitude, longitude, and name of selected car and
     * launches an intent to google maps to display the car's location
     */
    @Override
    public void displayCarLocation() {
        // Get text from view by fetching it's id
        TextView lat = (TextView) findViewById(R.id.lat);
        TextView longit = (TextView) findViewById(R.id.longit);
        TextView name = (TextView) findViewById(R.id.name);

        // Extract latitue, longitude, and name from strings using regex
        String LatNumberOnly= lat.getText().toString().replaceAll("[^0-9.]",
                "");
        String LongitNumberOnly= longit.getText().toString().replaceAll("[^0-9.]",
                "");
        String NameTextOnly = name.getText().toString().replaceAll("Name: ",
                "");

        // Use google maps API + intent to launch a new screen with the selected item's
        // latitude, longitude, and name of the car
        String strUri = "http://maps.google.com/maps?q=loc:"
                + LatNumberOnly + "," + LongitNumberOnly
                + " (" + NameTextOnly + ")";
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(strUri));
        intent.setClassName("com.google.android.apps.maps",
                "com.google.android.maps.MapsActivity");
        startActivity(intent);
    }

    /**
     * Async class GetCars gets executed to fetch the json file and
     * parse out every car's info
     */
    private class GetCars extends AsyncTask<Void, Void, Void> {

        /**
         * Display a simple message while app loads the list
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(Main.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * This function executes in the background by calling populateCarListFromJson
         * to parse the JSON string into a JSON object and then into an ArrayList
         * @param voids
         * @return
         */
        @Override
        protected Void doInBackground(Void... voids) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.e(TAG, "Response from url: " + jsonStr);

            model.populateCarListFromJson(jsonStr);

            return null;
        }

        /**
         * This function calls displayListView to display all the cars in a listview
         * @param result
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            // Dismiss the progress dialog
            if (pDialog.isShowing())
            {
                pDialog.dismiss();
            }

            displayListView();
        }
    }
}