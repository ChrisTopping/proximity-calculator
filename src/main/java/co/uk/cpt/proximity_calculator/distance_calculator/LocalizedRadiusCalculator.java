package co.uk.cpt.proximity_calculator.distance_calculator;

import org.springframework.stereotype.Component;

import static java.lang.Math.*;


/**
 * Calculates the localized earth radius for a given latitude
 * This partially accounts for the fact that the earth is an oblate spheroid, rather than a perfect sphere
 * A better alternative would be to alter the geometry used in the Haversine calculator
 */
@Component
public class LocalizedRadiusCalculator {

    private static final double EQUATORIAL_RADIUS = 6378137;
    private static final double POLAR_RADIUS = 6356752;

    public double calculate(double latitude) {
        double a = pow((pow(EQUATORIAL_RADIUS, 2) * cos(latitude)), 2);
        double b = pow((pow(POLAR_RADIUS, 2) * sin(latitude)), 2);
        double c = pow(EQUATORIAL_RADIUS * cos(latitude), 2);
        double d = pow(POLAR_RADIUS * sin(latitude), 2);

        return sqrt((a + b) / (c + d));
    }
}