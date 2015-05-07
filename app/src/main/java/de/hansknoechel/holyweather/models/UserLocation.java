package de.hansknoechel.holyweather.models;

/**
 * Created by hans on 07.05.15.
 */
public class UserLocation {
    private Double latitude;
    private Double longitude;

    /**
     * Sets the location latitude
     *
     * @param latitude  The new latitude.
     *
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * Sets the location longitude
     *
     * @param longitude  The new longitude.
     *
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * Returns the location latitude.
     *
     * @return The location latitude.
     *
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * Returns the location longitude.
     *
     * @return The location longitude.
     *
     */
    public Double getLongitude() {
        return longitude;
    }
}
