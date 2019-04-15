package co.uk.cpt.proximity_calculator.distance;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static co.uk.cpt.proximity_calculator.distance.Distance.Unit.*;
import static org.junit.jupiter.api.Assertions.*;

class DistanceTest {

    @Test
    void givenNullUnit_shouldThrowIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Distance(1D, null));
    }

    @Test
    void givenZeroDistance_whenGetDistanceInOtherUnits_shouldReturnZero() {
        Arrays.stream(values()).forEach(unit -> {
            for (Distance.Unit otherUnit : values())
                assertEquals(0, new Distance(0, unit).in(otherUnit));
        });
    }

    @Test
    void givenAnyUnit_whenGetDistanceInSameUnit_shouldReturnSameValue() {
        Arrays.stream(values()).forEach(unit -> assertEquals(1, new Distance(1, unit).in(unit)));
    }

    @Test
    void givenMetres_whenGetKilometres_returnCorrectValue() {
        assertEquals(1, new Distance(1000, METRE).in(KILOMETRE));
    }

    @Test
    void givenMetres_whenGetMiles_returnCorrectValue() {
        assertEquals(1, new Distance(1609.344, METRE).in(MILE));
    }

    @Test
    void givenKilometres_whenGetMetres_returnsCorrectValue() {
        assertEquals(1000, new Distance(1, KILOMETRE).in(METRE));
    }

    @Test
    void givenKilometres_whenGetMiles_returnsCorrectValue() {
        assertEquals(0.621371192237334, new Distance(1, KILOMETRE).in(MILE));
    }

    @Test
    void givenMiles_whenGetMetres_returnCorrectValue() {
        assertEquals(1609.344, new Distance(1, MILE).in(METRE));
    }

    @Test
    void givenMiles_whenGetKilometres_returnCorrectValue() {
        assertEquals(1.609344, new Distance(1, MILE).in(KILOMETRE));
    }

    @Test
    void comparingIdenticalDistances_shouldReturnZero() {
        Distance distance1 = new Distance(1D, METRE);
        Distance distance2 = new Distance(1D, METRE);
        assertEquals(0, distance1.compareTo(distance2));
    }

    @Test
    void comparingToSmallerDistance_shouldReturnPositiveNumber() {
        Distance distance1 = new Distance(10, METRE);
        Distance distance2 = new Distance(5, METRE);
        assertTrue(distance1.compareTo(distance2) > 0);
    }

    @Test
    void comparingToLargerDistance_shouldReturnNegativeNumber() {
        Distance distance1 = new Distance(10, METRE);
        Distance distance2 = new Distance(5, METRE);
        assertTrue(distance2.compareTo(distance1) < 0);
    }

}