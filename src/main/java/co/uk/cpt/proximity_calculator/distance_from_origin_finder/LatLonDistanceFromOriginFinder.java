package co.uk.cpt.proximity_calculator.distance_from_origin_finder;

import co.uk.cpt.proximity_calculator.business.Locatable;
import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.distance_calculator.DistanceCalculator;
import co.uk.cpt.proximity_calculator.location.LatLon;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class LatLonDistanceFromOriginFinder<T extends Locatable<LatLon>> implements DistanceFromOriginFinder<T> {

    private final DistanceCalculator<LatLon> calculator;

    public LatLonDistanceFromOriginFinder(DistanceCalculator<LatLon> calculator) {
        this.calculator = calculator;
    }

    @Override
    public Map<T, Distance> find(T origin, List<T> locatables) {
        if (origin == null)
            throw new IllegalArgumentException("Origin must not be null.");
        if (locatables == null)
            throw new IllegalArgumentException("List of locatable objects must not be null.");

        return locatables.stream().collect(Collectors.toMap(Function.identity(), o -> calculateDistance(origin, o)));
    }

    private Distance calculateDistance(T a, T b) {
        return calculator.getDistance(a.getLocation(), b.getLocation());
    }

}