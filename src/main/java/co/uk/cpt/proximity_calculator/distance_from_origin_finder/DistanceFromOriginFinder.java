package co.uk.cpt.proximity_calculator.distance_from_origin_finder;

import co.uk.cpt.proximity_calculator.business.Locatable;
import co.uk.cpt.proximity_calculator.distance.Distance;

import java.util.List;
import java.util.Map;

public interface DistanceFromOriginFinder<T extends Locatable> {

    Map<T, Distance> find(T origin, List<T> locatables);

}