package co.uk.cpt.proximity_calculator.business;

import co.uk.cpt.proximity_calculator.location.Location;

public interface Locatable<T extends Location> {

    T getLocation();

}