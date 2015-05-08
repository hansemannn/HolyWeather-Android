package de.hansknoechel.holyweather.utils;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import de.hansknoechel.holyweather.interfaces.LocationCallbackListener;

/**
 * Created by hans on 07.05.15.
 */
public class WeatherLocationManager implements LocationListener {
    private final LocationCallbackListener locationCallbackInterface;
    private LocationManager locationManager;
    private boolean isGPSEnabled;
    private boolean isNetworkEnabled;

    /**
     * Sets the object instances to be used in the events.
     *
     * @param context                     The activity context of class using the WeatherLocationManager.
     * @param locationCallbackInterface   The interface instance of the callback.
     *
     */
    public WeatherLocationManager(Context context, LocationCallbackListener locationCallbackInterface) {
        this.locationCallbackInterface = locationCallbackInterface;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        this.isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        this.isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        // Register event listener for receiving location updates (@see: onLocationChanged)
        if(this.isGPSEnabled == true) {
            this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        }
    }

    /**
     * Requests updates on the current location.
     */
    public void getCurrentLocation() {
        System.out.println("getCurrentLocation");

        // If there is neither a gps nor a network connection, show error dialog
        if(this.isGPSEnabled == false || this.isNetworkEnabled == false) {
            this.locationCallbackInterface.onLocationNotAvailable("ERROR: GPS or Network not available!");
        } else {

            // Try to get the current location based on gps
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            if (location != null) {
                // If there is a gps location, delegate it to the callback
                this.locationCallbackInterface.onLocationUpdated(location);
            } else {
                // If there is no gps location, try to get the location based on the network
                location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                if(location != null) {
                    // If there is a network location, delegate it to the callback
                    this.locationCallbackInterface.onLocationUpdated(location);
                } else {
                    // If there is no gps and no network location available, show error dialog
                    this.locationCallbackInterface.onLocationNotAvailable("Network- and gps-location is not available!");
                }
            }
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        System.out.println("onLocationChanged");
        this.locationCallbackInterface.onLocationUpdated(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("Provider disabled");
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("Provider enabled");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("Status changed");
    }
}
