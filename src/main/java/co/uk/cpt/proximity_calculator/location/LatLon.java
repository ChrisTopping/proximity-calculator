package co.uk.cpt.proximity_calculator.location;

import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class LatLon implements Location {

    private final double lat;
    private final double lon;

    public LatLon(double lat, double lon) {
        this.lat = lat;
        this.lon = lon;
    }

    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    @Override
    public String toString() {
        return String.format("%.5f, %.5f", lat, lon);
    }

}