package co.uk.cpt.proximity_calculator.distance_calculator;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LocalizedRadiusCalculatorTest {

    private final LocalizedRadiusCalculator calculator = new LocalizedRadiusCalculator();

    @Test
    void givenEquator_shouldReturnEquatorialRadius() {
        assertEquals(6378137, calculator.calculate(Math.toRadians(0)), 1);
    }

    @Test
    void givenPole_shouldReturnPolarRadius() {
        assertEquals(6356752, calculator.calculate(Math.toRadians(90)), 1);
    }

    @Test
    void givenALatitude_shouldReturnCorrectRadius() {
        assertEquals(6367489, calculator.calculate(Math.toRadians(45)), 1.0);
    }

}