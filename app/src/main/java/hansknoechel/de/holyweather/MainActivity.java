package hansknoechel.de.holyweather;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends Activity {
    private RequestManager requestManager;
    private WeatherLocationManager locationManager;
    private UserLocation userLocation;
    private TextView locationName;
    private TextView locationTemperature;
    private ProgressBar activityIndicator;

    interface RequestCallbackListener {
        void onResponseFinished(JSONObject result) throws JSONException;
    }

    interface LocationCallbackListener {
        void onLocationUpdated(Location location);
    }

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
        });
        this.locationManager.getCurrentLocation();
    }

    private void loadData() {
        final String url = "http://api.openweathermap.org/data/2.5/weather?lat="+userLocation.getLatitude()+"&lon="+userLocation.getLongitude()+"&units=metric";
        this.requestManager.load(url, new RequestCallbackListener(){

            @Override
            public void onResponseFinished(JSONObject response) throws JSONException {
                final String name = response.getString("name");
                final Double temperature = response.getJSONObject("main").getDouble("temp");

                locationName.setText(name);
                locationTemperature.setText(temperature.intValue()+"°");

                locationName.setVisibility(View.VISIBLE);
                locationTemperature.setVisibility(View.VISIBLE);
                activityIndicator.setVisibility(View.INVISIBLE);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
