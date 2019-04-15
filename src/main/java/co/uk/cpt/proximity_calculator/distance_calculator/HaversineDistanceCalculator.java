package co.uk.cpt.proximity_calculator.distance_calculator;

import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.location.LatLon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

/**
 * Calculates the great-circle distance between two points on the earth's surface using the Haversine distance formula.
 */
@Component
public class HaversineDistanceCalculator implements DistanceCalculator<LatLon> {

    private static final Logger log = Logger.getLogger(HaversineDistanceCalculator.class.getName());

    private static final double EARTH_RADIUS_METRES = 6371008;

    private final LocalizedRadiusCalculator radiusCalculator;

    /**
     * @param radiusCalculator allows the distance calculation to use a local radius approximation
     */
    @Autowired
    public HaversineDistanceCalculator(LocalizedRadiusCalculator radiusCalculator) {
        this.radiusCalculator = radiusCalculator;
    }

    /**
     * @param a first point
     * @param b second point
     * @return distance between a and b
     */
    @Override
    public Distance getDistance(LatLon a, LatLon b) {
        if (a == null || b == null)
            throw new IllegalArgumentException("Both LatLons must be non-null.");
        if (a.equals(b))
            return Distance.ofZero();

        return new Distance(getDistanceMetres(a, b), Distance.Unit.METRE);
    }

    private double getDistanceMetres(LatLon a, LatLon b) {
        return 2 * getLocalEarthRadius(a, b) * Math.asin(Math.sqrt(haversineFormula(a, b)));
    }

    private double getLocalEarthRadius(LatLon a, LatLon b) {
        if (radiusCalculator == null) {
            log.info("Using global average for earth radius when calculating distance between two points, as radius calculator has not been provided.");
            return EARTH_RADIUS_METRES;
        } else {
            return radiusCalculator.calculate(toRadians(averageLatitude(a, b)));
        }
    }

    private double averageLatitude(LatLon a, LatLon b) {
        return (a.getLat() + b.getLat()) / 2;
    }

    private double toRadians(double degrees) {
        return Math.toRadians(degrees);
    }

    /**
     * @param a first point
     * @param b second point
     * @return double haversine formula
     * @see <a href="https://en.wikipedia.org/wiki/Haversine_formula">Haversine Formula</a>
     */
    private double haversineFormula(LatLon a, LatLon b) {
        double dLatRadians = toRadians(getLatDistance(a, b));
        double dLonRadians = toRadians(getLonDistance(a, b));

        return haversine(dLatRadians) + Math.cos(toRadians(a.getLat())) * Math.cos(toRadians(b.getLat())) * haversine(dLonRadians);
    }

    private double getLonDistance(LatLon a, LatLon b) {
        return b.getLon() - a.getLon();
    }

    private double getLatDistance(LatLon a, LatLon b) {
        return b.getLat() - a.getLat();
    }

    private double haversine(double theta) {
        return Math.pow(Math.sin(theta / 2), 2);
    }

}
