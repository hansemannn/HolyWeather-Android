package de.hansknoechel.holyweather.activities;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import de.hansknoechel.holyweather.R;
import de.hansknoechel.holyweather.utils.RequestManager;
import de.hansknoechel.holyweather.models.UserLocation;
import de.hansknoechel.holyweather.utils.WeatherLocationManager;
import de.hansknoechel.holyweather.interfaces.LocationCallbackListener;
import de.hansknoechel.holyweather.interfaces.RequestCallbackListener;

public class MainActivity extends Activity {
    private RequestManager requestManager;
    private WeatherLocationManager locationManager;
    private RequestCallbackListener requestCallbackListener;
    private LocationCallbackListener locationCallbackListener;
    private UserLocation userLocation;
    private TextView locationName;
    private TextView locationTemperature;
    private ProgressBar activityIndicator;

    /**
     * Initializes the MainActivity.
     *
     * @param savedInstanceState  The instanceState of this activity to be used in the super class.
     *
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.locationName = (TextView) findViewById(R.id.locationName);
        this.locationTemperature = (TextView) findViewById(R.id.locationTemperature);
        this.activityIndicator = (ProgressBar) findViewById(R.id.activityIndicator);

        this.userLocation = new UserLocation();
        this.requestManager = new RequestManager(this);

        this.locationManager = new WeatherLocationManager(this, new LocationCallbackListener() {

            @Override
            public void onLocationUpdated(Location location) {
                userLocation.setLatitude(location.getLatitude());
                userLocation.setLongitude(location.getLongitude());

                loadData();
            }

            @Override
            public void onLocationNotAvailable(String error) {
                activityIndicator.setVisibility(View.INVISIBLE);

                Toast toast = Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        this.locationManager.getCurrentLocation();
    }

    /**
     * Loads the weather data from the json-based web service.
     **/
    private void loadData() {
        final String url = "http://api.openweathermap.org/data/2.5/weather?lat="+userLocation.getLatitude()+"&lon="+userLocation.getLongitude()+"&units=metric";
        this.requestManager.load(Request.Method.GET, url, new RequestCallbackListener(){

            @Override
            public void onResponseFinished(JSONObject response) throws JSONException {
                final String name = response.getString("name");
                final Double temperature = response.getJSONObject("main").getDouble("temp");

                locationName.setText(name);
                locationTemperature.setText(temperature.intValue()+"°");

                locationName.setVisibility(View.VISIBLE);
                locationTemperature.setVisibility(View.VISIBLE);
                activityIndicator.setVisibility(View.INVISIBLE);

                Toast toast = Toast.makeText(getApplicationContext(), "Weather was updated!", Toast.LENGTH_SHORT);
                toast.show();

            }
        });
    }

    /**
     * Handles the selection of menu items inside the actionBar.
     *
     * @param item The clicked menu item.
     *
     * @return False to allow normal menu processing to proceed, true to consume it here.
     **/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.opt_refresh:
                locationManager.getCurrentLocation();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Inflates the menu on creation.
     *
     * @param menu The menu to be inflated.
     *
     * @return False to allow normal menu processing to proceed, true to consume it here.
     **/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
