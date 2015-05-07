package de.hansknoechel.holyweather;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * Created by hans on 07.05.15.
 */
public class WeatherLocationManager implements LocationListener {
    private final MainActivity.LocationCallbackListener locationCallbackInterface;
    protected LocationManager locationManager;

    WeatherLocationManager(Context context, MainActivity.LocationCallbackListener locationCallbackInterface) {
        this.locationCallbackInterface = locationCallbackInterface;
        this.locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
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

    public void getCurrentLocation() {
        this.locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
    }
}
