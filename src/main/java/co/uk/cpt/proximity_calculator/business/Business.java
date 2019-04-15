package co.uk.cpt.proximity_calculator.business;

import co.uk.cpt.proximity_calculator.location.LatLon;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class Business implements Locatable<LatLon> {

    private final String name;
    private final LatLon latLon;

    public Business(String name, LatLon latLon) {
        if (name == null)
            throw new IllegalArgumentException("A business must have a name.");
        if (latLon == null)
            throw new IllegalArgumentException("A business must have a location.");
        this.name = name;
        this.latLon = latLon;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return String.format("%s (%s)", name, latLon.toString());
    }

    @Override
    public LatLon getLocation() {
        return latLon;
    }

}