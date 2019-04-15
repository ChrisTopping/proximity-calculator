package co.uk.cpt.proximity_calculator.distance_calculator;

import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.location.Location;

public interface DistanceCalculator<T extends Location> {

    Distance getDistance(T a, T b);

}