package co.uk.cpt.proximity_calculator.distance_from_origin_finder;

import co.uk.cpt.proximity_calculator.business.Business;
import co.uk.cpt.proximity_calculator.distance.Distance;
import co.uk.cpt.proximity_calculator.distance_calculator.HaversineDistanceCalculator;
import co.uk.cpt.proximity_calculator.distance_calculator.LocalizedRadiusCalculator;
import co.uk.cpt.proximity_calculator.location.LatLon;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

class LatLonDistanceFromOriginFinderTest {

    private static final Business BUSINESS = new Business("test", new LatLon(0, 0));
    private static final Business ONE = new Business("one", new LatLon(1D, 1D));
    private static final Business TWO = new Business("two", new LatLon(2D, 2D));
    private static final Business THREE = new Business("three", new LatLon(3D, 3D));
    private final HaversineDistanceCalculator calculator = new HaversineDistanceCalculator(new LocalizedRadiusCalculator());
    private final LatLonDistanceFromOriginFinder<Business> finder = new LatLonDistanceFromOriginFinder<>(calculator);

    @Test
    void whenFind_givenNullOrigin_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> finder.find(null, new ArrayList<>()));
    }

    @Test
    void whenFind_givenNullLocatables_throwIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> finder.find(BUSINESS, null));
    }

    @Test
    void whenFind_givenEmptyArray_returnEmptyMap() {
        assertTrue(finder.find(BUSINESS, new ArrayList<>()).isEmpty());
    }

    @Test
    void whenFind_givenNonEmptyArray_returnMapOfSameSize() {
        List<Business> businesses = new ArrayList<>(asList(ONE, TWO, THREE));
        assertEquals(businesses.size(), finder.find(BUSINESS, businesses).size());
    }

    @Test
    void whenFind_givenNonEmptyArray_shouldReturnCorrectMap() {
        List<Business> businesses = new ArrayList<>(asList(ONE, TWO, THREE));
        Map<Business, Distance> map = new HashMap<>();
        map.put(ONE, calculator.getDistance(BUSINESS.getLocation(), ONE.getLocation()));
        map.put(TWO, calculator.getDistance(BUSINESS.getLocation(), TWO.getLocation()));
        map.put(THREE, calculator.getDistance(BUSINESS.getLocation(), THREE.getLocation()));
        assertEquals(map, finder.find(BUSINESS, businesses));
    }
}