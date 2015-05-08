package de.hansknoechel.holyweather.interfaces;

import android.location.Location;

/**
 * Created by hans on 07.05.15.
 */
public interface LocationCallbackListener {

    /**
     * Returns the current location of the device.
     *
     * @param location  The current location of the device.
     *
     * @see             android.location.Location
     */
    void onLocationUpdated(Location location);

    /**
     * Returns the error if there is no location available.
     *
     * @param error     The current location of the device.
     */
    void onLocationNotAvailable(String error);
}
