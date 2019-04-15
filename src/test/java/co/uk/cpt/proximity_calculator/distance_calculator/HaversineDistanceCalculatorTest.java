package co.uk.cpt.proximity_calculator.distance_calculator;

import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.location.LatLon;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HaversineDistanceCalculatorTest {

    private final static LatLon FLEETSMART =  new LatLon(53.528769, -2.657577);
    private final static LatLon BOBS_BUILDERS = new LatLon(53.42097355,-3.029441567);

    private final HaversineDistanceCalculator calculatorWithRadiusCalculator = new HaversineDistanceCalculator(new LocalizedRadiusCalculator());

    @Test
    void givenANullLatLon_shouldThrowException() {
        assertThrows(IllegalArgumentException.class, () -> calculatorWithRadiusCalculator.getDistance(null, null));
        assertThrows(IllegalArgumentException.class, () -> calculatorWithRadiusCalculator.getDistance(null, FLEETSMART));
        assertThrows(IllegalArgumentException.class, () -> calculatorWithRadiusCalculator.getDistance(FLEETSMART, null));
    }

    @Test
    void givenSameLatLon_andRadiusCalculator_shouldReturnZeroDistance() {
        Distance distance = calculatorWithRadiusCalculator.getDistance(FLEETSMART, FLEETSMART);
        assertEquals(Distance.ofZero(), distance);
        assertEquals(0, distance.in(Distance.Unit.METRE));
    }

    @Test
    void givenDifferentLatLons_andRadiusCalculator_shouldReturnCorrectDistance() {
        Distance distance = calculatorWithRadiusCalculator.getDistance(FLEETSMART, BOBS_BUILDERS);
        assertEquals(27345, distance.in(Distance.Unit.METRE), 1.0);
    }
}