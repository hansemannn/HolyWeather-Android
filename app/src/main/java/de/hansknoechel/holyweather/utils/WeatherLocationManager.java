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
    protected LocationManager locationManager;

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
    }

    /**
     * Requests updates on the current location.
     */
    public void getCurrentLocation() {
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        this.locationCallbackInterface.onLocationUpdated(location);
    }

    @Override
    public void onProviderDisabled(String provider) {
        System.out.println("Latitude");
    }

    @Override
    public void onProviderEnabled(String provider) {
        System.out.println("Latitude");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        System.out.println("Latitude");
    }
}
